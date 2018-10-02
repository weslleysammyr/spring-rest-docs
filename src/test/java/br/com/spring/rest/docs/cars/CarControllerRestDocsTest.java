package br.com.spring.rest.docs.cars;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.spring.rest.docs.cars.CarController;
import br.com.spring.rest.docs.cars.description.CarDescription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CarControllerRestDocsTest {

	private MockMvc mockMvc;

	@Configuration
	static class ContextConfiguration {

	}

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(new CarController())
				.apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation)).build();
	}

	@Test
	public void getAll() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders
				.get(CarController.API_ROOT_RESOURCE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
						PayloadDocumentation
								.responseFields(PayloadDocumentation.fieldWithPath("[]").description("Array of cars"))
								.andWithPrefix("[]", CarDescription.car())));
	}

	@Test
	public void getById() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders
				.get(CarController.API_ROOT_RESOURCE + "/{" + CarController.PARAM_ID + "}", 1)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
						PayloadDocumentation.responseFields(CarDescription.car()),
						RequestDocumentation.pathParameters(RequestDocumentation
								.parameterWithName(CarController.PARAM_ID).description("Car's id"))));
	}

	@Test
	public void create() throws Exception {
		CarDTO dto = new CarDTO();
		dto.setBrand("Bugatti");
		dto.setModel("Chiron");
		dto.setYear(2017);
		dto.setLicensePlate("FUK1988");
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(dto);
		mockMvc.perform(RestDocumentationRequestBuilders.post(CarController.API_ROOT_RESOURCE)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcRestDocumentation.document(
						"{ClassName}/{methodName}", PayloadDocumentation.requestFields(CarDescription.carPost())));
	}

}
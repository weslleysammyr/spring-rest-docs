package br.com.spring.rest.docs.carros;

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

import br.com.spring.rest.docs.carros.CarroController;
import br.com.spring.rest.docs.carros.CarroDTO;
import br.com.spring.rest.docs.carros.descricao.CarroDescricao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CarroControllerRestDocsTest {

	private MockMvc mockMvc;

	@Configuration
	static class ContextConfiguration {

	}

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(new CarroController())
				.apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation)).build();
	}

	@Test
	public void listar() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders
				.get(CarroController.API_ROOT_RESOURCE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
						PayloadDocumentation
								.responseFields(PayloadDocumentation.fieldWithPath("[]").description("Lista de carros"))
								.andWithPrefix("[]", CarroDescricao.carro())));
	}

	@Test
	public void buscarPorId() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders
				.get(CarroController.API_ROOT_RESOURCE + "/{" + CarroController.PARAM_ID + "}", 1)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
						PayloadDocumentation.responseFields(CarroDescricao.carro()),
						RequestDocumentation.pathParameters(RequestDocumentation
								.parameterWithName(CarroController.PARAM_ID).description("Identificador do carro"))));
	}

	@Test
	public void salvar() throws Exception {
		CarroDTO dto = new CarroDTO();
		dto.setMarca("Bugatti");
		dto.setModelo("Chiron");
		dto.setAno(2017);
		dto.setPlaca("FUK1988");
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(dto);
		mockMvc.perform(RestDocumentationRequestBuilders.post(CarroController.API_ROOT_RESOURCE)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcRestDocumentation.document(
						"{ClassName}/{methodName}", PayloadDocumentation.requestFields(CarroDescricao.carroPost())));
	}

}
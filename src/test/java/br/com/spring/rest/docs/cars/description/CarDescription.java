package br.com.spring.rest.docs.cars.description;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;

import br.com.spring.rest.docs.cars.CarDTO;

public class CarDescription {

	private CarDescription() {
	}

	public static FieldDescriptor[] car() {
		return new FieldDescriptor[] { PayloadDocumentation.fieldWithPath("id").description("The car's id."),
				PayloadDocumentation.fieldWithPath("brand").description("The car's brand."),
				PayloadDocumentation.fieldWithPath("model").description("The car's model."),
				PayloadDocumentation.fieldWithPath("year").description("The car's year."),
				PayloadDocumentation.fieldWithPath("licensePlate").description("The car's license plate.") };
	}

	public static FieldDescriptor[] carPost() {
		return new FieldDescriptor[] {
				PayloadDocumentation.fieldWithPath("brand")
						.description("The car's brand. " + getConstraints(CarDTO.class, "brand")),
				PayloadDocumentation.fieldWithPath("model")
						.description("The car's model. " + getConstraints(CarDTO.class, "model")),
				PayloadDocumentation.fieldWithPath("year")
						.description("The car's year. " + getConstraints(CarDTO.class, "year")),
				PayloadDocumentation.fieldWithPath("licensePlate")
						.description("The car's license plate. " + getConstraints(CarDTO.class, "licensePlate")) };
	}

	private static <T> String getConstraints(Class<T> clazz, String property) {
		ConstraintDescriptions userConstraints = new ConstraintDescriptions(clazz);
		List<String> descriptions = userConstraints.descriptionsForProperty(property);
		StringJoiner stringJoiner = new StringJoiner(". ", "", ".");
		descriptions.forEach(stringJoiner::add);
		return stringJoiner.toString();
	}

}
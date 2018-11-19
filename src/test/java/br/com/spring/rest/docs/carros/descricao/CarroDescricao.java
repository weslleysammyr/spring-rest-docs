package br.com.spring.rest.docs.carros.descricao;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;

import br.com.spring.rest.docs.carros.CarroDTO;

public class CarroDescricao {

	private CarroDescricao() {
	}

	public static FieldDescriptor[] carro() {
		return new FieldDescriptor[] { PayloadDocumentation.fieldWithPath("id").description("Identificador do carro."),
				PayloadDocumentation.fieldWithPath("marca").description("Marca do carro."),
				PayloadDocumentation.fieldWithPath("modelo").description("Modelo do carro."),
				PayloadDocumentation.fieldWithPath("ano").description("Ano do carro."),
				PayloadDocumentation.fieldWithPath("placa").description("Placa do carro.") };
	}

	public static FieldDescriptor[] carroPost() {
		return new FieldDescriptor[] {
				PayloadDocumentation.fieldWithPath("marca")
						.description("Marca do carro. " + getConstraints(CarroDTO.class, "marca")),
				PayloadDocumentation.fieldWithPath("modelo")
						.description("Modelo do carro. " + getConstraints(CarroDTO.class, "modelo")),
				PayloadDocumentation.fieldWithPath("ano")
						.description("Ano do carro. " + getConstraints(CarroDTO.class, "ano")),
				PayloadDocumentation.fieldWithPath("placa")
						.description("Placa do carro. " + getConstraints(CarroDTO.class, "placa")) };
	}

	private static <T> String getConstraints(Class<T> clazz, String property) {
		ConstraintDescriptions userConstraints = new ConstraintDescriptions(clazz);
		List<String> descriptions = userConstraints.descriptionsForProperty(property);
		StringJoiner stringJoiner = new StringJoiner(". ", "", ".");
		descriptions.forEach(stringJoiner::add);
		return stringJoiner.toString();
	}

}
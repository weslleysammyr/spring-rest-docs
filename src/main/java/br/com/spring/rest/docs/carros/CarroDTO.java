package br.com.spring.rest.docs.carros;

import javax.validation.constraints.NotNull;

public class CarroDTO {

	@NotNull
	private String marca;
	@NotNull
	private String modelo;
	@NotNull
	private Integer ano;
	@NotNull
	private String placa;

	public CarroDTO() {

	}

	public CarroDTO(Carro car) {
		setMarca(car.getMarca());
		setModelo(car.getModelo());
		setAno(car.getAno());
		setPlaca(car.getPlaca());
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}

package br.com.spring.rest.docs.cars;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CarDTO {

	@NotNull
	private String brand;
	@NotNull
	private String model;
	@NotNull
	@Pattern(regexp = "\\d")
	private Integer year;
	@NotNull
	private String licensePlate;

	public CarDTO() {

	}

	public CarDTO(Car car) {
		setBrand(car.getBrand());
		setModel(car.getModel());
		setYear(car.getYear());
		setLicensePlate(car.getLicensePlate());
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String marca) {
		this.brand = marca;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String modelo) {
		this.model = modelo;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer ano) {
		this.year = ano;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String placa) {
		this.licensePlate = placa;
	}

}

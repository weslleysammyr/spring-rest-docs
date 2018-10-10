package br.com.spring.rest.docs.cars;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(CarController.API_ROOT_RESOURCE)
@RestController
public class CarController {

	public static final String API_ROOT_RESOURCE = "/cars";
	public static final String SEARCH_RESOURCE = "/search";
	public static final String PARAM_ID = "id";
	public static final String PARAM_MODEL_NAME = "brand";

	private List<Car> cars;

	public CarController() {
		final Car car1 = new Car();
		car1.setId(1L);
		car1.setBrand("Fiat");
		car1.setYear(LocalDate.now().minusYears(4).getYear());
		car1.setModel("Uno");
		car1.setLicensePlate("XXX9999");

		final Car car2 = new Car();
		car2.setId(2L);
		car2.setBrand("Ford");
		car2.setYear(LocalDate.now().minusYears(2).getYear());
		car2.setModel("Ka");
		car2.setLicensePlate("YYY0000");

		cars = new ArrayList<>();
		cars.add(car1);
		cars.add(car2);
	}

	@GetMapping
	public List<Car> getAll() {
		return cars;
	}

	@GetMapping(SEARCH_RESOURCE)
	public List<Car> searchByBrand(@RequestParam(PARAM_MODEL_NAME) String brand) {
		return cars.parallelStream().filter(m -> m.getBrand().toUpperCase().startsWith(brand.toUpperCase()))
				.collect(Collectors.toList());
	}

	@GetMapping("/{" + PARAM_ID + "}")
	public Car getById(@PathVariable(PARAM_ID) Long id) {
		return cars.parallelStream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody CarDTO carDto) {
		Long maxId = this.cars.stream().max(Comparator.comparingLong(Car::getId)).map(Car::getId).orElse(0L);
		Car car = new Car();
		car.setId(maxId + 1);
		car.setBrand(carDto.getBrand());
		car.setModel(carDto.getModel());
		car.setYear(carDto.getYear());
		car.setLicensePlate(carDto.getLicensePlate());
		this.cars.add(car);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
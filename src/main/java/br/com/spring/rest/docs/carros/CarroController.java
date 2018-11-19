package br.com.spring.rest.docs.carros;

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

@RequestMapping(CarroController.API_ROOT_RESOURCE)
@RestController
public class CarroController {

	public static final String API_ROOT_RESOURCE = "/carros";
	public static final String SEARCH_RESOURCE = "/buscar";
	public static final String PARAM_ID = "id";
	public static final String PARAM_MODEL_NAME = "marca";

	private List<Carro> carros;

	public CarroController() {
		final Carro carro1 = new Carro();
		carro1.setId(1L);
		carro1.setMarca("Fiat");
		carro1.setAno(LocalDate.now().minusYears(4).getYear());
		carro1.setModelo("Uno");
		carro1.setPlaca("XXX9999");

		final Carro carro2 = new Carro();
		carro2.setId(2L);
		carro2.setMarca("Ford");
		carro2.setAno(LocalDate.now().minusYears(2).getYear());
		carro2.setModelo("Ka");
		carro2.setPlaca("YYY0000");

		carros = new ArrayList<>();
		carros.add(carro1);
		carros.add(carro2);
	}

	@GetMapping
	public List<Carro> listar() {
		return carros;
	}

	@GetMapping(SEARCH_RESOURCE)
	public List<Carro> buscarPorMarca(@RequestParam(PARAM_MODEL_NAME) String marca) {
		return carros.parallelStream().filter(m -> m.getMarca().toUpperCase().startsWith(marca.toUpperCase()))
				.collect(Collectors.toList());
	}

	@GetMapping("/{" + PARAM_ID + "}")
	public Carro buscarPorId(@PathVariable(PARAM_ID) Long id) {
		return carros.parallelStream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody CarroDTO carroDto) {
		Long maxId = this.carros.stream().max(Comparator.comparingLong(Carro::getId)).map(Carro::getId).orElse(0L);
		Carro carro = new Carro();
		carro.setId(maxId + 1);
		carro.setMarca(carroDto.getMarca());
		carro.setModelo(carroDto.getModelo());
		carro.setAno(carroDto.getAno());
		carro.setPlaca(carroDto.getPlaca());
		this.carros.add(carro);
		return new ResponseEntity<>(carro, HttpStatus.CREATED);
	}

}
package br.com.zup.primeiro.desafio.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.primeiro.desafio.dto.CustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;
import br.com.zup.primeiro.desafio.service.CustomerService;

@RestController
@RequestMapping(value = "/costumers")
public class CustomerController {

	CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@ResponseStatus(CREATED)
	@PostMapping
	public UUID create(@Valid @RequestBody CustomerDTO costumerDTO) throws GenericException {
		return service.create(costumerDTO);
	}

	@ResponseStatus(OK)
	@GetMapping
	public List<Customer> findAll() {
		return service.findAll();
	}

	@ResponseStatus(OK)
	@GetMapping(value = "/{cpf}")
	public Customer findByCpf(@PathVariable String cpf) {
		return service.findByCpf(cpf);
	}

	@ResponseStatus(OK)
	@PutMapping(value = "/{cpf}")
	public UUID update(@Valid @RequestBody CustomerDTO costumerDTO) {
		return service.update(costumerDTO);
	}

	@ResponseStatus(NO_CONTENT)
	@DeleteMapping(value = "/{cpf}")
	public void delete(String cpf) {
		service.delete(cpf);
	}

}

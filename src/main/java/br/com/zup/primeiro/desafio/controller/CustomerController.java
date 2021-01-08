package br.com.zup.primeiro.desafio.controller;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.primeiro.desafio.dto.CustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
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
	public Long create(CustomerDTO costumerDTO) {
		return service.create(costumerDTO);
	}

	@ResponseStatus(OK)
	@GetMapping
	public List<Customer> findAll() {
		return service.findAll();
	}

	@ResponseStatus(OK)
	@GetMapping(value = "/{cpf}")
	public Customer findByCpf(String cpf) {
		return service.findByCpf(cpf);
	}

	@ResponseStatus(OK)
	@PutMapping(value = "/{cpf}")
	public void update(CustomerDTO costumerDTO) {
		service.update(costumerDTO);
	}

	@ResponseStatus(NO_CONTENT)
	@DeleteMapping(value = "/{cpf}")
	public void delete(String cpf) {
		service.delete(cpf);
	}

}

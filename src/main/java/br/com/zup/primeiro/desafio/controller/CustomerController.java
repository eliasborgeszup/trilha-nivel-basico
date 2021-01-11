package br.com.zup.primeiro.desafio.controller;

import java.util.List;

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

import br.com.zup.primeiro.desafio.dto.CreateCustomerDTO;
import br.com.zup.primeiro.desafio.dto.UpdateCustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;
import br.com.zup.primeiro.desafio.service.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

	CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@ResponseStatus(CREATED)
	@PostMapping
	public String create(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) throws GenericException {
		return service.create(createCustomerDTO);
	}

	@ResponseStatus(OK)
	@GetMapping
	public List<Customer> findAll() {
		return service.findAll();
	}

	@ResponseStatus(OK)
	@GetMapping(value = "/{cpf}")
	public Customer findByCpf(@PathVariable String cpf) throws GenericException {
		return service.findByCpf(cpf);
	}

	@ResponseStatus(OK)
	@PutMapping(value = "/{cpf}")
	public String update(@PathVariable String cpf, @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO)
			throws GenericException {
		return service.update(cpf, updateCustomerDTO);
	}

	@ResponseStatus(NO_CONTENT)
	@DeleteMapping(value = "/{cpf}")
	public void delete(@PathVariable String cpf) throws GenericException {
		service.delete(cpf);
	}

}

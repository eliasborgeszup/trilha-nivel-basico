package br.com.zup.primeiro.desafio.controller;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.data.domain.Sort.Direction.ASC;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.primeiro.desafio.config.PageSizeValidator;
import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.response.customer.CustomerIDResponse;
import br.com.zup.primeiro.desafio.controller.response.customer.CustomerResponse;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.service.CustomerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
	private static final int SIZE_MAX_PAGE = 100;

	private CustomerService service;

	@ResponseStatus(CREATED)
	@PostMapping
	public CustomerIDResponse create(@Valid @RequestBody CreateCustomerRequest request) {
		return new CustomerIDResponse(service.create(request));
	}

	@ResponseStatus(OK)
	@GetMapping
	public Page<CustomerResponse> findAll(
			@PageableDefault(sort = "name", direction = ASC, page = 0, size = 20) Pageable page) {

		PageSizeValidator.validate(SIZE_MAX_PAGE, page.getPageSize());

		return service.findAll(page).map(CustomerResponse::fromCustomer);
	}

	@ResponseStatus(OK)
	@GetMapping(value = "/{cpf}")
	public Customer findByCpf(@PathVariable String cpf) {
		return service.findByCpf(cpf);
	}

	@ResponseStatus(OK)
	@PutMapping(value = "/{cpf}")
	public CustomerIDResponse update(@PathVariable String cpf, @Valid @RequestBody UpdateCustomerRequest request) {
		return new CustomerIDResponse(service.update(cpf, request));
	}

	@ResponseStatus(NO_CONTENT)
	@DeleteMapping(value = "/{cpf}")
	public void delete(@PathVariable String cpf) {
		service.delete(cpf);
	}

}

package br.com.zup.primeiro.desafio.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.PaginationSizeLimitExceededException;
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
	public String create(@Valid @RequestBody CreateCustomerRequest request) {
		return service.create(request);
	}

	@ResponseStatus(OK)
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll(
			@PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 20) Pageable page) {

		if(page.getPageSize() > SIZE_MAX_PAGE) {
			throw new PaginationSizeLimitExceededException("m: findAll" + "size: " + page.getPageSize());
		}
		
		Page<Customer> pageCustomers = service.findAll(page);
		List<Customer> customers = pageCustomers.getContent();
		Map<String, Object> response = new HashMap<>();

		response.put("customers", customers);
		response.put("currentPage", pageCustomers.getNumber());
		response.put("totalItems", pageCustomers.getTotalElements());
		response.put("totalPages", pageCustomers.getTotalPages());

		return new ResponseEntity<>(response, OK);
	}

	@ResponseStatus(OK)
	@GetMapping(value = "/{cpf}")
	public Customer findByCpf(@PathVariable String cpf) {
		return service.findByCpf(cpf);
	}

	@ResponseStatus(OK)
	@PutMapping(value = "/{cpf}")
	public String update(@PathVariable String cpf, @Valid @RequestBody UpdateCustomerRequest request) {
		return service.update(cpf, request);
	}

	@ResponseStatus(NO_CONTENT)
	@DeleteMapping(value = "/{cpf}")
	public void delete(@PathVariable String cpf) {
		service.delete(cpf);
	}

}

package br.com.zup.primeiro.desafio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.DocumentAlreadyExistsException;
import br.com.zup.primeiro.desafio.exceptions.NotFoundException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;
import br.com.zup.primeiro.desafio.service.CustomerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository repository;

	public String create(CreateCustomerRequest request) {
		if (repository.existsByCpf(request.getCpf())) {
			throw new DocumentAlreadyExistsException("service m: created" + "cpf:" + request.getCpf());
		}
		return new Customer().create(request, repository);
	}

	public Page<Customer> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Customer findByCpf(String cpf) {
		return repository.findByCpf(cpf).orElseThrow(() -> new NotFoundException("service m: findByCpf cpf: " + cpf));
	}

	public String update(String cpf, UpdateCustomerRequest request) {
		Customer customer = repository.findByCpf(cpf)
				.orElseThrow(() -> new NotFoundException("service m: findByCpf" + "cpf:" + cpf));

		return customer.update(request, repository);
	}

	public void delete(String cpf) {
		Customer customer = repository.findByCpf(cpf)
				.orElseThrow(() -> new NotFoundException("service m: delete" + "cpf:" + cpf));

		customer.delete(customer, repository);
	}

}

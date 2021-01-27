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
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository repository;

	public String create(CreateCustomerRequest request) {
		if (repository.existsByCpf(request.getCpf())) {
			log.error("Client not created, user = {} register", request.getCpf());
			throw new DocumentAlreadyExistsException("CustomerServiceImpl: create");
		}

		return new Customer().create(request, repository);
	}

	public Page<Customer> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Customer findByCpf(String cpf) {
		return repository.findByCpf(cpf).orElseThrow(() -> {
			log.error("Client not update, user = {} not found", cpf);
			throw new NotFoundException("CustomerServiceImpl: findByCpf");
		});
	}

	public String update(String cpf, UpdateCustomerRequest request) {
		Customer customer = repository.findByCpf(cpf).orElseThrow(() -> {
			log.error("Client not update, user = {} not found", cpf);
			throw new NotFoundException("CustomerServiceImpl: update");
		});

		return customer.update(request, repository);
	}

	public void delete(String cpf) {
		Customer customer = repository.findByCpf(cpf).orElseThrow(() -> {
			log.error("Client not update, user = {} not found", cpf);
			throw new NotFoundException(String.format("CustomerServiceImpl: delete, user = %s not found", cpf));
		});

		customer.delete(customer, repository);
	}

}

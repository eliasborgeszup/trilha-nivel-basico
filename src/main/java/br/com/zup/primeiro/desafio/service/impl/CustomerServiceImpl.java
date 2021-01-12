package br.com.zup.primeiro.desafio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;
import br.com.zup.primeiro.desafio.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final String CPF_NOT_FOUND = "CPF não encontrado!";
	private static final String CPF_REGISTERED = "CPF já cadastrado!";

	CustomerRepository repository;

	public CustomerServiceImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	public String create(CreateCustomerRequest request) throws GenericException {
		if (repository.existsByCpf(request.getCpf())) {
			throw new GenericException(CPF_REGISTERED);
		}

		return new Customer().create(request, repository);
	}

	public List<Customer> findAll() {
		return (List<Customer>) repository.findAll();
	}

	public Customer findByCpf(String cpf) throws GenericException {
		return repository.findByCpf(cpf).orElseThrow(() -> new GenericException(CPF_NOT_FOUND));
	}

	public String update(String cpf, UpdateCustomerRequest request) throws GenericException {
		Customer customer = repository.findByCpf(cpf).orElseThrow(() -> new GenericException(CPF_NOT_FOUND));

		return customer.update(request, repository);
	}

	public void delete(String cpf) throws GenericException {
		Customer customer = repository.findByCpf(cpf).orElseThrow(() -> new GenericException(CPF_NOT_FOUND));

		customer.delete(customer, repository);
	}

}

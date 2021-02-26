package br.com.zup.primeiro.desafio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;

public interface CustomerService {
	public String create(CreateCustomerRequest request);

	public Page<Customer> findAll(Pageable page);

	public Customer findByCpf(String cpf);

	public Customer findById(String id);

	public String update(String cpf, UpdateCustomerRequest request);

	public void delete(String cpf);
}
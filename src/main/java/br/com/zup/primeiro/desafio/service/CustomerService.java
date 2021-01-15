package br.com.zup.primeiro.desafio.service;

import java.util.List;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;

public interface CustomerService {
	public String create(CreateCustomerRequest request);

	public List<Customer> findAll();

	public Customer findByCpf(String cpf);

	public String update(String cpf, UpdateCustomerRequest request);

	public void delete(String cpf);
}
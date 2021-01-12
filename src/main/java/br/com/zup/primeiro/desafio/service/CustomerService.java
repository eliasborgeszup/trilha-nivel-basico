package br.com.zup.primeiro.desafio.service;

import java.util.List;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;

public interface CustomerService {
	public String create(CreateCustomerRequest request) throws GenericException;

	public List<Customer> findAll();

	public Customer findByCpf(String cpf) throws GenericException;

	public String update(String cpf, UpdateCustomerRequest request) throws GenericException;

	public void delete(String cpf) throws GenericException;
}
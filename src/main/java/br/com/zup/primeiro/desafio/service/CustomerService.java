package br.com.zup.primeiro.desafio.service;

import java.util.List;

import br.com.zup.primeiro.desafio.dto.CreateCustomerDTO;
import br.com.zup.primeiro.desafio.dto.UpdateCustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;

public interface CustomerService {
	public String create(CreateCustomerDTO createCustomerDTO) throws GenericException;

	public List<Customer> findAll();

	public Customer findByCpf(String cpf) throws GenericException;

	public String update(String cpf, UpdateCustomerDTO updateCustomerDTO) throws GenericException;

	public void delete(String cpf) throws GenericException;
}
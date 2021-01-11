package br.com.zup.primeiro.desafio.service;

import java.util.List;
import java.util.UUID;

import br.com.zup.primeiro.desafio.dto.CustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;

public interface CustomerService {
	public UUID create(CustomerDTO customerDTO) throws GenericException;

	public List<Customer> findAll();

	public Customer findByCpf(String cpf) throws GenericException;

	public String update(String cpf, CustomerDTO customerDTO) throws GenericException;

	public void delete(String cpf) throws GenericException;
}
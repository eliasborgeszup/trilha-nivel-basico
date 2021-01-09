package br.com.zup.primeiro.desafio.service;

import java.util.List;
import java.util.UUID;

import br.com.zup.primeiro.desafio.dto.CustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;

public interface CustomerService {
	public UUID create(CustomerDTO costumerDTO) throws GenericException;

	public List<Customer> findAll();

	public Customer findByCpf(String cpf) throws GenericException;

	public UUID update(CustomerDTO costumerDTO);

	public void delete(String cpf);
}
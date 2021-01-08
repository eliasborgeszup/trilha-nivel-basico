package br.com.zup.primeiro.desafio.service;

import java.util.List;

import br.com.zup.primeiro.desafio.dto.CustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;

public interface CustomerService {
	public Long create(CustomerDTO costumerDTO);

	public List<Customer> findAll();

	public Customer findByCpf(String cpf);

	public Long update(CustomerDTO costumerDTO);

	public void delete(String cpf);
}
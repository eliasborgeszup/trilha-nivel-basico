package br.com.zup.primeiro.desafio.service;

import java.util.List;

import br.com.zup.primeiro.desafio.dto.CustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;

public interface CustomerService {
	public void created(CustomerDTO costumerDTO);
	
	public List<Customer> findAll();
	
	public Customer findByCpf(String cpf);
	
	public void delete(String cpf);
}
package br.com.zup.primeiro.desafio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.dto.CustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	public Long create(CustomerDTO costumerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findByCpf(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long update(CustomerDTO costumerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String cpf) {
		// TODO Auto-generated method stub

	}
}

package br.com.zup.primeiro.desafio.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.dto.CustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;
import br.com.zup.primeiro.desafio.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final String CPF_JÁ_CADASTRADO = "CPF já cadastrado!";

	CustomerRepository repository;

	public CustomerServiceImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	public UUID create(CustomerDTO costumerDTO) throws GenericException {

		boolean verificarCPFExistenteBD = repository.existsByCpf(costumerDTO.getCpf());

		if (verificarCPFExistenteBD) {
			throw new GenericException(CPF_JÁ_CADASTRADO);
		}

		Customer customer = new Customer();

		BeanUtils.copyProperties(costumerDTO, customer);

		customer.setId(UUID.randomUUID());
		repository.save(customer);

		return customer.getId();
	}

	@Override
	public List<Customer> findAll() {
		return (List<Customer>)repository.findAll();
	}

	@Override
	public Customer findByCpf(String cpf) {
		return null;
	}

	@Override
	public UUID update(CustomerDTO costumerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String cpf) {
		// TODO Auto-generated method stub

	}
}

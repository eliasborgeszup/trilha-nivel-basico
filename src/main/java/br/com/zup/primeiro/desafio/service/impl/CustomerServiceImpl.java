package br.com.zup.primeiro.desafio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.dto.CreateCustomerDTO;
import br.com.zup.primeiro.desafio.dto.UpdateCustomerDTO;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.GenericException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;
import br.com.zup.primeiro.desafio.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final String CPF_NOT_FOUND = "CPF não encontrado!";
	private static final String CPF_REGISTERED = "CPF já cadastrado!";

	CustomerRepository repository;

	public CustomerServiceImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	public String create(CreateCustomerDTO createCustomerDTO) throws GenericException {
		if (repository.existsByCpf(createCustomerDTO.getCpf())) {
			throw new GenericException(CPF_REGISTERED);
		}

		return new Customer().create(createCustomerDTO, repository);
	}

	public List<Customer> findAll() {
		return (List<Customer>) repository.findAll();
	}

	public Customer findByCpf(String cpf) throws GenericException {
		return repository.findByCpf(cpf).orElseThrow(() -> new GenericException(CPF_NOT_FOUND));
	}

	public String update(String cpf, UpdateCustomerDTO updateCustomerDTO) throws GenericException {
		Customer customer = repository.findByCpf(cpf).orElseThrow(() -> new GenericException(CPF_NOT_FOUND));

		return customer.update(updateCustomerDTO, repository);
	}

	public void delete(String cpf) throws GenericException {
		Customer customer = repository.findByCpf(cpf).orElseThrow(() -> new GenericException(CPF_NOT_FOUND));
		
		customer.delete(customer, repository);
	}

}

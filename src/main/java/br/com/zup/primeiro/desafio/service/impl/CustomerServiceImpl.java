package br.com.zup.primeiro.desafio.service.impl;

import java.util.List;
import java.util.Optional;
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

	private static final String CPF_NÃO_ENCONTRADO = "CPF não encontrado!";

	private static final String CPF_JÁ_CADASTRADO = "CPF já cadastrado!";

	CustomerRepository repository;

	public CustomerServiceImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	public UUID create(CustomerDTO customerDTO) throws GenericException {
		if (checkExistByCPF(customerDTO.getCpf())) {
			throw new GenericException(CPF_JÁ_CADASTRADO);
		}

		return repository.save(convertDtoToEntity(customerDTO)).getId();
	}

	public List<Customer> findAll() {
		return (List<Customer>) repository.findAll();
	}

	public Customer findByCpf(String cpf) throws GenericException {
		return repository.findByCpf(cpf).orElseThrow(() -> new GenericException(CPF_NÃO_ENCONTRADO));
	}

	public String update(String cpf, CustomerDTO customerDTO) throws GenericException {
		Optional<Customer> customerConsulted = repository.findByCpf(cpf);
		
		if(customerConsulted.isEmpty()) {
			throw new GenericException(CPF_NÃO_ENCONTRADO);
		}

		Customer customer = convertDtoToEntity(customerDTO);

		customer.setId(customerConsulted.get().getId());
		customer.setCpf(cpf);

		return repository.save(customer).getId().toString();
	}

	public void delete(String cpf) throws GenericException {
		if (!checkExistByCPF(cpf)) {
			throw new GenericException(CPF_NÃO_ENCONTRADO);
		}

		repository.deleteCustomerByCpf(cpf);
	}

	public boolean checkExistByCPF(String cpf) {
		return repository.existsByCpf(cpf);
	}

	public Customer convertDtoToEntity(CustomerDTO customerDTO) {
		Customer customer = new Customer();

		BeanUtils.copyProperties(customerDTO, customer);

		return customer;
	}
}

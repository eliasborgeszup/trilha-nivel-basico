package br.com.zup.primeiro.desafio.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.DocumentAlreadyExistsException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository repository;

	@InjectMocks
	private CustomerServiceImpl service;

	@Test
	public void shouldCreateCustomerAndReturnId() {
		//Given
		CreateCustomerRequest customerRequest = buildCreateCustomerRequest();
		Customer customer = buildCustomer();
		when(repository.save(any())).thenReturn(customer);
		
		//When
		String id = service.create(customerRequest);
		
		//Then
		assertEquals(id, customer.getId());
		
	}
	
	@Test
	public void findByCpfSucess() {
		Customer customer = buildCustomer();

		Optional<Customer> customerOptional = Optional.of(customer);

		when(repository.findByCpf(customer.getCpf())).thenReturn(customerOptional);

		service.findByCpf(customer.getCpf());
	}

	public CreateCustomerRequest buildCreateCustomerRequest() {
		return new CreateCustomerRequest("Elias", LocalDate.now(), "59522283053", "eliasborges@zup.com.br",
				"34992454428", "Rua X");
	}

	public UpdateCustomerRequest buildCreateUpdateCustomerRequest() {
		return new UpdateCustomerRequest("Elias", LocalDate.now(), "eliasborges@zup.com.br", "34992454428", "Rua X");
	}

	public Customer buildCustomer() {
		return new Customer(UUID.randomUUID().toString(), "Elias", LocalDate.now(), "59522283053",
				"eliasborges@zup.com.br", "34992454428", "Rua X");
	}
}

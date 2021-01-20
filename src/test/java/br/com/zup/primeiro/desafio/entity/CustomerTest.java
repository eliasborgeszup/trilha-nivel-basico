package br.com.zup.primeiro.desafio.entity;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;

public class CustomerTest {

	@Mock
	private CustomerRepository repository;
	
	@InjectMocks
	private Customer customer;
	
	@Test
	public void testCreate() {
		String id = generatorUUID();
		
		Mockito.when(repository.save(new Customer()).getId()).thenReturn(id);
		
		
		Mockito.verify(customer).create(null, repository);
	}

	private String generatorUUID() {
		return UUID.randomUUID().toString();
	}


}

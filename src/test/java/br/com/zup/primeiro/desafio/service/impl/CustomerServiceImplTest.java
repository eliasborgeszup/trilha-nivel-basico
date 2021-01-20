package br.com.zup.primeiro.desafio.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.DocumentAlreadyExistsException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository repository;

	@InjectMocks
	private CustomerServiceImpl service;

	@Test
	public void createdSuccess() {
		Customer customer = customerObject();

		when(repository.save(any())).thenReturn(customer);

		when(service.create(createCustomerRequestObject())).thenReturn(customer.getId());

		assertFalse(repository.existsByCpf(customer.getCpf()));

		String messageReceived = service.create(createCustomerRequestObject());
		String expectedMessage = customer.getId();

		assertEquals(expectedMessage, messageReceived);
	}

	@Test
	public void createdUnsuccessfulExistingCPF() {
		when(repository.existsByCpf(any())).thenThrow(DocumentAlreadyExistsException.class);

		try {
			service.create(createCustomerRequestObject());
		} catch (DocumentAlreadyExistsException e) {
			assertEquals("m: created" + "cpf:",  e.getMessage());
		}

	}

	public CreateCustomerRequest createCustomerRequestObject() {
		CreateCustomerRequest request = new CreateCustomerRequest();

		request.setName("Elias");
		request.setBirthDate(LocalDate.now());
		request.setCpf("59522283053");
		request.setEmail("eliasborges@zup.com.br");
		request.setPhone("34992454428");
		request.setAddress("Rua X");

		return request;
	}

	public Customer customerObject() {
		return new Customer(UUID.randomUUID().toString(), "Elias", LocalDate.now(), "59522283053",
				"eliasborges@zup.com.br", "34992454428", "Rua X");
	}
}

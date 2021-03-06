package br.com.zup.primeiro.desafio.service.impl;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.DocumentAlreadyExistsException;
import br.com.zup.primeiro.desafio.exceptions.NotFoundException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    @Test
    public void shouldCreateCustomerAndReturnId() {
        // Given
        CreateCustomerRequest customerRequest = buildCreateCustomerRequest();
        Customer customer = buildCustomer();
        when(repository.save(any())).thenReturn(customer);

        // When
        String id = service.create(customerRequest);

        // Then
        assertEquals(id, customer.getId());
    }

    @Test(expected = DocumentAlreadyExistsException.class)
    public void shouldNotCreateCustomerWhenCpfExists() {
        // Given
        CreateCustomerRequest customerRequest = buildCreateCustomerRequest();
        when(repository.existsByCpf(customerRequest.getCpf())).thenReturn(true);

        // When
        service.create(customerRequest);
    }

    @Test
    public void findAllCustomersAndReturnList() {
        // Given
        Pageable page = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<Customer> customers = new PageImpl<Customer>(buildListCustomer());
        when(repository.findAll(page)).thenReturn(customers);

        // When
        Page<Customer> customersBD = service.findAll(page);

        // Then
        assertEquals(customers, customersBD);
    }

    @Test
    public void findByCpfAndReturnCustomer() {
        // Given
        Customer customer = buildCustomer();
        when(repository.findByCpf(buildCPF())).thenReturn(Optional.of(customer));

        // When
        Customer customerBD = service.findByCpf(buildCPF());

        // Then
        assertEquals(customer, customerBD);
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotFindCustomerByCpfWhenNotExists() {
        // Given
        when(repository.findByCpf(buildCPF())).thenReturn(Optional.empty());

        // When
        service.findByCpf(buildCPF());
    }

    @Test
    public void shouldUpdateCustomerAndReturnId() {
        // Given
        UpdateCustomerRequest customerRequest = buildUpdateCustomerRequest();
        Customer customer = buildCustomer();
        when(repository.findByCpf(buildCPF())).thenReturn(Optional.of(customer));
        when(repository.save(any())).thenReturn(customer);

        // When
        String id = service.update(buildCPF(), customerRequest);

        // Then
        assertEquals(id, customer.getId());
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotUpdateCustomerWhenCpfNotExists() {
        // Given
        UpdateCustomerRequest customerRequest = buildUpdateCustomerRequest();
        when(repository.findByCpf(buildCPF())).thenReturn(Optional.empty());

        // When
        service.update(buildCPF(), customerRequest);
    }

    @Test
    public void shouldDeleteCustomer() {
        // Given
        Customer customer = buildCustomer();
        when(repository.findByCpf(buildCPF())).thenReturn(Optional.of(customer));

        // When
        service.delete(buildCPF());

        // Then
        verify(repository).delete(customer);
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotDeleteCustomerWhenCpfNotExists() {
        // Given
        when(repository.findByCpf(buildCPF())).thenReturn(Optional.empty());

        // When
        service.delete(buildCPF());
    }

    public static Customer buildCustomer() {
        return new Customer(UUID.randomUUID().toString(), "Elias", LocalDate.now(), "59522283053",
                "eliasborges@zup.com.br", "34992454428", "Rua X");
    }

    private CreateCustomerRequest buildCreateCustomerRequest() {
        return new CreateCustomerRequest("Elias", LocalDate.now(), "59522283053", "eliasborges@zup.com.br",
                "34992454428", "Rua X");
    }

    private UpdateCustomerRequest buildUpdateCustomerRequest() {
        return new UpdateCustomerRequest("Elias", LocalDate.now(), "eliasborges@zup.com.br", "34992454428", "Rua X");
    }

    private List<Customer> buildListCustomer() {
        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer(UUID.randomUUID().toString(), "Elias", LocalDate.now(), "59522283053",
                "eliasborges@zup.com.br", "34992454428", "Rua X"));
        customers.add(new Customer(UUID.randomUUID().toString(), "Israel", LocalDate.now(), "86202508094",
                "israel.jesus2@zup.com.br", "34992454428", "Rua X"));

        return customers;
    }

    private String buildCPF() {
        return "59522283053";
    }
}

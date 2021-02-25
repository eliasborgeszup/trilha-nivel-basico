package br.com.zup.primeiro.desafio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.DocumentAlreadyExistsException;
import br.com.zup.primeiro.desafio.exceptions.NotFoundException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;
import br.com.zup.primeiro.desafio.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@AllArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    public String create(CreateCustomerRequest request) {
        if (repository.existsByCpf(request.getCpf())) {
            log.error("Customer not created, cpf customer = {} register", request.getCpf());

            throw new DocumentAlreadyExistsException(
                    format("CustomerServiceImpl: create, customer = {} register",
                            request.getCpf()));
        }

        log.info("Create customer = {}", request);
        return Customer.create(request, repository);
    }

    public Page<Customer> findAll(Pageable page) {
        return repository.findAll(page);
    }

    public Customer findByCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow(() -> {
            log.error("Customer not found, cpf customer = {} not found", cpf);

            throw new NotFoundException(format("CustomerServiceImpl: findByCpf, cpf customer = {} not found",
                    cpf));
        });
    }

    public Customer findById(String id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error("Customer not found, id customer = {} not found", id);

            throw new NotFoundException(format("CustomerServiceImpl: findById, id customer = {} not found",
                    id));
        });
    }

    public void delete(String cpf) {
        Customer customer = repository.findByCpf(cpf).orElseThrow(() -> {
            log.error("Customer not delete, cpfCustomer = {} not found", cpf);

            throw new NotFoundException(format("CustomerServiceImpl: delete, cpf customer = %s not found", cpf));
        });

        log.info("Delete customer = {}", customer);
        customer.delete(customer, repository);
    }

    public String update(String cpf, UpdateCustomerRequest request) {
        Customer customer = repository.findByCpf(cpf).orElseThrow(() -> {
            log.error("Costumer not update, costumer = {} not found", cpf);

            throw new NotFoundException(format("CustomerServiceImpl: update, cpfc customer = %s not found", cpf));
        });

        log.info("Update customer, cpf = {}, customerBefore = {}, customerAfter = {}",
                cpf,
                request,
                customer);

        return customer.update(request, repository);
    }
}
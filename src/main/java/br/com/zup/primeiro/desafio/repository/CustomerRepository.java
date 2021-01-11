package br.com.zup.primeiro.desafio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.primeiro.desafio.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID>{
	boolean existsByCpf(String cpf);
	
	Optional<Customer> findByCpf(String cpf);
	
	void deleteCustomerByCpf(String cpf);
}

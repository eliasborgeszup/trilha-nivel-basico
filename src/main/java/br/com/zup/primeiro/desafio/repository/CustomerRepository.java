package br.com.zup.primeiro.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.primeiro.desafio.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{
	boolean existsByCpf(String cpf);
	
	Customer findByCpf(String cpf);
}

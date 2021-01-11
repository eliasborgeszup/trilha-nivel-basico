package br.com.zup.primeiro.desafio.entity;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.zup.primeiro.desafio.dto.CreateCustomerDTO;
import br.com.zup.primeiro.desafio.dto.UpdateCustomerDTO;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Customer")
public class Customer {
	@Id
	@Column(updatable = false, unique = true, nullable = false)
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(nullable = false, unique = true, length = 11)
	private String cpf;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false, length = 11)
	private String phone;

	@Column(nullable = false)
	private String address;
	
	public String create(CreateCustomerDTO createCustomerDTO, CustomerRepository repository) {
		this.id = UUID.randomUUID().toString();
		this.name = createCustomerDTO.getName();
		this.birthDate = createCustomerDTO.getBirthDate();
		this.cpf = createCustomerDTO.getCpf();
		this.email = createCustomerDTO.getEmail();
		this.phone = createCustomerDTO.getPhone();
		this.address = createCustomerDTO.getAddress();
		
		return repository.save(this).getId();
	}
	
	public String update(UpdateCustomerDTO customerDTO, CustomerRepository repository) {
		this.name = customerDTO.getName();
		this.birthDate = customerDTO.getBirthDate();
		this.email = customerDTO.getEmail();
		this.phone = customerDTO.getPhone();
		this.address = customerDTO.getAddress();
		
		return repository.save(this).getId();
	}
}

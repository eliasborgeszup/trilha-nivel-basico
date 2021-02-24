package br.com.zup.primeiro.desafio.entity;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.zup.primeiro.desafio.controller.request.customer.CreateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.request.customer.UpdateCustomerRequest;
import br.com.zup.primeiro.desafio.controller.response.customer.CustomerResponse;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
@ToString
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

	public String create(CreateCustomerRequest request, CustomerRepository repository) {
		this.id = UUID.randomUUID().toString();
		this.name = request.getName();
		this.birthDate = request.getBirthDate();
		this.cpf = request.getCpf();
		this.email = request.getEmail();
		this.phone = request.getPhone();
		this.address = request.getAddress();

		return repository.save(this).id;
	}

	public String update(UpdateCustomerRequest request, CustomerRepository repository) {
		this.name = request.getName();
		this.birthDate = request.getBirthDate();
		this.email = request.getEmail();
		this.phone = request.getPhone();
		this.address = request.getAddress();

		return repository.save(this).id;
	}

	public void delete(Customer customer, CustomerRepository repository) {
		repository.delete(customer);
	}
}

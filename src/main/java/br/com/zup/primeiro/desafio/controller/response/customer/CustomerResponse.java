package br.com.zup.primeiro.desafio.controller.response.customer;

import java.time.LocalDate;

import br.com.zup.primeiro.desafio.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerResponse {
	private String id;
	private String name;
	private LocalDate birthDate;
	private String cpf;
	private String email;
	private String phone;
	private String address;

	public CustomerResponse(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.birthDate = customer.getBirthDate();
		this.cpf = customer.getCpf();
		this.email = customer.getEmail();
		this.phone = customer.getPhone();
		this.address = customer.getAddress();
	}
}

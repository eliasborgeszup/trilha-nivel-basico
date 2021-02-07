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

	public static CustomerResponse fromCustomer(Customer customer) {
		return new CustomerResponse(customer.getId(), customer.getName(), customer.getBirthDate(), customer.getCpf(),
				customer.getEmail(), customer.getPhone(), customer.getAddress());
	}
}

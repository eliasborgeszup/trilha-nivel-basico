package br.com.zup.primeiro.desafio.controller.response.customer;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.zup.primeiro.desafio.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
	private String id;
	private String name;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
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

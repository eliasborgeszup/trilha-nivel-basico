package br.com.zup.primeiro.desafio.controller.request.customer;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateCustomerRequest {
	@NotBlank(message = "{validation.blank}")
	private String name;

	@Past(message = "{validation.invalid.date}")
	private LocalDate birthDate;

	@CPF(message = "{validation.invalid.cpf}")
	private String cpf;

	@Email(message = "{validation.invalid.email}")
	@NotBlank(message = "{validation.blank}")
	private String email;

	@Pattern(regexp = "^[0-9]*", message = "{validation.phone}")
	@Size(min = 10, max = 11, message = "{validation.size}")
	private String phone;

	@NotBlank(message = "{validation.blank}")
	private String address;
}

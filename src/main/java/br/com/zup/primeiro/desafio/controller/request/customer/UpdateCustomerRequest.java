package br.com.zup.primeiro.desafio.controller.request.customer;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCustomerRequest {
	@NotBlank(message = "{validation.blank}")
	private String name;

	@Past(message = "{validation.invalid.date}")
	private LocalDate birthDate;

	@Email(message = "{validation.invalid.email}")
	@NotBlank(message = "{validation.blank}")
	private String email;

	@Pattern(regexp = "^[0-9]*", message = "{validation.phone}")
	@Size(min = 10, max = 11, message = "{validation.size}")
	private String phone;

	@NotBlank(message = "{validation.blank}")
	private String address;
}

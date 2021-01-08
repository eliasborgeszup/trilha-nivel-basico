package br.com.zup.primeiro.desafio.dto;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class CustomerDTO {
	@NotBlank(message = "{validation.blank}")
	private String name;

	@Future(message = "{validation.invalid.date}")
	private Date birthDate;

	@Email(message = "{validation.invalid.email}")
	@NotBlank(message = "{validation.blank}")
	private String email;

	@Pattern(regexp = "^[0-9]*", message = "{validation.phone}")
	@Size(min = 10, max = 11, message = "{validation.size}")
	private String phone;

	@NotBlank(message = "{validation.blank}")
	private String adress;
}

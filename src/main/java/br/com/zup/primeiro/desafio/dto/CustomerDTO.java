package br.com.zup.primeiro.desafio.dto;

import java.sql.Date;

import lombok.Getter;

@Getter
public class CustomerDTO {
	private String nome;
	private Date birthDate;
	private String email;
	private String phone;
	private String adress;
}

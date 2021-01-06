package br.com.zup.primeiro.desafio.dto;

import java.sql.Date;

import lombok.Getter;

@Getter
public class ClienteDTO {
	private String nome;
	private Date dataNascimento;
	private String email;
	private String telefone;
	private String endereco;
}

package br.com.zup.primeiro.desafio.pojo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@AllArgsConstructor
@NoArgsConstructor
public class ClientePOJO {
	private String nome;
	private Date dataNascimento;
	private String cpf;
	private String email;
	private String telefone;
	private String endereco;
}

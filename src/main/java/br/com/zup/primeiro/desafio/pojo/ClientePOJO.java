package br.com.zup.primeiro.desafio.pojo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

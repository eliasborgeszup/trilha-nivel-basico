package br.com.zup.primeiro.desafio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.connection.factory.ConnectionFactory;
import br.com.zup.primeiro.desafio.dto.MensagemDTO;
import br.com.zup.primeiro.desafio.pojo.ClientePOJO;

@Service
public class ClienteDAO {
	private static final String OPERACAO_NAO_REALIZADA = "Infelizmente não foi possivel realizar a operação.";
	private static final String CLIENTE_CADASTRADO_COM_SUCESSO = "Cliente cadastrado com sucesso!";
	private Connection conn;

	public ClienteDAO() {
		this.conn = new ConnectionFactory().obterConexao();
	}

	public MensagemDTO adicionarCliente(ClientePOJO cliente) {
		String sqlInserirCliente = "INSERT INTO cliente (nome, data_nascimento, cpf, email, telefone, endereco)"
				+ "values (?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = conn.prepareStatement(sqlInserirCliente);

			stmt.setString(1, cliente.getNome());
			stmt.setDate(2, cliente.getDataNascimento());
			stmt.setString(3, cliente.getCpf());
			stmt.setString(4, cliente.getEmail());
			stmt.setString(5, cliente.getTelefone());
			stmt.setString(6, cliente.getEndereco());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			return new MensagemDTO(OPERACAO_NAO_REALIZADA + e.getMessage());
		}

		return new MensagemDTO(CLIENTE_CADASTRADO_COM_SUCESSO);
	}

}

package br.com.zup.primeiro.desafio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.connection.factory.ConnectionFactory;
import br.com.zup.primeiro.desafio.dto.MensagemDTO;
import br.com.zup.primeiro.desafio.pojo.ClientePOJO;

@Service
public class ClienteDAO {
	private static final String CPF_INVALIDO = "CPF Invalido!";
	private static final String OPERACAO_NAO_REALIZADA = "Infelizmente não foi possivel realizar a operação.";
	private static final String CLIENTE_CADASTRADO_COM_SUCESSO = "Cliente cadastrado com sucesso!";
	private Connection conexao;

	public ClienteDAO() {
		this.conexao = new ConnectionFactory().obterConexao();
	}

	public MensagemDTO adicionarCliente(ClientePOJO cliente) {
		String sqlInserirCliente = "INSERT INTO cliente (nome, data_nascimento, cpf, email, telefone, endereco)"
				+ "values (?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = conexao.prepareStatement(sqlInserirCliente);

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

	public List<ClientePOJO> listarClientes() throws SQLException {
		List<ClientePOJO> clientes = new ArrayList<>();

		String consultarClientesSql = "SELECT * FROM cliente";

		try {
			PreparedStatement stmt = conexao.prepareStatement(consultarClientesSql);

			ResultSet rs = stmt.executeQuery();
			
			//CASO NÃO TENHA CLIENTE THROWS
			
			while (rs.next()) {
				ClientePOJO cliente = instanciarCidade(rs);
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			throw new SQLException(OPERACAO_NAO_REALIZADA + e.getMessage());
		}
		return clientes;
	}

	public ClientePOJO listarClientePorCpf(String cpf) throws SQLException {
		ClientePOJO cliente = new ClientePOJO();

		String consultarClientePorCpf = "SELECT * FROM cliente WHERE cliente.cpf = ?";

		try {
			PreparedStatement stmt = conexao.prepareStatement(consultarClientePorCpf);
			stmt.setString(1, cpf);

			ResultSet rs = stmt.executeQuery();
			
			if(rs.next() == false) {
				throw new SQLException(CPF_INVALIDO);
			}
			
			while (rs.next()) {
				cliente = instanciarCidade(rs);
			}
		} catch (SQLException e) {
			throw new SQLException(OPERACAO_NAO_REALIZADA + e.getMessage());
		}

		return cliente;
	}

	public ClientePOJO instanciarCidade(ResultSet rs) throws SQLException {
		ClientePOJO cliente = new ClientePOJO();

		cliente.setNome(rs.getString("nome"));
		cliente.setDataNascimento(rs.getDate("data_nascimento"));
		cliente.setCpf(rs.getString("cpf"));
		cliente.setEmail(rs.getString("email"));
		cliente.setTelefone(rs.getString("telefone"));
		cliente.setEndereco(rs.getString("endereco"));

		return cliente;
	}

}

package br.com.zup.primeiro.desafio.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.primeiro.desafio.dao.ClienteDAO;
import br.com.zup.primeiro.desafio.dto.MensagemDTO;
import br.com.zup.primeiro.desafio.pojo.ClientePOJO;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	ClienteDAO dao;
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public MensagemDTO adicionarCliente(@RequestBody ClientePOJO cliente) {
		return dao.adicionarCliente(cliente);
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<ClientePOJO> listarClientes() throws SQLException{
		return dao.listarClientes();
	}
	
	@GetMapping(value = "/{cpf}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ClientePOJO listarClientePorCpf(@PathVariable String cpf) throws SQLException {
		return dao.listarClientePorCpf(cpf);
	}
}

package br.com.zup.primeiro.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.primeiro.desafio.dao.ClienteDAO;
import br.com.zup.primeiro.desafio.dto.ClienteDTO;
import br.com.zup.primeiro.desafio.dto.MensagemDTO;
import br.com.zup.primeiro.desafio.exceptions.GenericException;
import br.com.zup.primeiro.desafio.pojo.ClientePOJO;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	ClienteDAO dao;

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO adicionarCliente(@RequestBody ClientePOJO cliente) {
		return dao.adicionarCliente(cliente);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<ClientePOJO> listarClientes() throws GenericException {
		return dao.listarClientes();
	}

	@GetMapping(value = "/{cpf}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ClientePOJO listarClientePorCpf(@PathVariable String cpf) throws GenericException {
		return dao.listarClientePorCpf(cpf);
	}

	@PutMapping(value = "/{cpf}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO alterarCliente(@PathVariable String cpf, @RequestBody ClienteDTO cliente)
			throws GenericException {
		return dao.alterarCliente(cpf, cliente);
	}

	@DeleteMapping(value = "/{cpf}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO excluirCliente(@PathVariable String cpf) throws GenericException {
		return dao.excluirCliente(cpf);
	}
}

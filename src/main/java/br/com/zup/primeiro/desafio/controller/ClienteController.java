package br.com.zup.primeiro.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
}

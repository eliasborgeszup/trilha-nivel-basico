package br.com.zup.primeiro.desafio.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.primeiro.desafio.controller.response.marvel.MarvelResponse;
import br.com.zup.primeiro.desafio.service.MarvelComicsService;

@RestController
@RequestMapping(value = "/marvel")
public class MarvelController {

	MarvelComicsService service;

	public MarvelController(MarvelComicsService service) {
		this.service = service;
	}

	@ResponseStatus(OK)
	@GetMapping(value = "/comics")
	public MarvelResponse findAll() {
		return service.findAll();
	}
}

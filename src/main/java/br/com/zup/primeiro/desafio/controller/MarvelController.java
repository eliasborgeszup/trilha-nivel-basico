package br.com.zup.primeiro.desafio.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.primeiro.desafio.controller.response.marvel.ComicsResponse;
import br.com.zup.primeiro.desafio.service.MarvelComicsService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/marvel")
public class MarvelController {

	private MarvelComicsService service;

	@ResponseStatus(OK)
	@GetMapping(value = "/comics")
	public ComicsResponse findAll() {
		return service.findAll();
	}
}

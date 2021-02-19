package br.com.zup.primeiro.desafio.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.client.MarvelComicsClient;
import br.com.zup.primeiro.desafio.controller.response.marvel.ComicsResponse;
import br.com.zup.primeiro.desafio.service.MarvelComicsService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MarvelComicsServiceImpl implements MarvelComicsService {
	private static final String PUBLIC_KEY = "de8ae6b7ddc90c4bbcaba79435515c1a";
	private static final String PRIVATE_KEY = "36411abdd8d7167a1f98760edadb5189be6b339d";

	private MarvelComicsClient client;

	public ComicsResponse findAll() {
		Long timeStamp = new Date().getTime();
		
		return client.findAll(timeStamp, PUBLIC_KEY, buildHash(timeStamp));
	}

	private String buildHash(Long timeStamp) {
		return DigestUtils.md5Hex(timeStamp + PRIVATE_KEY + PUBLIC_KEY);
	}
}
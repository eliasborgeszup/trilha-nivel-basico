package br.com.zup.primeiro.desafio.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import br.com.zup.primeiro.desafio.client.MarvelClient;
import br.com.zup.primeiro.desafio.controller.response.marvel.MarvelResponse;
import br.com.zup.primeiro.desafio.service.MarvelComicsService;

@Service
public class MarvelComicsServiceImpl implements MarvelComicsService {
	private static final String PUBLIC_KEY = "de8ae6b7ddc90c4bbcaba79435515c1a";
	private static final String PRIVATE_KEY = "36411abdd8d7167a1f98760edadb5189be6b339d";

	private MarvelClient client;

	public MarvelComicsServiceImpl(MarvelClient client) {
		this.client = client;
	}

	private Long timeStamp = new Date().getTime();

	public MarvelResponse findAll() {
		return client.getAll(timeStamp, PUBLIC_KEY, buildHash());
	}

	public String buildHash() {
		return DigestUtils.md5Hex(timeStamp + PRIVATE_KEY + PUBLIC_KEY);
	}
}

package br.com.zup.primeiro.desafio.service.impl;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.primeiro.desafio.client.MarvelComicsClient;
import br.com.zup.primeiro.desafio.controller.response.marvel.DataResponse;
import br.com.zup.primeiro.desafio.controller.response.marvel.ComicsResponse;
import br.com.zup.primeiro.desafio.controller.response.marvel.ResultsResponse;

@RunWith(MockitoJUnitRunner.class)
public class MarvelComicsServiceImplTest {

	@Mock
	MarvelComicsClient client;

	@InjectMocks
	MarvelComicsServiceImpl service;

	@Test
	public void findAllCustomersAndReturnMarvelResponse() {
		// Given 
		ComicsResponse comics = buildComicsResponse();
		when(client.findAll(any(), any(), any())).thenReturn(comics);

		// When 
		ComicsResponse comicsClient = service.findAll();

		// Then 
		assertEquals(comics, comicsClient);

	}

	private ComicsResponse buildComicsResponse() {
		List<ResultsResponse> results = buildListResultsResponse();
		DataResponse data = new DataResponse(results);

		return new ComicsResponse("© 2021 MARVEL",
				"<a href=\"http://marvel.com\">Data provided by Marvel. © 2021 MARVEL</a>", data);
	}

	private List<ResultsResponse> buildListResultsResponse() {
		List<ResultsResponse> results = new ArrayList<>();

		ResultsResponse result82967 = new ResultsResponse(82967L, "Marvel Previews (2017)");
		ResultsResponse result82970 = new ResultsResponse(82970L, "Marvel Previews (2017)");

		results.add(result82967);
		results.add(result82970);

		return results;
	}
}

package br.com.zup.primeiro.desafio.controller.response.marvel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class DataResponse {
	private List<ResultsResponse> results;
}

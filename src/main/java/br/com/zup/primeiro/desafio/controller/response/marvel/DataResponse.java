package br.com.zup.primeiro.desafio.controller.response.marvel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DataResponse {
	private List<ResultsResponse> results;
}

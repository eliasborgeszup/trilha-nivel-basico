package br.com.zup.primeiro.desafio.controller.response.marvel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ResultsResponse {
	private Long id;
	private String title;
	private String resourceURI;
}

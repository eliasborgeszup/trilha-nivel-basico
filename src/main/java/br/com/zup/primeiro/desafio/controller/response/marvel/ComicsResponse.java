package br.com.zup.primeiro.desafio.controller.response.marvel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ComicsResponse {
	private String copyright;
	private String attributionHTML;
	
	private DataResponse data;
}

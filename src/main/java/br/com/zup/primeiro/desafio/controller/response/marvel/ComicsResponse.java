package br.com.zup.primeiro.desafio.controller.response.marvel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ComicsResponse {
	private String copyright;
	private String attributionHTML;
	
	private DataResponse data;
}

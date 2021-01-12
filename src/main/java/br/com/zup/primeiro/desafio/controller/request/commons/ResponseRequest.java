package br.com.zup.primeiro.desafio.controller.request.commons;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class ResponseRequest {
	private String mensagem;
}

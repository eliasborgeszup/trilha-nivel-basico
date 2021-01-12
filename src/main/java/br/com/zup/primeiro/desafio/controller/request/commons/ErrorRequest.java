package br.com.zup.primeiro.desafio.controller.request.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorRequest {
	private String mensagem;
}

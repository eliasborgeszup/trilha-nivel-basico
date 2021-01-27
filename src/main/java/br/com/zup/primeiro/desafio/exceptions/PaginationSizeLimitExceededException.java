package br.com.zup.primeiro.desafio.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaginationSizeLimitExceededException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message;
}

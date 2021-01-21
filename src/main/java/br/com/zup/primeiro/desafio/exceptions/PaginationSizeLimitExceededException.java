package br.com.zup.primeiro.desafio.exceptions;

public class PaginationSizeLimitExceededException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PaginationSizeLimitExceededException(String message) {
		super(message);
	}
}

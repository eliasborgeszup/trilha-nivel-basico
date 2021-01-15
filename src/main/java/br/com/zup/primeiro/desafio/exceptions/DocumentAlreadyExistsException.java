package br.com.zup.primeiro.desafio.exceptions;

public class DocumentAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DocumentAlreadyExistsException(String message) {
		super(message);
	}
}

package br.com.zup.primeiro.desafio.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.zup.primeiro.desafio.dto.ResponseDTO;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ GenericException.class })
	public @ResponseBody ResponseDTO handlerRegrasDeNegocio(GenericException e) {
		return new ResponseDTO(e.getMessage());
	}
}

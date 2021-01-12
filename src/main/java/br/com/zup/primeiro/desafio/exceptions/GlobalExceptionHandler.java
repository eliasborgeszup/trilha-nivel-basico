package br.com.zup.primeiro.desafio.exceptions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import static java.util.Objects.nonNull;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.zup.primeiro.desafio.controller.request.commons.ErrorRequest;
import br.com.zup.primeiro.desafio.controller.request.commons.ResponseRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	public static final int STR_FIELD_NAME = 0;
	public static final int IGNORE_DOT_POST = 1;

	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody List<ErrorRequest> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		List<ErrorRequest> validationErrors = new ArrayList<>();

		for (ObjectError error : e.getBindingResult().getAllErrors()) {

			if (nonNull(error.getCodes()) && nonNull(error.getCodes()[STR_FIELD_NAME])) {
				String fieldName = error.getCodes()[STR_FIELD_NAME];

				StringBuilder messageDisplayed = new StringBuilder();
				messageDisplayed.append("[");
				messageDisplayed
						.append(fieldName.substring(fieldName.lastIndexOf(".") + IGNORE_DOT_POST).toUpperCase());
				messageDisplayed.append("] - ");
				messageDisplayed.append(error.getDefaultMessage());

				validationErrors.add(new ErrorRequest(messageDisplayed.toString()));
			}
		}

		return validationErrors;
	}

	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler({ NotFoundException.class })
	public @ResponseBody ResponseRequest handlerBusinessRules(NotFoundException exception) {
		return new ResponseRequest(exception.getMessage());
	}

	@ResponseStatus(UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ DocumentAlreadyExistsException.class })
	public @ResponseBody ResponseRequest handlerBusinessRules(DocumentAlreadyExistsException exception) {
		return new ResponseRequest(exception.getMessage());
	}
}

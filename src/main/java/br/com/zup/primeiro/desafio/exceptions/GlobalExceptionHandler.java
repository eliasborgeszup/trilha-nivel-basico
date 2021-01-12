package br.com.zup.primeiro.desafio.exceptions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import static java.util.Objects.nonNull;

import static br.com.zup.primeiro.desafio.constant.Constant.STR_FIELD_NAME;
import static br.com.zup.primeiro.desafio.constant.Constant.IGNORE_DOT_POST;

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

	@ResponseStatus(UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ GenericException.class })
	public @ResponseBody ResponseRequest handlerBusinessRules(GenericException e) {
		return new ResponseRequest(e.getMessage());
	}
}

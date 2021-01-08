package br.com.zup.primeiro.desafio.exceptions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import static java.util.Objects.nonNull;

import br.com.zup.primeiro.desafio.dto.ErrorDTO;
import br.com.zup.primeiro.desafio.dto.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final int STR_FIELD_NAME = 0;
	private static final int IGNORE_DOT_POST = 1;

	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody List<ErrorDTO> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		List<ErrorDTO> validationErrors = new ArrayList<>();

		for (ObjectError error : e.getBindingResult().getAllErrors()) {

			if (nonNull(error.getCodes()) && nonNull(error.getCodes()[STR_FIELD_NAME])) {
				String fieldName = error.getCodes()[STR_FIELD_NAME];

				StringBuilder messageDisplayed = new StringBuilder();
				messageDisplayed.append("[");
				messageDisplayed
						.append(fieldName.substring(fieldName.lastIndexOf(".") + IGNORE_DOT_POST).toUpperCase());
				messageDisplayed.append("] - ");
				messageDisplayed.append(error.getDefaultMessage());

				validationErrors.add(new ErrorDTO(messageDisplayed.toString()));
			}
		}

		return validationErrors;
	}

	@ResponseStatus(UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ GenericException.class })
	public @ResponseBody ResponseDTO handlerBusinessRules(GenericException e) {
		return new ResponseDTO(e.getMessage());
	}
}

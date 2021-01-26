package br.com.zup.primeiro.desafio.exceptions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import static java.util.Objects.nonNull;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.zup.primeiro.desafio.controller.response.commons.ErrorResponse;
import br.com.zup.primeiro.desafio.controller.response.commons.ResponseResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	public static final int STR_FIELD_NAME = 0;
	public static final int IGNORE_DOT_POST = 1;

	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody List<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

		List<ErrorResponse> validationErrors = new ArrayList<>();

		for (ObjectError error : exception.getBindingResult().getAllErrors()) {

			if (nonNull(error.getCodes()) && nonNull(error.getCodes()[STR_FIELD_NAME])) {
				String fieldName = error.getCodes()[STR_FIELD_NAME];

				StringBuilder messageDisplayed = new StringBuilder();
				messageDisplayed.append("[");
				messageDisplayed
						.append(fieldName.substring(fieldName.lastIndexOf(".") + IGNORE_DOT_POST).toUpperCase());
				messageDisplayed.append("] - ");
				messageDisplayed.append(error.getDefaultMessage());

				validationErrors.add(new ErrorResponse(messageDisplayed.toString()));
			}
		}

		return validationErrors;
	}

	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler({ NotFoundException.class })
	public @ResponseBody ResponseResponse handlerBusinessRules(NotFoundException exception) {
		log.error(exception.getMessage());
		return new ResponseResponse("Documento não encontrado.");
	}

	@ResponseStatus(UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ DocumentAlreadyExistsException.class })
	public @ResponseBody ResponseResponse handlerBusinessRules(DocumentAlreadyExistsException exception) {
		log.error(exception.getMessage());
		return new ResponseResponse("Documento já existente.");
	}
	
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler({ PaginationSizeLimitExceededException.class })
	public @ResponseBody ResponseResponse handlerBusinessRules(PaginationSizeLimitExceededException exception) {
		log.error(exception.getMessage());
		return new ResponseResponse("Quantidade de paginas maior que o permitido.");
	}

	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public @ResponseBody ErrorResponse runtimeExceptionError(RuntimeException exception) {
		log.error(exception.getMessage());
		return new ErrorResponse("Erro interno no servidor, contate o administrador do sistema.");
	}

}

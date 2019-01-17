package com.ceiba.estacionamiento.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.validation.ConstraintViolationException;

/**
 * clase para controlar las excepciones de manera global
 * 
 * @author jhon.bedoya
 *
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
	/**
	 * método para retornar una excepcion al front
	 * 
	 * @param ex, la excepcion generada
	 * @return ApiErrorResponse, con la informacion del error
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse constraintViolationException(ConstraintViolationException ex) {
		return new ApiErrorResponse(500, 5001, ex.getMessage());
	}

	/**
	 * método para retornar una excepcion al front
	 * 
	 * @param ex, la excepcion generada
	 * @return ApiErrorResponse, con la informacion del error
	 */
	@ExceptionHandler(value = { NoHandlerFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponse noHandlerFoundException(Exception ex) {
		return new ApiErrorResponse(404, 4041, ex.getMessage());
	}

	/**
	 * método para retornar una excepcion al front
	 * 
	 * @param ex, la excepcion generada
	 * @return ApiErrorResponse, con la informacion del error
	 */
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiErrorResponse unknownException(Exception ex) {
		return new ApiErrorResponse(500, 5002, ex.getMessage());
	}

}

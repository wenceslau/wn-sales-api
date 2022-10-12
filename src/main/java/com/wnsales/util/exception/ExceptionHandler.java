
package com.wnsales.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que intercepta as excecoes do sistema e devolve respostas http conforme o Erro
 * @author Wenceslau
 *
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Execeção para mensagens formato JSON invalidas
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers,
																  final HttpStatus status, final WebRequest request) {
		String msgs = "Invalid format. ";// + exceptionMessage(ex);
		return handleExceptionInternal(ex, new Error(msgs,HttpStatus.UNPROCESSABLE_ENTITY), headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers,
																  final HttpStatus status, final WebRequest request) {

		final List<String> errors = new ArrayList<>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		String msgs = "Required fields. " + String.join(". ", errors);

		return handleExceptionInternal(ex,  new Error(msgs,HttpStatus.BAD_REQUEST), new HttpHeaders(),  HttpStatus.BAD_REQUEST, request);
	}




	// Execeção para Null Pointer
	@org.springframework.web.bind.annotation.ExceptionHandler({ NullPointerException.class })
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
		String msgs = "Internal failure.";// + exceptionMessage(ex);
		return handleExceptionInternal(ex, new Error(msgs,HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

	}

	// Execeção para recursos nao encontrado
	@org.springframework.web.bind.annotation.ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String msgs = exceptionMessage(ex);
		return handleExceptionInternal(ex,  new Error(msgs,HttpStatus.NOT_FOUND), new HttpHeaders(),  HttpStatus.NOT_FOUND, request);
	}

	// Execeção para erros de PK no banco
	@org.springframework.web.bind.annotation.ExceptionHandler({ DataIntegrityViolationException.class, JpaObjectRetrievalFailureException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
		String msgs = "This resource is in used. ";//+ exceptionMessage(ex);
		return handleExceptionInternal(ex,  new Error(msgs,HttpStatus.BAD_REQUEST), new HttpHeaders(),  HttpStatus.BAD_REQUEST, request);
	}

	// Execeção genericas
	@org.springframework.web.bind.annotation.ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		String msgs = exceptionMessage(ex);
		return handleExceptionInternal(ex,  new Error(msgs,HttpStatus.BAD_REQUEST), new HttpHeaders(),  HttpStatus.BAD_REQUEST, request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		String msgs = exceptionMessage(ex);
		return handleExceptionInternal(ex,  new Error(msgs,HttpStatus.BAD_REQUEST), new HttpHeaders(),  HttpStatus.BAD_REQUEST, request);
	}


	private String exceptionMessage(Exception ex) {
		logger.error("Error on API: ", ex);
		return ex.getMessage();
	}

	public static class Error {

		private final String message;
		private final Integer httpCode;
		private final HttpStatus httpStatus;

		public Error(String message, HttpStatus httpStatus) {
			super();
			this.message = message;
			this.httpStatus = httpStatus;
			this.httpCode = httpStatus.value();
		}

		public String getMessage() {
			return message;
		}

		public HttpStatus getHttpStatus() {
			return httpStatus;
		}

		public Integer getHttpCode() {
			return httpCode;
		}
	}

}

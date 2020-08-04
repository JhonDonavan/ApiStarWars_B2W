package com.starwars.b2w.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PlanetExceptionHandler extends ResponseEntityExceptionHandler {

	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler({PlanetNotFoundException.class})
	public ResponseEntity<Object> planetNotFoundException(PlanetNotFoundException ex, WebRequest request){
	
		ex.setMessage(messageSource.getMessage("message.planet.notfound", new Object[] {ex.getId()}, LocaleContextHolder.getLocale()));
		
		return handleExceptionInternal(ex, new ResponseErrorBuild(ex.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), 
										ex.toString()) , new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String message = messageSource.getMessage("msg.json.invalid", null, LocaleContextHolder.getLocale());
		
		return handleExceptionInternal(ex, new ResponseErrorBuild(message, status.value(), status.name(), ex.getCause().toString()), headers, status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		String message = messageSource.getMessage("message.planet.invalid.fiels", null, LocaleContextHolder.getLocale());
	
		return handleExceptionInternal(ex, new ResponseErrorBuild(message, status.value(), status.name(), ex.getBindingResult().getAllErrors().toString()), headers, status, request);
	}
	
}

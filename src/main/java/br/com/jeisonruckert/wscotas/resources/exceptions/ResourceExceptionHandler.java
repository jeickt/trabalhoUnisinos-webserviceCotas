package br.com.jeisonruckert.wscotas.resources.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.jeisonruckert.wscotas.services.exceptions.FileReaderException;
import br.com.jeisonruckert.wscotas.services.exceptions.InvalidNumberOfCourseVacancyException;
import br.com.jeisonruckert.wscotas.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(FileReaderException.class)
	public ResponseEntity<StandardError> iO(FileReaderException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Load file error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<StandardError> negativeValue(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Invalid number of course vacancy.";
		HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(InvalidNumberOfCourseVacancyException.class)
	public ResponseEntity<StandardError> negativeValue(InvalidNumberOfCourseVacancyException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Invalid number of course vacancy.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
	    Map<String, String> errors = new HashMap<>();
	    HttpStatus status = HttpStatus.BAD_REQUEST;
	    
	    e.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    
	    StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<StandardError> iO(MissingServletRequestParameterException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Required request parameter.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

}

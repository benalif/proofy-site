package dz.wta.web.ooredoo.frontend.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import dz.wta.web.ooredoo.frontend.model.GenericResponse;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

	@Autowired
	Environment env;

	private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	private static String FATAL_ERROR = "Une erreur inattendue s'est produite";
	private static String PAGE_NOT_FOUND = "La ressource demandée n'existe pas";

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<GenericResponse> noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException exception) {
		GenericResponse response = new GenericResponse(PAGE_NOT_FOUND, 404);
		return new ResponseEntity<GenericResponse>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericResponse> globalException(HttpServletRequest request, Exception exception) {
		exception.printStackTrace();
		LOGGER.error(exception.getMessage());
		GenericResponse response = new GenericResponse(FATAL_ERROR, 500);
		return new ResponseEntity<GenericResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

package com.mynotes.api.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler  {

	/**
     * Handle BusinessException. 
     * Triggered when BusinessException Occurs in code
     *
     * @param ex      BusinessException
     * @param request WebRequest
     * @return the ResponseEntity object
     */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorDTO> handleFeignBusinessException(BusinessException exception,WebRequest request){
		ErrorDTO errorDto = new ErrorDTO();
		errorDto.setTimestamp(Date.from(Instant.now()));
		errorDto.setErrorCode(exception.getErrorCode());
		errorDto.setErrorsMessage(Arrays.asList(exception.getErrorMessage()));
		
		HttpStatus status;
		switch (exception.getHttpCode()) {
		case 400:
			status = HttpStatus.BAD_REQUEST;
			errorDto.setStatus(HttpStatus.BAD_REQUEST.toString());
			break;
		case 401:
			status = HttpStatus.UNAUTHORIZED;
			errorDto.setStatus(HttpStatus.UNAUTHORIZED.toString());
			break;
		case 404:
			status = HttpStatus.NOT_FOUND;
			errorDto.setStatus(HttpStatus.NOT_FOUND.toString());
			break;
		case 500:
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			errorDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			break;
		case 503:
			status = HttpStatus.SERVICE_UNAVAILABLE;
			errorDto.setStatus(HttpStatus.SERVICE_UNAVAILABLE.toString());
			break;
		default:
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			errorDto.setStatus("UNKNOWN_ERROR");
			break;
		}
		
		return ResponseEntity.status(status).body(errorDto);
	}
	
	/**
     * Handle MethodArgumentNotValidException. 
     * Triggered when @Valid fails
     *
     * @param ex      MethodArgumentNotValidException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ResponseEntity object
     */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDTO errorDto = new ErrorDTO();
        errorDto.setTimestamp(Date.from(Instant.now()));
        errorDto.setErrorCode("ERROR-01");
        errorDto.setStatus(status.toString());
        errorDto.setErrorsMessage(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
	}
	
	/**
     * Handle HttpMessageNotReadableException. 
     * Triggered when requestBody is Empty
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ResponseEntity object
     */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDTO errorDto = new ErrorDTO();        
        errorDto.setTimestamp(Date.from(Instant.now()));
        errorDto.setErrorCode("ERROR-02");
        errorDto.setStatus(status.toString());        
        List<String> list= new ArrayList<>();
        list.add("Request has empty body");
        errorDto.setErrorsMessage(list);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
	
	/**
     * Handle ServletRequestBindingException. 
     * Triggered when a 'required' request header parameter is missing.
     *
     * @param ex      ServletRequestBindingException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ResponseEntity object
     */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {	
    	ErrorDTO errorDto = new ErrorDTO();        
        errorDto.setTimestamp(Date.from(Instant.now()));
        errorDto.setErrorCode("ERROR-03");
        errorDto.setStatus(status.toString());
        List<String> list= new ArrayList<>();
        list.add(ex.getMessage());
        errorDto.setErrorsMessage(list);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
    
	/**
     * Handle MissingServletRequestParameterException
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ResponseEntity object
     */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, 
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDTO errorDto = new ErrorDTO();        
        errorDto.setTimestamp(Date.from(Instant.now()));
        errorDto.setErrorCode("ERROR-04");
        errorDto.setStatus(status.toString());
        List<String> list= new ArrayList<>();
        list.add(ex.getParameterName()+" parameter is missing");
        errorDto.setErrorsMessage(list);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}

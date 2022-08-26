package com.mynotes.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 4L;
	
	private String errorCode;
	private String errorMessage;
	private Integer httpCode;
	
	public BusinessException(String errorCode, String errorMessage, Integer httpCode, Throwable t) {
		super(errorCode, t);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpCode = httpCode;
	}
}

package com.mynotes.api.exception;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
	private Date timestamp;
	private String status;
	private String errorCode;
	private List<String> errorsMessage;
}

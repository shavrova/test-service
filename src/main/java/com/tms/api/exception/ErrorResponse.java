package com.tms.api.exception;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {

	private int status;
	private String error;
	private String exception;
	private String message;
	@Setter(AccessLevel.NONE)
	private Date datetime;


	public ErrorResponse(HttpStatus status, Exception ex) {
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.exception = ex.getClass().getCanonicalName();
		this.message = ex.getMessage();
		this.datetime = new Date();
	}


}








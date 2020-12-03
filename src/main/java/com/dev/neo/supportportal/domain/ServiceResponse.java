package com.dev.neo.supportportal.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ServiceResponse
{
	private int httpStatusCode; // 200,400, 500
	private HttpStatus httpStatus;
	private String reason;
	private String message;

	public ServiceResponse()
	{
	}

	public ServiceResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message)
	{
		super();
		this.httpStatusCode = httpStatusCode;
		this.httpStatus = httpStatus;
		this.reason = reason;
		this.message = message;
	}

	public int getHttpStatusCode()
	{
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode)
	{
		this.httpStatusCode = httpStatusCode;
	}

	public HttpStatus getHttpStatus()
	{
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus)
	{
		this.httpStatus = httpStatus;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}

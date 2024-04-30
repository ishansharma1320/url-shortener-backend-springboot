package com.ishans.dev.URLShortenerApplication.ResponseModel;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class URLShortenerErrorResponse {
	private HttpStatus status;
	private String message;
	private String stackTrace;
}

package com.ishans.dev.URLShortenerApplication.ResponseModel;

import org.springframework.http.HttpStatus;

import com.ishans.dev.URLShortenerApplication.collection.URLObject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class URLCreatedSuccessfulResponse {
	private HttpStatus status;
	private URLObject message;
	
}

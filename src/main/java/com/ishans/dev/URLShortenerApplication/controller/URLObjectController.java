package com.ishans.dev.URLShortenerApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ishans.dev.URLShortenerApplication.ResourceModel.URLObjectCreateResource;
import com.ishans.dev.URLShortenerApplication.ResponseModel.URLCreatedSuccessfulResponse;
import com.ishans.dev.URLShortenerApplication.ResponseModel.URLShortenerErrorResponse;
import com.ishans.dev.URLShortenerApplication.collection.URLObject;
import com.ishans.dev.URLShortenerApplication.service.URLObjectService;
import com.ishans.dev.URLShortenerApplication.util.ExceptionUtil;

@RestController
@RequestMapping("/url")
public class URLObjectController {
	
	@Autowired
	URLObjectService urlObjectService;

	@SuppressWarnings("rawtypes")
	@PostMapping
	public ResponseEntity<URLCreatedSuccessfulResponse> createShortURL(@RequestBody URLObjectCreateResource urlObjectCreateResource){
		
		ResponseEntity response;
		try {
			if(urlObjectCreateResource.getLongURL().isBlank()) {
				URLShortenerErrorResponse urlShortenerErrorResponse = URLShortenerErrorResponse.builder().status(HttpStatus.BAD_REQUEST).message("BAD_REQUEST").build();
				response = ResponseEntity.badRequest().body(urlShortenerErrorResponse);
			} else {
				URLObject urlObject = URLObject.builder().longURL(urlObjectCreateResource.getLongURL()).build();
				URLCreatedSuccessfulResponse urlCreatedSuccessfulResponse = URLCreatedSuccessfulResponse.builder().status(HttpStatus.OK).message(urlObject).build();
				System.out.println(urlObject);
				System.out.println(urlObjectService.save(urlObject));
				response = ResponseEntity.ok(urlCreatedSuccessfulResponse);
				
			}
		} catch(Exception e) {
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			// return a 500 response
			URLShortenerErrorResponse urlShortenerErrorResponse = URLShortenerErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR)
					.message("INTERNAL_SERVER_ERROR").stackTrace(ExceptionUtil.getStackTraceAsString(e)).build();
			
			response = ResponseEntity.internalServerError().body(urlShortenerErrorResponse);
		}
		
		return response;
	}
}

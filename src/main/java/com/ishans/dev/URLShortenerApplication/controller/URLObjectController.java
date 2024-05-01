package com.ishans.dev.URLShortenerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ishans.dev.URLShortenerApplication.ResourceModel.URLObjectCreateResource;
import com.ishans.dev.URLShortenerApplication.ResponseModel.URLCreatedSuccessfulResponse;
import com.ishans.dev.URLShortenerApplication.ResponseModel.URLShortenerErrorResponse;
import com.ishans.dev.URLShortenerApplication.collection.URLHistory;
import com.ishans.dev.URLShortenerApplication.collection.URLObject;
import com.ishans.dev.URLShortenerApplication.service.IPInfoService;
import com.ishans.dev.URLShortenerApplication.service.URLObjectService;
import com.ishans.dev.URLShortenerApplication.util.ExceptionUtil;

import io.ipinfo.api.model.IPResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/url")
public class URLObjectController {
	
	 @Autowired
	 private HttpServletRequest httpServletRequest;
	
	 @Autowired
	 private URLObjectService urlObjectService;
	 
	 @Autowired
	 private IPInfoService ipInfoService;

	@SuppressWarnings("rawtypes")
	@PostMapping
	public ResponseEntity<?> createShortURL(@RequestBody URLObjectCreateResource urlObjectCreateResource){
		
		ResponseEntity<?> response;
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
	
	
	@GetMapping("/{id}")
	public Object redirectURL(@PathVariable String id) {
		Object response;
		
		// increment click by 1
		// get ip
		// get region by calling API
		// create a date string for access date
		try {
			if(id.isBlank()) {
				URLShortenerErrorResponse urlShortenerErrorResponse = URLShortenerErrorResponse.builder().status(HttpStatus.BAD_REQUEST).message("BAD_REQUEST").build();
				response = ResponseEntity.badRequest().body(urlShortenerErrorResponse);
			} else {
				URLObject urlObject = urlObjectService.getURLObjectById(id);
				
				if(urlObject == null) {
					URLShortenerErrorResponse urlShortenerErrorResponse = URLShortenerErrorResponse.builder().status(HttpStatus.NOT_FOUND).message("NOT_FOUND").build();
					response = ((BodyBuilder) ResponseEntity.notFound()).body(urlShortenerErrorResponse);
				} else {
					int clicks = urlObject.getClicks() + 1;
					urlObject.setClicks(clicks);
					String ipAddress = getUserIp();
					URLHistory newHistoryObject = null;
					String longURL = urlObject.getLongURL();
					if(!ipAddress.isBlank()) {
						IPResponse IPResponse = ipInfoService.getIPDetails(ipAddress);
						System.out.println(IPResponse.toString());
						newHistoryObject = URLHistory.builder().ipAddress(ipAddress)
								.city(IPResponse.getCity()).country(IPResponse.getCountryName())
								.region(IPResponse.getRegion()).timezone(IPResponse.getTimezone()).build();
					}
					
					List<URLHistory> urlObjectHistory = urlObject.getHistory();
					if(newHistoryObject != null) {
						urlObjectHistory.add(newHistoryObject);
						urlObject.setHistory(urlObjectHistory);
					}
					
					urlObjectService.save(urlObject);
					if(longURL != null && !longURL.isBlank()) {
						response = new RedirectView(longURL);
					} else {
						throw new Exception("longURL cannot be null or empty");
					}
					
				}
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
	
	private String getUserIp() {
		String ipAddress = httpServletRequest.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        
        return ipAddress == null ? "" : ipAddress;
	}
}

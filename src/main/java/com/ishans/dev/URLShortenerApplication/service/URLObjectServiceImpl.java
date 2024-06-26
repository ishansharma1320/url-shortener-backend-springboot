package com.ishans.dev.URLShortenerApplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishans.dev.URLShortenerApplication.collection.URLObject;
import com.ishans.dev.URLShortenerApplication.repository.URLObjectRepository;

@Service
public class URLObjectServiceImpl implements URLObjectService {
	
	@Autowired
	URLObjectRepository urlObjectRepository;
	
	@Override
	public String save(URLObject urlObject) {
		return urlObjectRepository.save(urlObject).getUniqueId();
	}
	
	@Override
	public URLObject getURLObjectById(String id) {
		return urlObjectRepository.findById(id).orElse(null);
	}
	
}

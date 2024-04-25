package com.ishans.dev.URLShortenerApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ishans.dev.URLShortenerApplication.collection.URLObject;

@Repository
public interface URLObjectRepository extends MongoRepository<URLObject, String>{

}

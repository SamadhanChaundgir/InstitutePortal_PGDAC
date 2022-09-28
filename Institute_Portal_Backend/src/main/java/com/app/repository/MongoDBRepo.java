package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.pojos.Studymaterial;

@Repository
public interface MongoDBRepo extends MongoRepository<Studymaterial, String> {

}

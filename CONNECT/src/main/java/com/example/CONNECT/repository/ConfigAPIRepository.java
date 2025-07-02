package com.example.CONNECT.repository;

import com.example.CONNECT.entry.ConfigApiEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConfigAPIRepository extends MongoRepository<ConfigApiEntry, ObjectId> {

}

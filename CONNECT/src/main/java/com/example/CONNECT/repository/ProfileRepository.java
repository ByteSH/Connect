package com.example.CONNECT.repository;

import com.example.CONNECT.entry.ProfileEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProfileRepository extends MongoRepository<ProfileEntry, ObjectId> {
}

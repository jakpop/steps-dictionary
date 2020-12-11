package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User getByUsername(String username);
}

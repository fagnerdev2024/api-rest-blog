package com.fagnerdev.api_rest_blog.repository;

import com.fagnerdev.api_rest_blog.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}

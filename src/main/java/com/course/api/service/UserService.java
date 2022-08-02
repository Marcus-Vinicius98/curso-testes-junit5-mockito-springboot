package com.course.api.service;

import com.course.api.domain.User;

import java.util.List;

public interface UserService {

    User findById(long id);
     List<User> findAll();
}

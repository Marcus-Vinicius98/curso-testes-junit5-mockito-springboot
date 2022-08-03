package com.course.api.service;

import com.course.api.domain.User;
import com.course.api.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(long id);
     List<User> findAll();
     User create (UserDTO obj);
     User update(UserDTO obj);
     void delete(Long id);
}

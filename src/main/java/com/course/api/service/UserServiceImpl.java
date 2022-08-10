package com.course.api.service;

import com.course.api.domain.User;
import com.course.api.domain.dto.UserDTO;
import com.course.api.repository.UserRepository;
import com.course.api.service.exceptions.DataIntegratyViolationException;
import com.course.api.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public User findById(long id) {
        Optional<User> obj =  userRepository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(mapper.map(obj,User.class));
    }

    @Override

    public User update(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(mapper.map(obj,User.class));
        }

    @Override
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }


    public void findByEmail(UserDTO obj){
      Optional<User> user = userRepository.findByEmail(obj.getEmail());
      if (user.isPresent() && !user.get().getId().equals(obj.getId())){
          throw new DataIntegratyViolationException("Email já cadastrado no sistema");
      }
    }


}
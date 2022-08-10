package com.course.api.resources;


import com.course.api.domain.dto.UserDTO;
import com.course.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
       return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
    }
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
      return ResponseEntity.ok().body(userService.findAll().stream()
              .map(x-> mapper.map(x, UserDTO.class)).collect(Collectors.toList()));

    }
    @PostMapping
    public ResponseEntity<UserDTO> create (@RequestBody UserDTO obj){
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
              .buildAndExpand(userService.create(obj).getId()).toUri();
      return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable long id,@RequestBody UserDTO obj){
        obj.setId(id);

        return ResponseEntity.ok().body(mapper.map(userService.update(obj), UserDTO.class));

    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();


    }
}

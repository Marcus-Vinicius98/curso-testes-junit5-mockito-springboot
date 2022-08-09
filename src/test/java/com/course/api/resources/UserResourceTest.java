package com.course.api.resources;

import com.course.api.domain.User;
import com.course.api.domain.dto.UserDTO;
import com.course.api.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserResourceTest {

    public static final long ID = 1L;
    public static final String Name = "Marcus";
    public static final String EMAIL = "marcus@gmail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;

    @InjectMocks
    private UserResource resource;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;


    private User user;

    private UserDTO userDTO;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void findById() {
        when(service.findById(anyLong())).thenReturn(user);
        when(mapper.map(any(),Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID,response.getBody().getId());
        assertEquals(Name,response.getBody().getName());
        assertEquals(EMAIL,response.getBody().getEmail());
        assertEquals(PASSWORD,response.getBody().getPassword());
    }

    @Test
    void findAll() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(Mockito.any(),Mockito.any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(ArrayList.class,response.getBody().getClass());
        assertEquals(UserDTO.class,response.getBody().get(0).getClass());

        assertEquals(ID,response.getBody().get(INDEX).getId());
        assertEquals(Name,response.getBody().get(INDEX).getName());
        assertEquals(EMAIL,response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD,response.getBody().get(INDEX).getPassword());

    }

    @Test
    void create() {
        when(service.create(Mockito.any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertNotNull(response.getHeaders().get("location"));
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    private void startUser(){
        user = new User(ID, Name, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, Name, EMAIL, PASSWORD);

    }
}
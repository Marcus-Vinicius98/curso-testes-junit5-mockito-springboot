package com.course.api.service;

import com.course.api.domain.User;
import com.course.api.domain.dto.UserDTO;
import com.course.api.repository.UserRepository;

import com.course.api.service.exceptions.DataIntegratyViolationException;
import com.course.api.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {


    public static final long ID = 1L;
    public static final String Name = "Marcus";
    public static final String EMAIL = "marcus@gmail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;

    private UserDTO userDTO;

    private Optional<User> optionalUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }


    @Test
    void findById() {
    when(repository.findById(anyLong())).thenReturn(optionalUser);
     User response = service.findById(ID);

        assertNotNull(response);

        assertEquals(User.class,response.getClass());
        assertEquals(ID,response.getId());
        assertEquals(Name, response.getName());
        assertEquals(EMAIL, response.getEmail());

    }
    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
         when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

         try {
             service.findById(ID);
         }catch (Exception ex){
             assertEquals(ObjectNotFoundException.class,ex.getClass());
             assertEquals("Objeto não encontrado",ex.getMessage());
         }
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();
        assertNotNull(response);
        assertEquals(1,response.size());
        assertEquals(User.class,response.get(INDEX).getClass());

        assertEquals(ID,response.get(INDEX).getId());
        assertEquals(Name,response.get(INDEX).getName());
        assertEquals(EMAIL,response.get(INDEX).getEmail());
        assertEquals(PASSWORD,response.get(INDEX).getPassword());
    }

    @Test
    void create() {
     when(repository.save(any())).thenReturn(user);
     User response = service.create(userDTO);
     assertNotNull(response);

        assertEquals(ID,response.getId());
        assertEquals(Name,response.getName());
        assertEquals(EMAIL,response.getEmail());
        assertEquals(PASSWORD,response.getPassword());

    }
    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException(){

        when(repository.findByEmail(anyString())).thenReturn(optionalUser);
        optionalUser.get().setId(2L);

        try {
            service.create(userDTO);
        }catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals("Email já cadastrado no sistema",ex.getMessage());
        }
    }

    @Test
    void update() {
        when(repository.save(any())).thenReturn(user);
        User response = service.update(userDTO);
        assertNotNull(response);

        assertEquals(ID,response.getId());
        assertEquals(Name,response.getName());
        assertEquals(EMAIL,response.getEmail());
        assertEquals(PASSWORD,response.getPassword());
    }

    @Test
    void delete() {
    }

    @Test
    void findByEmail() {
    }

    private void startUser(){
         user = new User(ID, Name, EMAIL, PASSWORD);
         userDTO = new UserDTO(ID, Name, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, Name, EMAIL, PASSWORD));
    }
}
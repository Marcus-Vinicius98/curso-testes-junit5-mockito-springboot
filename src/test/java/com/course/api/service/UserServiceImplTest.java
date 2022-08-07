package com.course.api.service;

import com.course.api.domain.User;
import com.course.api.domain.dto.UserDTO;
import com.course.api.repository.UserRepository;

import com.course.api.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {


    public static final long ID = 1L;
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
        assertEquals("Marcus", response.getName());
        assertEquals("marcus@gmail.com", response.getEmail());

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
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByEmail() {
    }

    private void startUser(){
         user = new User(ID,"Marcus","marcus@gmail.com","123");
         userDTO = new UserDTO(ID, "Marcus", "marcus@gmail.com", "123");
        optionalUser = Optional.of(new User(ID,"Marcus","marcus@gmail.com","123"));
    }
}
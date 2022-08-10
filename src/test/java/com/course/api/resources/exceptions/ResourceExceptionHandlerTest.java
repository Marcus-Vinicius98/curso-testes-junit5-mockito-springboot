package com.course.api.resources.exceptions;

import com.course.api.service.exceptions.DataIntegratyViolationException;
import com.course.api.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExThenReturnAResponsityEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.objNotFound(
                new ObjectNotFoundException("Objeto não encontrado"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(StandardError.class,response.getBody().getClass());
        assertEquals("Objeto não encontrado",response.getBody().getError());
        assertEquals(404,response.getBody().getStatus());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler.objNotFound(
                new DataIntegrityViolationException("Email já cadastrado"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(StandardError.class,response.getBody().getClass());
        assertEquals("Email já cadastrado",response.getBody().getError());
        assertEquals(400,response.getBody().getStatus());
    }
}
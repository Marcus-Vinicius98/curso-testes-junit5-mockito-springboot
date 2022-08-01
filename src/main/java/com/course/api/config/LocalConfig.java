package com.course.api.config;

import com.course.api.domain.User;
import com.course.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDb(){

        User u1 = new User(null,"Andreia","andreiamoural@gmail.com","Euamoamim");

        userRepository.saveAll(Arrays.asList(u1));

    }
}

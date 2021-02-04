package com.revature.chronicle.services;

import com.revature.chronicle.daos.UserRepo;
import com.revature.chronicle.services.UserService;
import com.revature.chronicle.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Component
public class UserServiceTests {
    @Mock
    private UserRepo repo;

    @InjectMocks
    private UserService service;

    @Test
    public void saveAUser(){
        User newUser = new User();

        service.save(newUser);

        verify(repo).save(newUser);
    }

}

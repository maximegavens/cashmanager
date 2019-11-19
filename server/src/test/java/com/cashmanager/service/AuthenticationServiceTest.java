package com.cashmanager.service;

import com.cashmanager.model.User;
import com.cashmanager.repository.AuthenticationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {

    private MockMvc mock;

    @Mock
    private AuthenticationRepository repository;

    @InjectMocks
    private AuthenticationService service;

    @Before
    public void setUp() throws Exception {
        mock = MockMvcBuilders.standaloneSetup(service)
                .build();
    }

    /*@Test
    public void signInTest() throws Exception {
        User us = new User();
        us.setId_user(4);
        us.setFull_name("JUnit");
        us.setEmail("junit@test.fr");
        us.setPassword("JUnit");
        Mockito.when(service.SignIn(us)).thenReturn(us);
    }*/

    @Test
    public void createUserTest() throws Exception {
        User us = new User();
        us.setId_user(4);
        us.setFull_name("JUnit");
        us.setEmail("junffffit@test.fr");
        us.setPassword("JUnit");
        Mockito.when(service.createUser(us)).thenReturn(us);
    }
}

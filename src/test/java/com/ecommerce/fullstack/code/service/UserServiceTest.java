package com.ecommerce.fullstack.code.service;

import com.ecommerce.fullstack.code.dao.UserDao;
import com.ecommerce.fullstack.code.entity.Role;
import com.ecommerce.fullstack.code.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // initializes the mock objects
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Mock
    private UserDao userDao;
    @BeforeEach
    void setUp() {
        userService = new UserService(userDao);
    }

    @Test
    public void getUserByIdTest()
    {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER_ROLE","default description"));
        when(userDao.findById("Mosala7")).
                thenReturn(Optional.of(new User("Mosala7",
                        "Mohamed", "Salah", "123456", roles)));
        User user = userService.getUser("Mosala7");
        assertEquals("Mohamed", user.getFirstName());
        assertEquals("Salah", user.getLastName());
    }

    @Test
    public void createUserTest()
    {
        User user = new User("Mosala7",
                "Mohamed", "Salah", "123456", new HashSet<>());
        userService.createUser(user);
        verify(userDao).save(user);
    }

    @Test
    public void getAllUsersTest() {
        // when
        userService.getAllUsers();
        // then
        verify(userDao).findAll();
    }
}

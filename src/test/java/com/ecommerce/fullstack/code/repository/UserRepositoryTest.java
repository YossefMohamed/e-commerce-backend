package com.ecommerce.fullstack.code.repository;

import com.ecommerce.fullstack.code.dao.UserDao;
import com.ecommerce.fullstack.code.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserDao userDao;
    @Test
    void it_should_findUserByUserName() {
        // given
        Role role = new Role("USER_ROLE", "DEFALUT DESCRIPTION");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user1 = new User("mosala7", "Mohamed", "Salah", "123456", roles);
        userDao.save(user1);
        // when
        User testFname = userDao.findUserByUserName("mosala7");
        // then
        assertThat(testFname).isEqualTo(user1);
    }
}
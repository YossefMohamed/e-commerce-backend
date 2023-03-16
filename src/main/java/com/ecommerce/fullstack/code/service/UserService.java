package com.ecommerce.fullstack.code.service;


import com.ecommerce.fullstack.code.dao.RoleDao;
import com.ecommerce.fullstack.code.dao.UserDao;
import com.ecommerce.fullstack.code.entity.Role;
import com.ecommerce.fullstack.code.entity.User;
import com.ecommerce.fullstack.code.event.EmailEvent;
import com.ecommerce.fullstack.code.event.EmailPublisher;
import com.ecommerce.fullstack.code.exception.CustomApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    EmailPublisher emailPublisher;

    public User createUser(User user) {

        emailPublisher
                .publishEmailEvent
                        (new EmailEvent(user));

        Optional<Role> roleOptional = roleDao.findById("USER_ROLE");
        if (!roleOptional.isPresent())
            throw new CustomApiException("USER_ROLE NOT FOUND");
        Set<Role> roles = new HashSet<>();
        roles.add(roleOptional.get());
        user.setRoles(roles);
        user.setPassword(getEncodedPassword(user.getPassword()));
        return userDao.save(user);
    }

    public String getEncodedPassword(String pass) {
        return passwordEncoder.encode(pass);
    }

    public User getUser(String id) {
        Optional<User> userOptional = userDao.findById(id);
        if (!userOptional.isPresent()) {
            Object params[] = {id};
            String message = messageSource.getMessage("message.user.with.id.not.found",
                    params, LocaleContextHolder.getLocale());
            throw new CustomApiException(message);
        }
        return userOptional.get();
    }

    // only for test purpose
    public void getAllUsers() {
        userDao.findAll();
    }

}

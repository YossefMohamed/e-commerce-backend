package com.ecommerce.fullstack.code.controller;


import com.ecommerce.fullstack.code.entity.User;
import com.ecommerce.fullstack.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/admin-dash")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public String adminDashboard() {
        return "Admin Dashboard";
    }

    @GetMapping("/user-dash")
    @PreAuthorize("hasRole('USER_ROLE')")
    public String userDashboard() {
        return "User Dashboard";
    }

}

package com.ecommerce.fullstack.code.dao;

import com.ecommerce.fullstack.code.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
    User findUserByUserName(@Param("userName") String username);
}

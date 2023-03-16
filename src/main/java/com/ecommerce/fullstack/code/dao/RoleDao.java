package com.ecommerce.fullstack.code.dao;

import com.ecommerce.fullstack.code.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
}

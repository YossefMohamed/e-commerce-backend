package com.ecommerce.fullstack.code.entity;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class JwtRequest {
    private String user_name;
    private String user_password;
}

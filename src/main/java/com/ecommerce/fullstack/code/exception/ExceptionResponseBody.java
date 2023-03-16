package com.ecommerce.fullstack.code.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionResponseBody {
    private final String message;
    private final HttpStatus error;
    private final int status;
    private Date timestamp;
}

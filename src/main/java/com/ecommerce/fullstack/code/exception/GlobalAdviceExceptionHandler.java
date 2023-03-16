package com.ecommerce.fullstack.code.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalAdviceExceptionHandler extends ResponseEntityExceptionHandler {

    // error - 500
    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ExceptionResponseBody>
    handleCustomServerErrorException(CustomApiException e) {
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        return CustomResponseBody(e, serverError);
    }

    // return response
    private ResponseEntity<ExceptionResponseBody> CustomResponseBody(Exception e, HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new ExceptionResponseBody(
                        e.getMessage(),
                        httpStatus,
                        httpStatus.value(),
                        new Date()),
                httpStatus);
    }


}

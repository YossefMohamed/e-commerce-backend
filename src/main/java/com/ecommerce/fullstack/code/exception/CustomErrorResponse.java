package com.ecommerce.fullstack.code.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import java.util.Map;
@Component
public class CustomErrorResponse extends DefaultErrorAttributes {

    @Autowired
    private MessageSource messageSource;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        // call getErrorAttributes and modify it
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        // get status code
        Object status = errorAttributes.get("status");
        // check
        if (status.equals(404)){
            errorAttributes.put("message", getMessageSource("message.not.found"));
        } else if (status.equals(401)) {
            errorAttributes.put("message", getMessageSource("message.unauthorized"));
        } else if (status.equals(403)) {
            errorAttributes.put("message", getMessageSource("message.forbidden"));
        }else {
            errorAttributes.put("message", errorAttributes.get("message"));
        }
        // need only these attributes
        errorAttributes.put("error", errorAttributes.get("error"));
        errorAttributes.put("status",status );
        errorAttributes.put("local", LocaleContextHolder.getLocale());
        errorAttributes.put("timestamp", new Date());

        // no need for these attributes
        errorAttributes.remove("trace");
        errorAttributes.remove("path");

        return errorAttributes;
    }

    private String getMessageSource(String msg){
        return messageSource.getMessage(msg,
                null, LocaleContextHolder.getLocale());
    }

}

package com.ecommerce.fullstack.code.event;

import com.ecommerce.fullstack.code.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailEvent extends ApplicationEvent {

    User user;
    public EmailEvent(User user) {
        super(user);
        this.user = user;
    }
}

package com.ecommerce.fullstack.code.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EmailPublisher {
    @Autowired
    private final ApplicationEventPublisher eventPublisher;

    EmailPublisher(ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }

    public void publishEmailEvent(EmailEvent event) {
        eventPublisher.publishEvent(event);
    }

}

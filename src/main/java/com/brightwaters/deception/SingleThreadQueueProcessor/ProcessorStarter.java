package com.brightwaters.deception.SingleThreadQueueProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProcessorStarter {
    @Autowired 
    QueueProcessor processor;

    @EventListener(ApplicationReadyEvent.class)
    public void startProcessor() {
        processor.run();
    }
}

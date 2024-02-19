package com.anthonyquere.companionapi.events;

import com.anthonyquere.companionapi.crud.companions.Companion;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class NewAIMessageEvent extends ApplicationEvent {
    private final String message;
    private final Companion companion;

    public NewAIMessageEvent(Object source, String message, Companion companion) {
        super(source);
        this.message = message;
        this.companion = companion;
    }

}

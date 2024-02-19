package com.anthonyquere.companionapi.crud.companions;

import com.anthonyquere.companionapi.crud.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "messages", types = Companion.class)
public interface CompanionMessageProjection {
    @Value("#{target}")
    Companion getCompanion();

    @Value("#{@messageRepository.getMessagesByCompanion_IdOrderByCreatedAtDesc(target.id)}")
    List<Message> getMessages();


}

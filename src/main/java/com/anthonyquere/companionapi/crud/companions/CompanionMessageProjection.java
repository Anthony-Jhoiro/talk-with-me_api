package com.anthonyquere.companionapi.crud.companions;

import com.anthonyquere.companionapi.crud.message.Message;
import dev.langchain4j.service.V;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;
import java.util.Optional;

@Projection(name = "messages", types = Companion.class)
public interface CompanionMessageProjection {


    @Value("#{target}")
    Companion getCompanion();

    @Value("#{@messageRepository.getLatestMassages(target.id)}")
    List<Message> getMessages();

    @Value("#{@messageRepository.getLatestSummary(target.id)}")
    Optional<String> getLatestSummary();
}

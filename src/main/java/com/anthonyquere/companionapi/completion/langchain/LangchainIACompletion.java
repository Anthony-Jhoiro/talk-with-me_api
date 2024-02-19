package com.anthonyquere.companionapi.completion.langchain;

import com.anthonyquere.companionapi.completion.Completion;
import com.anthonyquere.companionapi.completion.CompletionOutput;
import com.anthonyquere.companionapi.completion.langchain.services.Summary;
import com.anthonyquere.companionapi.completion.langchain.services.TalkWithCompanion;
import com.anthonyquere.companionapi.crud.companions.Companion;
import com.anthonyquere.companionapi.crud.message.Message;
import com.anthonyquere.companionapi.crud.message.MessageRepository;
import com.anthonyquere.companionapi.events.NewAIMessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("Langchain")
@RequiredArgsConstructor
public class LangchainIACompletion implements Completion {

    private final TalkWithCompanion talkWithCompanion;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public CompletionOutput answerMessage(Companion companion, String question) throws Exception {

        var response = talkWithCompanion.chat(companion, companion.getBackground(), question, "Jhoiro");

        // Publish event to build summary
        applicationEventPublisher.publishEvent(new NewAIMessageEvent(this, response, companion));

        return new CompletionOutput(response);
    }
}

package com.anthonyquere.companionapi.completion.langchain;

import com.anthonyquere.companionapi.completion.langchain.services.Summary;
import com.anthonyquere.companionapi.completion.langchain.services.TalkWithCompanion;
import com.anthonyquere.companionapi.crud.companions.Companion;
import com.anthonyquere.companionapi.crud.message.MessageRepository;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanionAiService {

    @Bean
    public TalkWithCompanion buildAiCompanionService(
            ChatLanguageModel model,
            MessageRepository messageRepository

    ) {
        return AiServices.builder(TalkWithCompanion.class)
                .chatLanguageModel(model)
                .chatMemoryProvider(companion -> new CompanionChatMemory((Companion) companion, messageRepository))
                .build();
    }

    @Bean
    public Summary buildAiSummaryService(
            ChatLanguageModel model
    ) {
        return AiServices.builder(Summary.class)
                .chatLanguageModel(model)
                .build();
    }
}

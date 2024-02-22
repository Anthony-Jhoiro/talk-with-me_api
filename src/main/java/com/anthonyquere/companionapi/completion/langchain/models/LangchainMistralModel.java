package com.anthonyquere.companionapi.completion.langchain.models;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangchainMistralModel {

    @Value("${mistral-ia.api-key}")
    private String mistralApiKey;

    @Bean
    public ChatLanguageModel buildMistralModel() {
        return MistralAiChatModel.builder()
                .apiKey(mistralApiKey)
                .maxTokens(100)
                .modelName("mistral-tiny")
                .temperature(1.0d)
                .logRequests(true)
                .logResponses(true)
                .maxRetries(1)
                .build();
    }
}

package com.anthonyquere.companionapi.completion.mistralia;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class MistralIAClient {

    @Value("${mistral-ia.api-endpoint:https://api.mistral.ai}")
    private String mistralApiEndpoint;

    @Value("${mistral-ia.api-key}")
    private String mistralApiKey;


    public record MistralRequestPayloadMessage(String role, String content) {}

    public record MistralRequestPayload(String model, List<MistralRequestPayloadMessage> messages) {}

    public record MistralResponsePayloadChoiceMessage(String content, String role) {}
    public record MistralResponsePayloadChoice(MistralResponsePayloadChoiceMessage message) {}
    public record MistralResponsePayload(List<MistralResponsePayloadChoice> choices) {}

    private  <Payload, Output> Output postMistral(String path, @Nullable Payload payload, Class<Output> outputClass) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(mistralApiKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        if (payload == null) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        var req = new HttpEntity<>(payload, headers);

        try {
            return restTemplate.postForObject(
                    mistralApiEndpoint + path,
                    req,
                    outputClass
            );
        } catch (Exception e) {
            log.error("Call to mistral API failed, reason: {}", e.getMessage());
            throw e;
        }

    }

    public MistralResponsePayload complete(MistralRequestPayload payload) throws Exception {
        return postMistral("/v1/chat/completions", payload, MistralResponsePayload.class);
    }
}

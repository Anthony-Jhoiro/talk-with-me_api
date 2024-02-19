package com.anthonyquere.companionapi.completion.mistralia;

import com.anthonyquere.companionapi.completion.Completion;
import com.anthonyquere.companionapi.completion.CompletionOutput;
import com.anthonyquere.companionapi.crud.companions.Companion;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MistralIA")
@RequiredArgsConstructor
public class MistralIACompletion implements Completion {

    @Value("${mistral-ia.model:mistral-tiny}")
    private String mistralModel;

    private final MistralIAClient mistralIAClient;


    public CompletionOutput answerMessage(Companion companion, String question) throws Exception {
        var apiAnswer = mistralIAClient.complete(
                new MistralIAClient.MistralRequestPayload(
                        mistralModel,
                        List.of(
                                new MistralIAClient.MistralRequestPayloadMessage("system", companion.getBackground()),
                                new MistralIAClient.MistralRequestPayloadMessage("user", question)
                        )
                )
        );

        var responseMessage = apiAnswer.choices().stream().findFirst().map(choice -> choice.message().content()).orElse("No Answer...");

        return new CompletionOutput(responseMessage);
    }
}

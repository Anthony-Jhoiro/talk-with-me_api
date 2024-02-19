package com.anthonyquere.companionapi.completion;

import com.anthonyquere.companionapi.crud.companions.CompanionRepository;
import com.anthonyquere.companionapi.crud.message.Message;
import com.anthonyquere.companionapi.crud.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    @Qualifier("Langchain")
    private final Completion completion;
    private final CompanionRepository companionRepository;
    private final MessageRepository messageRepository;

    public CompletionService(@Qualifier("Langchain") Completion completion, CompanionRepository companionRepository, MessageRepository messageRepository) {
        this.completion = completion;
        this.companionRepository = companionRepository;
        this.messageRepository = messageRepository;
    }

    public Message complete(String companionId, String question) throws Exception {
        var companion = companionRepository.findById(companionId).orElseThrow();

        var message = Message
                .builder()
                .message(question)
                .companion(companion)
                .build();
//
//        message = messageRepository.save(message);

        var completionOutput = completion.answerMessage(companion, message.getMessage());

        var response = Message
                .builder()
                .message(completionOutput.message())
                .companion(companion)
                .build();

//        response = messageRepository.save(response);
        return response;
    }
}

package com.anthonyquere.companionapi.completion.langchain;

import com.anthonyquere.companionapi.completion.langchain.services.Summary;
import com.anthonyquere.companionapi.crud.companions.Companion;
import com.anthonyquere.companionapi.crud.message.Message;
import com.anthonyquere.companionapi.crud.message.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringMapMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryService {

    private final MessageRepository messageRepository;
    private final Summary summary;

    @Value("${hardRetentionSummary:5}")
    private Integer hardRetentionSummary = 5;

    @Transactional
    public void summarizeChat(Companion companion) {
        var messages = messageRepository.getMessagesByCompanion_IdOrderByCreatedAtDesc(companion.getId(), Pageable.ofSize(20));

        var notArchivedMessages = messages.stream()
                .filter(m-> m.getStatus() == null || m.getStatus().equals(Message.Status.NOT_ARCHIVED))
                .toList();

        if (notArchivedMessages.size() < hardRetentionSummary) {
            return;
        }

        // do not summarize  n last messages
        var messagesToArchive = notArchivedMessages.subList(hardRetentionSummary, notArchivedMessages.size());

        if (messagesToArchive.isEmpty()) {
            log.debug("Nothing to summarize");
            return;
        }

        log.info(new StringMapMessage()
                .with("message", "Generating summary")
                .with("messageCount", messagesToArchive)
                .with("companion", companion)
                .toString()
        );

        String sum;

        try {
            sum = summary.summarize("Summarize the following messages as best you can as if you were explaining it to the AI.\n" + String.join("\n", messagesToArchive.stream().map(Message::toString).toList()));
        } catch (Exception e) {
            log.error("Fail to generate summary: {}", e.getMessage());
            return;
        }

        var summaryMessage = Message.builder()
                .message(sum)
                .type("SUMMARY")
                .companion(companion)
                .createdAt(LocalDateTime.now())
                .build();


        // Mark previous messages as archived
        messagesToArchive.forEach(m -> m.setStatus(Message.Status.ARCHIVED));
        messageRepository.saveAll(messagesToArchive);
        // Save summary
        messageRepository.save(summaryMessage);

    }
}

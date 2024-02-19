package com.anthonyquere.companionapi.crud.message;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message, UUID> {
    List<Message> getMessagesByCompanion_IdOrderByCreatedAtDesc(String companionId);
}

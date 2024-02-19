package com.anthonyquere.companionapi.crud.message;

import com.anthonyquere.companionapi.crud.companions.Companion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Message {

    public Message() {}

    public enum Status {
        ARCHIVED,
        NOT_ARCHIVED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 512)
    private String message;

    @Column(length = 24)
    private String type;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NOT_ARCHIVED;

    @CreatedDate
    @OrderBy
    private LocalDateTime createdAt;

    @ManyToOne
    private Companion companion;

    @Override
    public String toString() {
        return type + ": " + message;
    }
}

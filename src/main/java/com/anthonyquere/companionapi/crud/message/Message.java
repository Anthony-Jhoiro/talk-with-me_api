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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 512)
    private String message;

    @CreatedDate
    @OrderBy
    private LocalDateTime createdAt;

    @ManyToOne
    private Companion companion;

}

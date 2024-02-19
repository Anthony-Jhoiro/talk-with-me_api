package com.anthonyquere.companionapi.crud.companions;

import com.anthonyquere.companionapi.crud.message.Message;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Entity
@Getter
@Setter
public class Companion {
    @Id
    @GenericGenerator(name = "id", type = CompanionIdGenerator.class)
    @GeneratedValue(generator = "id")
    private String id;

    private String name;

    @Column(length = 500)
    private String background;

    public static class CompanionIdGenerator implements IdentifierGenerator {

        @Override
        public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
            var c = (Companion) o;
            return c.getName().toLowerCase().replaceAll("[^a-zA-Z]", "_");
        }
    }
}

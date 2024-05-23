package com.bitesped.entity;

import com.bitesped.dto.LinkPrecedence;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BiteSpeedTask")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "MailId is empty")
    private String mailId;
    @NotEmpty(message = "Phone number is empty")
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private int linkedId;
    private LinkPrecedence linkPrecedence;
}

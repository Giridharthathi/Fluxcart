package com.bitesped.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseContactClass {
    private int primaryContactId;
    private List<String> mailId;
    private List<String> phoneNumber;
    private List<Integer> secondaryContactId;
}

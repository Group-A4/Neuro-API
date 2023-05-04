package com.example.Neurosurgical.App.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDetails {

    private String fromEmail;
    private String toEmail;
    private String subject;
    private String body;
}


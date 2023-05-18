package com.example.Neurosurgical.App.recovery;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryBody {
    public String email;
    public String secretCode;
    public String newPassword;
}

package com.example.Neurosurgical.App.recovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recovery")
public class RecoveryController {

    private final RecoveryService recoveryService;

    @Autowired
    public RecoveryController(RecoveryService recoveryService){
        this.recoveryService = recoveryService;
    }

    @PostMapping("/credentials")
    public ResetCode sendResetCode(@RequestBody String email){
        return recoveryService.sendResetCode(email);
        
    }
}

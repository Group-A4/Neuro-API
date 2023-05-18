package com.example.Neurosurgical.App.recovery;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recovery")
public class RecoveryController {

    private final RecoveryService recoveryService;

    @Autowired
    public RecoveryController(RecoveryService recoveryService){
        this.recoveryService = recoveryService;
    }

    @PostMapping("/credentials")
    public String sendResetCode(@RequestBody String email){
        return recoveryService.sendResetCode(email);
    }

    @PostMapping("/reset")
    public String resetPassword(@Valid @RequestBody RecoveryBody recovery){
        return recoveryService.resetPassword(recovery);
    }

    @GetMapping("/validate_code/{email}/{code}")
    public String validateCode(@PathVariable String email, @PathVariable String code){
        return recoveryService.validateCode(email, code);
    }

    @GetMapping("/show")
    public List<Recovery> showAll(){
        return recoveryService.showAll();
    }
}

package com.example.Neurosurgical.App.recovery;


import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecoveryService {



    public ResetCode sendResetCode(String email) {
        // generate the reset code
        // send it to the user through an email
        // encrypt it with bcrypt
        // save it in the recoveryRepository + the expiration date

        return null;
    }
}

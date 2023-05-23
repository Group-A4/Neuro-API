package com.example.Neurosurgical.App.recovery;


import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.email.EmailService;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.repositories.RecoveryRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class RecoveryService {

    private final RecoveryRepository recoveryRepository;
    private final UserRepository userRepository;

    private final EmailService emailService;

    @Autowired
    public RecoveryService(RecoveryRepository recoveryRepository, UserRepository userRepository, EmailService emailService)
    {
        this.recoveryRepository=recoveryRepository;
        this.userRepository=userRepository;
        this.emailService=emailService;
    }

    public String sendResetCode(String email) {
        // Check the mail, if exist
        if (userRepository.findByFacMail(email) == null)
            throw new UserNotFoundException();


        // generate the reset code
        String resetCode = generateCode();

        // send it to the user through an email
        var recovery =  Recovery.builder()
                .emailFaculty(email)
                .secretCode(resetCode)
                .timeExpiration(LocalDateTime.now().plusMinutes(5))
                .build();

        var foundRecovery = recoveryRepository.findByFacMail(email);

        if (foundRecovery != null) {
            recoveryRepository.delete(foundRecovery);
        }

        // encrypt it with bcrypt
        recovery.setSecretCode(BCrypt.hashpw(recovery.getSecretCode(), BCrypt.gensalt(10)));

        // save it in the recoveryRepository + the expiration date
        recoveryRepository.save(recovery);

        // Send code to the user
        //emailService.sendResetCode(email, resetCode);

        // generate the reset code
        return resetCode;
    }

    protected String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            code.append(chars.charAt(index));
        }
        String genCode = code.toString();
        return genCode;
    }

    public List<Recovery> showAll() {
        return recoveryRepository.findAll();
    }

    public String resetPassword(RecoveryBody recovery) {
        // Check if the code exist
        Recovery recoveryEntity = recoveryRepository.findByFacMail(recovery.getEmail());

        if (recoveryEntity == null)
            throw new UserNotFoundException();

        // Check if the code is expired
        if (recoveryEntity.getTimeExpiration().isBefore(LocalDateTime.now()))
            throw new UserNotFoundException();

        // Check if the code is correct
        if (!BCrypt.checkpw(recovery.getSecretCode(), recoveryEntity.getSecretCode()))
            return "mars de aici, cod nu bun";

        // Update the password
        UserEntity user = userRepository.findByFacMail(recovery.getEmail());

        user.setPassword(BCrypt.hashpw(recovery.getNewPassword(), BCrypt.gensalt(10)));
        userRepository.save(user);

        return "Password updated successfully";
    }

    public String validateCode(String email, String code) {
        // Check if the code exist
        Recovery recoveryEntity = recoveryRepository.findByFacMail(email);

        if (recoveryEntity == null)
            return "Code does not exist";

        // Check if the code is expired
        if (recoveryEntity.getTimeExpiration().isBefore(LocalDateTime.now()))
            return "Code is expired";

        // Check if the code is correct
        if (!BCrypt.checkpw(code, recoveryEntity.getSecretCode()))
            return "Code is not correct";

        return "Code is correct";
    }
}

package com.jsularz.practice_app.registrationUtill;

import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(final RegistrationCompleteEvent event) {
        confirmRegistration(event);
    }

    private void confirmRegistration(final RegistrationCompleteEvent event){
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        final SimpleMailMessage email = createConfirmationEmail(event, user, token);
        javaMailSender.send(email);
    }

    private SimpleMailMessage createConfirmationEmail(final RegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("solsoftest@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText( "http://localhost:8080" + confirmationUrl);
        return email;
    }
}

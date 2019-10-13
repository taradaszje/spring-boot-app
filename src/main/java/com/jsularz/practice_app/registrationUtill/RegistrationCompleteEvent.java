package com.jsularz.practice_app.registrationUtill;

import com.jsularz.practice_app.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private String appUrl;
    private Locale locale;
    private User user;

    public RegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}

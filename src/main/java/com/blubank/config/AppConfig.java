package com.blubank.config;

import com.blubank.entity.User.UserRole;
import com.blubank.entity.User.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.blubank")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc

public class AppConfig extends GlobalMethodSecurityConfiguration {

    public static final String ADMIN = "admin";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public EmailValidator emailValidator() {
        return EmailValidator.getInstance();
    }

    @Bean
    public CommandLineRunner demo(final UserService userService,
                                  final PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) {
                userService.addUser("Super", "Admin", encoder.encode("admin"), UserRole.ADMIN, "admin", "", "");
                userService.addUser("Giga", "Tester", encoder.encode("test"), UserRole.MODERATOR, "test", "", "");
            }
        };
    }

}

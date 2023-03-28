package com.in28minutes.springboot.springbootwebapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {
    //LDAP or Database
    //In memory

    @Bean
    public InMemoryUserDetailsManager configureUserDetails() {
        UserDetails user1 = buildUser("mark", "pass");
        UserDetails user2 = buildUser("gyuszi", "jelszo");
        return new InMemoryUserDetailsManager(user1, user2);
    }

    private UserDetails buildUser(String username, String password) {
        return User.builder()
                .passwordEncoder(this::encodePassword)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }

    private String encodePassword(String password) {
        return passwordEncoder().encode(password);
    }
}

package com.team_3.travel_forum;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // This generates the complete string, prefix included!
        System.out.println("User Password Hash: " + encoder.encode("Travel123!"));
    }
}
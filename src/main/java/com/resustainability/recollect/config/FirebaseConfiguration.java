package com.resustainability.recollect.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {
    private final Resource credentials;

    @Autowired
    public FirebaseConfiguration(@Value("${app.google.credentials}") Resource credentials) {
        this.credentials = credentials;
    }

    @PostConstruct
    public void init() throws IOException {
        try (final InputStream serviceAccount = credentials.getInputStream()) {
            final FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(serviceAccount)
                    )
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        }
    }
}

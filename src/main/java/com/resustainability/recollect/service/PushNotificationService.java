package com.resustainability.recollect.service;

import com.google.api.core.ApiFuture;
import com.google.common.collect.Lists;
import com.google.firebase.messaging.*;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.PushNotificationRequest;
import com.resustainability.recollect.repository.UserFcmTokenRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Service
public class PushNotificationService {
    public static final int MAX_TOKEN_SIZE = 500;

    private final UserFcmTokenRepository userFcmTokenRepository;
    private final PushTokenService pushTokenService;
    private final Executor pushExecutor;
    private final Logger log;

    @Autowired
    public PushNotificationService(
            UserFcmTokenRepository userFcmTokenRepository,
            PushTokenService pushTokenService,
            @Qualifier(Default.EXECUTOR_PUSH) Executor pushExecutor
    ) {
        this.userFcmTokenRepository = userFcmTokenRepository;
        this.pushTokenService = pushTokenService;
        this.pushExecutor = pushExecutor;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public void sendToCustomer(Long customerId, String title, String body, String imageUrl) {
        final List<String> tokens = userFcmTokenRepository
                .findActiveTokensByCustomerId(customerId);
        send(tokens, title, body, imageUrl);
    }

    public void sendToProvider(Long providerId, String title, String body, String imageUrl) {
        final List<String> tokens = userFcmTokenRepository
                .findActiveTokensByProviderId(providerId);
        send(tokens, title, body, imageUrl);
    }

    public void send(PushNotificationRequest request) {
        ValidationUtils.validateRequestBody(request);
        send(request.token(), request.title(), request.body(), request.image());
    }

    private void send(String token, String title, String body, String imageUrl) {
        if (StringUtils.isBlank(token)) {
            return;
        }
        sendBatch(List.of(token), title, body, imageUrl);
    }

    private void send(List<String> tokens, String title, String body, String imageUrl) {
        if (CollectionUtils.isBlank(tokens)) {
            return;
        }
        Lists.partition(tokens, MAX_TOKEN_SIZE)
                .forEach(batch ->
                        sendBatch(batch, title, body, imageUrl)
                );
    }

    public void sendBatch(
            final List<String> tokens,
            final String title,
            final String body,
            final String imageUrl
    ) {
        if (CollectionUtils.isBlank(tokens)) {
            return;
        }

        final MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .setImage(imageUrl)
                                .build()
                )
                .build();

        final ApiFuture<BatchResponse> future = FirebaseMessaging.getInstance()
                .sendEachForMulticastAsync(message);

        future.addListener(() -> {
            try {
                handleMulticastResult(future.get(), tokens);
            } catch (Exception e) {
                log.error("FCM multicast send failed", e);
            }
        }, pushExecutor);
    }

    private void handleMulticastResult(
            final BatchResponse response,
            final List<String> tokens
    ) {
        final Set<String> invalidTokens = new HashSet<>();
        try {
            for (int i = 0; i < response.getResponses().size(); i++) {
                SendResponse r = response.getResponses().get(i);
                if (!r.isSuccessful() && isInvalidToken(r.getException())) {
                    invalidTokens.add(tokens.get(i));
                }
            }
        } catch (Exception e) {
            log.error("FCM multicast send failed", e);
        }

        if (!invalidTokens.isEmpty()) {
            pushTokenService.deleteTokens(invalidTokens);
        }
    }

    private boolean isInvalidToken(FirebaseMessagingException e) {
        return e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED;
    }
}

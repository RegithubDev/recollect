package com.resustainability.recollect.entity.django;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = DjangoSession.TABLE_NAME)
public class DjangoSession {
    public static final String TABLE_NAME = "django_session";

    @Id
    @Column(name = "session_key", length = 40)
    private String sessionKey;

    @Column(name = "session_data", nullable = false, columnDefinition = "LONGTEXT")
    private String sessionData;

    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;

    public DjangoSession() {
    }

    public DjangoSession(String sessionKey, String sessionData, LocalDateTime expireDate) {
        this.sessionKey = sessionKey;
        this.sessionData = sessionData;
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DjangoSession entity = (DjangoSession) object;
        return Objects.equals(sessionKey, entity.sessionKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionKey);
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionData() {
        return sessionData;
    }

    public void setSessionData(String sessionData) {
        this.sessionData = sessionData;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}

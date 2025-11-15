package com.resustainability.recollect.entity.django;

import com.resustainability.recollect.entity.backend.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = DjangoAdminLog.TABLE_NAME)
public class DjangoAdminLog {
    public static final String TABLE_NAME = "django_admin_log";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "action_time", nullable = false)
    private LocalDateTime actionTime;

    @Column(name = "object_id", columnDefinition = "LONGTEXT")
    private String objectId;

    @Column(name = "object_repr", nullable = false, length = 200)
    private String objectRepr;

    @Column(name = "action_flag", nullable = false)
    private Short actionFlag;

    @Column(name = "change_message", nullable = false, columnDefinition = "LONGTEXT")
    private String changeMessage;

    @ManyToOne
    @JoinColumn(name = "content_type_id")
    private DjangoContentType contentType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Customer user;

    public DjangoAdminLog() {}

    public DjangoAdminLog(Integer id, LocalDateTime actionTime, String objectId, String objectRepr, Short actionFlag, String changeMessage, DjangoContentType contentType, Customer user) {
        this.id = id;
        this.actionTime = actionTime;
        this.objectId = objectId;
        this.objectRepr = objectRepr;
        this.actionFlag = actionFlag;
        this.changeMessage = changeMessage;
        this.contentType = contentType;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectRepr() {
        return objectRepr;
    }

    public void setObjectRepr(String objectRepr) {
        this.objectRepr = objectRepr;
    }

    public Short getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(Short actionFlag) {
        this.actionFlag = actionFlag;
    }

    public String getChangeMessage() {
        return changeMessage;
    }

    public void setChangeMessage(String changeMessage) {
        this.changeMessage = changeMessage;
    }

    public DjangoContentType getContentType() {
        return contentType;
    }

    public void setContentType(DjangoContentType contentType) {
        this.contentType = contentType;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }
}

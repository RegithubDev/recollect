package com.resustainability.aakri.entity.django;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = DjangoCeleryResultsGroupResult.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"group_id"})
        }
)
public class DjangoCeleryResultsGroupResult {
    public static final String TABLE_NAME = "django_celery_results_groupresult";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_id", nullable = false, length = 255)
    private String groupId;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_done", nullable = false)
    private LocalDateTime dateDone;

    @Column(name = "content_type", nullable = false, length = 128)
    private String contentType;

    @Column(name = "content_encoding", nullable = false, length = 64)
    private String contentEncoding;

    @Column(name = "result", columnDefinition = "LONGTEXT")
    private String result;

    public DjangoCeleryResultsGroupResult() {}

    public DjangoCeleryResultsGroupResult(Integer id, String groupId, LocalDateTime dateCreated, LocalDateTime dateDone, String contentType, String contentEncoding, String result) {
        this.id = id;
        this.groupId = groupId;
        this.dateCreated = dateCreated;
        this.dateDone = dateDone;
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateDone() {
        return dateDone;
    }

    public void setDateDone(LocalDateTime dateDone) {
        this.dateDone = dateDone;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

package com.resustainability.recollect.entity.django;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = DjangoCeleryResultsTaskResult.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"task_id"})
        }
)
public class DjangoCeleryResultsTaskResult {
    public static final String TABLE_NAME = "django_celery_results_taskresult";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_id", nullable = false, length = 255)
    private String taskId;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "content_type", nullable = false, length = 128)
    private String contentType;

    @Column(name = "content_encoding", nullable = false, length = 64)
    private String contentEncoding;

    @Column(name = "result", columnDefinition = "LONGTEXT")
    private String result;

    @Column(name = "date_done", nullable = false)
    private LocalDateTime dateDone;

    @Column(name = "traceback", columnDefinition = "LONGTEXT")
    private String traceback;

    @Column(name = "meta", columnDefinition = "LONGTEXT")
    private String meta;

    @Column(name = "task_args", columnDefinition = "LONGTEXT")
    private String taskArgs;

    @Column(name = "task_kwargs", columnDefinition = "LONGTEXT")
    private String taskKwargs;

    @Column(name = "task_name", length = 255)
    private String taskName;

    @Column(name = "worker", length = 100)
    private String worker;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "periodic_task_name", length = 255)
    private String periodicTaskName;

    @Column(name = "date_started")
    private LocalDateTime dateStarted;

    public DjangoCeleryResultsTaskResult() {
    }

    public DjangoCeleryResultsTaskResult(Integer id, String taskId, String status, String contentType, String contentEncoding, String result, LocalDateTime dateDone, String traceback, String meta, String taskArgs, String taskKwargs, String taskName, String worker, LocalDateTime dateCreated, String periodicTaskName, LocalDateTime dateStarted) {
        this.id = id;
        this.taskId = taskId;
        this.status = status;
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
        this.result = result;
        this.dateDone = dateDone;
        this.traceback = traceback;
        this.meta = meta;
        this.taskArgs = taskArgs;
        this.taskKwargs = taskKwargs;
        this.taskName = taskName;
        this.worker = worker;
        this.dateCreated = dateCreated;
        this.periodicTaskName = periodicTaskName;
        this.dateStarted = dateStarted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getDateDone() {
        return dateDone;
    }

    public void setDateDone(LocalDateTime dateDone) {
        this.dateDone = dateDone;
    }

    public String getTraceback() {
        return traceback;
    }

    public void setTraceback(String traceback) {
        this.traceback = traceback;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getTaskArgs() {
        return taskArgs;
    }

    public void setTaskArgs(String taskArgs) {
        this.taskArgs = taskArgs;
    }

    public String getTaskKwargs() {
        return taskKwargs;
    }

    public void setTaskKwargs(String taskKwargs) {
        this.taskKwargs = taskKwargs;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPeriodicTaskName() {
        return periodicTaskName;
    }

    public void setPeriodicTaskName(String periodicTaskName) {
        this.periodicTaskName = periodicTaskName;
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }
}

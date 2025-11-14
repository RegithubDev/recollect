package com.resustainability.aakri.entity.django;

import jakarta.persistence.*;

@Entity
@Table(
        name = DjangoCeleryResultsChordCounter.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"group_id"})
        }
)
public class DjangoCeleryResultsChordCounter {
    public static final String TABLE_NAME = "django_celery_results_chordcounter";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_id", nullable = false, length = 255)
    private String groupId;

    @Column(name = "sub_tasks", nullable = false, columnDefinition = "LONGTEXT")
    private String subTasks;

    @Column(name = "count", nullable = false)
    private Integer count;

    public DjangoCeleryResultsChordCounter() {}

    public DjangoCeleryResultsChordCounter(Integer id, String groupId, String subTasks, Integer count) {
        this.id = id;
        this.groupId = groupId;
        this.subTasks = subTasks;
        this.count = count;
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

    public String getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(String subTasks) {
        this.subTasks = subTasks;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

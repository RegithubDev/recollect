package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = FrequentlyAskedQuestions.TABLE_NAME)
public class FrequentlyAskedQuestions {
    public static final String TABLE_NAME = "backend_frequentlyaskedquestions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "question", nullable = false, columnDefinition = "LONGTEXT")
    private String question;

    @Column(name = "answer", nullable = false, columnDefinition = "LONGTEXT")
    private String answer;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}

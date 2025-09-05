package com.example.study.groups.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

// StudyGroup.java
@Entity @Table(name="study_groups")
@Getter @Setter
public class StudyGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    private Long leaderId;
    private String title;
    @Column(length=2000) private String description;
    private Integer maxMembers;
    private String category;
    private Double latitude;
    private Double longitude;
    private Instant createdAt;
    private Instant updatedAt;
}

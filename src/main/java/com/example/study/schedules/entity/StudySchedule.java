package com.example.study.schedules.entity;

import com.example.study.groups.entity.StudyGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

// StudySchedule.java
@Entity @Table(name="study_schedules")
@Getter @Setter
public class StudySchedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="group_id")
    private StudyGroup group;
    private String title;
    @Column(length=1000) private String description;
    private Instant startTime;
    private Instant endTime;
    private String location;
    private Instant createdAt;
}

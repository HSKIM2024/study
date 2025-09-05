package com.example.study.attendance.entity;

import com.example.study.schedules.entity.StudySchedule;
import com.example.study.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

// Attendance.java
@Entity @Table(name="attendance")
@Getter @Setter
public class Attendance {
    @EmbeddedId
    private AttendanceId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("scheduleId")
    @JoinColumn(name="schedule_id")
    private StudySchedule schedule;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
    @JoinColumn(name="user_id")
    private Users user;

    private String status;    // e.g. CHECKED_IN, ABSENT
    private Instant checkedAt;
}

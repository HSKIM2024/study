package com.example.study.attendance.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

// AttendanceId.java
@Embeddable
@Getter @Setter @EqualsAndHashCode
public class AttendanceId implements Serializable {
    private Long scheduleId;
    private Long userId;
}


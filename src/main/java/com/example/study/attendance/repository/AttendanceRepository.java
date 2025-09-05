package com.example.study.attendance.repository;

import com.example.study.attendance.entity.Attendance;
import com.example.study.attendance.entity.AttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {
    List<Attendance> findBySchedule_ScheduleId(Long scheduleId);
    List<Attendance> findByUser_UserId(Long userId);
}

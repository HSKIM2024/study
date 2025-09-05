package com.example.study.attendance.controller;

import com.example.study.attendance.entity.Attendance;
import com.example.study.attendance.service.AttendanceService;
import com.example.study.common.exception.NotFoundException;
import com.example.study.notifications.entity.Notification;
import com.example.study.notifications.repository.NotificationRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("check-in")
    public ResponseEntity<Attendance> checkIn(@RequestParam Long scheduleId, @RequestParam Long userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.checkIn(scheduleId, userId));
    }
}


package com.example.study.schedules.controller;

import com.example.study.schedules.entity.StudySchedule;
import com.example.study.schedules.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController @RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    record CreateScheduleReq(Long groupId, String title, Instant startTime, Instant endTime) {}

    @PostMapping
    public ResponseEntity<StudySchedule> create(@RequestBody CreateScheduleReq req){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                scheduleService.create(req.groupId(), req.title(), req.startTime(), req.endTime())
        );
    }
}


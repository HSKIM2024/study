package com.example.study.attendance.service;

import com.example.study.attendance.entity.Attendance;
import com.example.study.attendance.entity.AttendanceId;
import com.example.study.attendance.repository.AttendanceRepository;
import com.example.study.common.exception.NotFoundException;
import com.example.study.schedules.entity.StudySchedule;
import com.example.study.schedules.repository.StudyScheduleRepository;
import com.example.study.users.entity.Users;
import com.example.study.users.repository.UsersRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.Instant;

/**
@Service @RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepo;
    private final StudyScheduleRepository scheduleRepo;
    private final UsersRepository usersRepo;

    @Transactional
    public Attendance checkIn(Long scheduleId, Long userId){
        StudySchedule s = scheduleRepo.findById(scheduleId).orElseThrow(() -> new NotFoundException("Schedule not found"));
        Users u = usersRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        AttendanceId id = new AttendanceId(); id.setScheduleId(scheduleId); id.setUserId(userId);
        Attendance a = new Attendance(); a.setId(id); a.setSchedule(s); a.setUser(u);
        a.setStatus("CHECKED_IN"); a.setCheckedAt(Instant.now());
        return attendanceRepo.save(a);
    }
}
 **/


@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final StudyScheduleRepository scheduleRepo;
    private final UsersRepository usersRepo;

    // Create: 체크인(기존 레코드가 있으면 상태만 변경하고 시간 갱신)
    @Transactional
    public Attendance checkIn(Long scheduleId, Long userId){
        StudySchedule s = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));
        Users u = usersRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        AttendanceId id = new AttendanceId();
        id.setScheduleId(scheduleId);
        id.setUserId(userId);

        Attendance a = attendanceRepo.findById(id).orElse(new Attendance());
        a.setId(id);
        a.setSchedule(s);
        a.setUser(u);
        a.setStatus("CHECKED_IN");
        a.setCheckedAt(Instant.now());
        return attendanceRepo.save(a);
    }

    // Read: 단건
    @Transactional(readOnly = true)
    public Attendance get(Long scheduleId, Long userId){
        AttendanceId id = new AttendanceId();
        id.setScheduleId(scheduleId);
        id.setUserId(userId);
        return attendanceRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendance not found"));
    }

    // Read: 스케줄별 목록
    @Transactional(readOnly = true)
    public List<Attendance> listBySchedule(Long scheduleId){
        return attendanceRepo.findBySchedule_ScheduleId(scheduleId);
    }

    // Read: 사용자별 목록
    @Transactional(readOnly = true)
    public List<Attendance> listByUser(Long userId){
        return attendanceRepo.findByUser_UserId(userId);
    }

    // Update: 상태 변경(예: CHECKED_IN, ABSENT, LATE)
    @Transactional
    public Attendance updateStatus(Long scheduleId, Long userId, String status){
        Attendance a = get(scheduleId, userId);
        a.setStatus(status);
        if ("CHECKED_IN".equalsIgnoreCase(status)) {
            a.setCheckedAt(Instant.now());
        }
        return a; // 변경감지로 업데이트
    }

    // Delete: 출석 기록 삭제
    @Transactional
    public void delete(Long scheduleId, Long userId){
        AttendanceId id = new AttendanceId();
        id.setScheduleId(scheduleId);
        id.setUserId(userId);
        if (!attendanceRepo.existsById(id)) {
            throw new NotFoundException("Attendance not found");
        }
        attendanceRepo.deleteById(id);
    }
}



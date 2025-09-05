package com.example.study.schedules.service;

import com.example.study.common.exception.NotFoundException;
import com.example.study.groups.entity.StudyGroup;
import com.example.study.groups.repository.StudyGroupRepository;
import com.example.study.schedules.entity.StudySchedule;
import com.example.study.schedules.repository.StudyScheduleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.time.Instant;

/**
@Service @RequiredArgsConstructor
public class ScheduleService {
    private final StudyScheduleRepository scheduleRepo;
    private final StudyGroupRepository groupRepo;

    @Transactional
    public StudySchedule create(Long groupId, String title, Instant start, Instant end){
        StudyGroup g = groupRepo.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));
        StudySchedule s = new StudySchedule();
        s.setGroup(g); s.setTitle(title); s.setStartTime(start); s.setEndTime(end);
        s.setCreatedAt(Instant.now());
        return scheduleRepo.save(s);
    }
}
**/



@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final StudyScheduleRepository scheduleRepo;
    private final StudyGroupRepository groupRepo;

    // Create
    @Transactional
    public StudySchedule create(Long groupId, String title, Instant start, Instant end){
        StudyGroup g = groupRepo.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        StudySchedule s = new StudySchedule();
        s.setGroup(g);
        s.setTitle(title);
        s.setStartTime(start);
        s.setEndTime(end);
        s.setCreatedAt(Instant.now());
        return scheduleRepo.save(s);
    }

    // Read: 단건
    @Transactional(readOnly = true)
    public StudySchedule get(Long scheduleId){
        return scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));
    }

    // Read: 전체 페이징
    @Transactional(readOnly = true)
    public Page<StudySchedule> getPage(Pageable pageable){
        return scheduleRepo.findAll(pageable);
    }

    // Read: 그룹별 목록
    @Transactional(readOnly = true)
    public List<StudySchedule> getByGroup(Long groupId){
        return scheduleRepo.findByGroup_GroupId(groupId);
    }

    // Update: 전체 갱신/부분 수정
    @Transactional
    public StudySchedule update(Long scheduleId, String title, Instant start, Instant end, String location, String description){
        StudySchedule s = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));
        if (title != null) s.setTitle(title);
        if (start != null) s.setStartTime(start);
        if (end != null) s.setEndTime(end);
        if (location != null) s.setLocation(location);
        if (description != null) s.setDescription(description);
        return s; // 변경감지로 업데이트
    }

    // Delete
    @Transactional
    public void delete(Long scheduleId){
        if (!scheduleRepo.existsById(scheduleId)){
            throw new NotFoundException("Schedule not found");
        }
        scheduleRepo.deleteById(scheduleId);
    }
}


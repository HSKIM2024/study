package com.example.study.schedules.repository;

import com.example.study.schedules.entity.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
    List<StudySchedule> findByGroup_GroupId(Long groupId);
}

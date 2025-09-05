package com.example.study.groups.repository;

import com.example.study.groups.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    List<StudyGroup> findByLeaderId(Long leaderId);
}

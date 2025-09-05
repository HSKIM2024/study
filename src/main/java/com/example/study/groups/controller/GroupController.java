package com.example.study.groups.controller;

import com.example.study.groups.entity.StudyGroup;
import com.example.study.groups.service.GroupService;
import com.example.study.members.entity.GroupMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    record CreateGroupReq(Long leaderId, String title) {}
    record AddMemberReq(Long userId, String role) {}

    @PostMapping
    public ResponseEntity<StudyGroup> create(@RequestBody CreateGroupReq req){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                groupService.createGroup(req.leaderId(), req.title())
        );
    }

    @PostMapping("{groupId}/members")
    public ResponseEntity<GroupMember> addMember(@PathVariable Long groupId, @RequestBody AddMemberReq req){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                groupService.addMember(groupId, req.userId(), req.role())
        );
    }
}


package com.example.study.groups.service;

import com.example.study.common.exception.NotFoundException;
import com.example.study.groups.entity.StudyGroup;
import com.example.study.groups.repository.StudyGroupRepository;
import com.example.study.members.entity.GroupMember;
import com.example.study.members.entity.GroupMemberId;
import com.example.study.members.repository.GroupMemberRepository;
import com.example.study.users.entity.Users;
import com.example.study.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
@Service @RequiredArgsConstructor
public class GroupService {
    private final StudyGroupRepository groupRepo;
    private final UsersRepository usersRepo;
    private final GroupMemberRepository memberRepo;

    @Transactional
    public StudyGroup createGroup(Long leaderId, String title){
        Users leader = usersRepo.findById(leaderId).orElseThrow(() -> new NotFoundException("Leader not found"));
        StudyGroup g = new StudyGroup();
        g.setLeaderId(leader.getUserId());
        g.setTitle(title);
        g.setCreatedAt(Instant.now());
        g.setUpdatedAt(Instant.now());
        g = groupRepo.save(g);

        // 리더를 멤버로 등록
        GroupMemberId id = new GroupMemberId();
        id.setGroupId(g.getGroupId()); id.setUserId(leader.getUserId());
        GroupMember m = new GroupMember();
        m.setId(id); m.setGroup(g); m.setUser(leader);
        m.setRole("LEADER"); m.setStatus("ACTIVE"); m.setJoinedAt(Instant.now());
        memberRepo.save(m);
        return g;
    }

    @Transactional
    public GroupMember addMember(Long groupId, Long userId, String role){
        StudyGroup g = groupRepo.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));
        Users u = usersRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        GroupMemberId id = new GroupMemberId(); id.setGroupId(groupId); id.setUserId(userId);
        GroupMember m = new GroupMember(); m.setId(id); m.setGroup(g); m.setUser(u);
        m.setRole(role == null ? "MEMBER" : role); m.setStatus("ACTIVE"); m.setJoinedAt(Instant.now());
        return memberRepo.save(m);
    }
}
 **/






@Service
@RequiredArgsConstructor
public class GroupService {

    private final StudyGroupRepository groupRepo;
    private final UsersRepository usersRepo;
    private final GroupMemberRepository memberRepo;

    // Create 그룹 생성 + 리더 자동 등록
    @Transactional
    public StudyGroup createGroup(Long leaderId, String title){
        Users leader = usersRepo.findById(leaderId)
                .orElseThrow(() -> new NotFoundException("Leader not found"));

        StudyGroup g = new StudyGroup();
        g.setLeaderId(leader.getUserId());
        g.setTitle(title);
        g.setCreatedAt(Instant.now());
        g.setUpdatedAt(Instant.now());
        g = groupRepo.save(g);

        GroupMemberId id = new GroupMemberId();
        id.setGroupId(g.getGroupId());
        id.setUserId(leader.getUserId());
        GroupMember m = new GroupMember();
        m.setId(id); m.setGroup(g); m.setUser(leader);
        m.setRole("LEADER"); m.setStatus("ACTIVE"); m.setJoinedAt(Instant.now());
        memberRepo.save(m);
        return g;
    }

    // Read 단건
    @Transactional(readOnly = true)
    public StudyGroup get(Long groupId){
        return groupRepo.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
    }

    // Read 전체 페이징
    @Transactional(readOnly = true)
    public Page<StudyGroup> getPage(Pageable pageable){
        return groupRepo.findAll(pageable);
    }

    // Read: 리더가 만든 그룹 목록
    @Transactional(readOnly = true)
    public List<StudyGroup> getByLeader(Long leaderId){
        return groupRepo.findByLeaderId(leaderId);
    }

    // Update: 제목/설명/카테고리/최대인원/좌표 등 부분 수정
    @Transactional
    public StudyGroup update(Long groupId, String title, String description,
                             String category, Integer maxMembers, Double latitude, Double longitude){
        StudyGroup g = groupRepo.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        if (title != null) g.setTitle(title);
        if (description != null) g.setDescription(description);
        if (category != null) g.setCategory(category);
        if (maxMembers != null) g.setMaxMembers(maxMembers);
        if (latitude != null) g.setLatitude(latitude);
        if (longitude != null) g.setLongitude(longitude);
        g.setUpdatedAt(Instant.now());
        return g; // 변경감지로 업데이트 반영
    }

    // Delete: 그룹 삭제(연관 멤버 제거는 FK cascade 또는 별도 처리)
    @Transactional
    public void delete(Long groupId){
        if (!groupRepo.existsById(groupId)) {
            throw new NotFoundException("Group not found");
        }
        // 필요 시 memberRepo.deleteAllByGroup_GroupId(groupId) 등으로 선삭제
        groupRepo.deleteById(groupId);
    }

    // Member: 추가
    @Transactional
    public GroupMember addMember(Long groupId, Long userId, String role){
        StudyGroup g = groupRepo.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        Users u = usersRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        GroupMemberId id = new GroupMemberId();
        id.setGroupId(groupId);
        id.setUserId(userId);

        GroupMember m = new GroupMember();
        m.setId(id); m.setGroup(g); m.setUser(u);
        m.setRole(role == null ? "MEMBER" : role);
        m.setStatus("ACTIVE");
        m.setJoinedAt(Instant.now());
        return memberRepo.save(m);
    }

    // Member: 역할 변경
    @Transactional
    public GroupMember changeRole(Long groupId, Long userId, String newRole){
        GroupMember m = memberRepo.findById(new GroupMemberId(){{
            setGroupId(groupId); setUserId(userId);
        }}).orElseThrow(() -> new NotFoundException("Member not found"));
        m.setRole(newRole);
        return m;
    }

    // Member: 제거
    @Transactional
    public void removeMember(Long groupId, Long userId){
        GroupMemberId id = new GroupMemberId();
        id.setGroupId(groupId); id.setUserId(userId);
        if (!memberRepo.existsById(id)) {
            throw new NotFoundException("Member not found");
        }
        memberRepo.deleteById(id);
    }

    // Member: 그룹 멤버 목록
    @Transactional(readOnly = true)
    public List<GroupMember> getMembers(Long groupId){
        return memberRepo.findByGroup_GroupId(groupId);
    }
}


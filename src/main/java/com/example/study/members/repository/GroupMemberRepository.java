package com.example.study.members.repository;

import com.example.study.members.entity.GroupMember;
import com.example.study.members.entity.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// GroupMemberRepository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    List<GroupMember> findByGroup_GroupId(Long groupId);
    boolean existsById(GroupMemberId id);
    List<GroupMember> findByUser_UserId(Long userId);
}

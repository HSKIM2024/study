package com.example.study.members.entity;

import com.example.study.groups.entity.StudyGroup;
import com.example.study.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

// GroupMember.java (중간 엔티티)
@Entity @Table(name="group_members")
@Getter @Setter
public class GroupMember {
    @EmbeddedId
    private GroupMemberId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("groupId")
    @JoinColumn(name="group_id")
    private StudyGroup group;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
    @JoinColumn(name="user_id")
    private Users user;

    private String role;    // e.g. MEMBER, MANAGER
    private String status;  // e.g. ACTIVE, PENDING
    private Instant joinedAt;
}

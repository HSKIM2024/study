package com.example.study.members.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

// GroupMemberId.java (복합키)
@Embeddable
@Getter @Setter @EqualsAndHashCode
public class GroupMemberId implements Serializable {
    private Long groupId;
    private Long userId;
}

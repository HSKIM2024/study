package com.example.study.notifications.entity;

import com.example.study.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

// Notification.java
@Entity @Table(name="notifications")
@Getter @Setter
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private Users user;

    @Column(length=2000) private String message;
    private String type;
    private Boolean isRead = false;
    private Instant createdAt;
}

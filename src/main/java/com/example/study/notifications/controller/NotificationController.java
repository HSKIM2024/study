package com.example.study.notifications.controller;

import com.example.study.common.exception.NotFoundException;
import com.example.study.notifications.entity.Notification;
import com.example.study.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepo;

    @GetMapping("{userId}")
    public List<Notification> unread(@PathVariable Long userId){
        return notificationRepo.findByUser_UserIdAndIsReadFalse(userId);
    }

    @PatchMapping("{id}/read")
    public Notification markRead(@PathVariable Long id){
        Notification n = notificationRepo.findById(id).orElseThrow(() -> new NotFoundException("Notification not found"));
        n.setIsRead(true);
        return n;
    }
}




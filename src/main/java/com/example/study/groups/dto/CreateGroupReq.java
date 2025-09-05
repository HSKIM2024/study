package com.example.study.groups.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 스터디 그룹 생성 요청 DTO
 */
public record CreateGroupReq(
        @NotNull(message = "리더 ID는 필수입니다.")
        Long leaderId,

        @NotBlank(message = "그룹 제목은 필수입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        String title,

        @Size(max = 2000, message = "설명은 2000자 이하여야 합니다.")
        String description,

        @Size(max = 50, message = "카테고리는 50자 이하여야 합니다.")
        String category,

        Integer maxMembers
) {}


package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureDto {
    @NotBlank
    private String name;
    @NotBlank
    private String schedule;
    @NotBlank
    private String content;
    @NotBlank
    private String teacher;

    public static LectureDto newInstance(
            final String name,
            final String schedule,
            final String content,
            final String teacher
    ) {
        LectureDto dto = new LectureDto();
        dto.name = name;
        dto.schedule = schedule;
        dto.content = content;
        dto.teacher = teacher;
        return dto;
    }
}

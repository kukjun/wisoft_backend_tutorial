package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindLectureDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String schedule;
    @NotBlank
    private String content;
    @NotBlank
    private String teacher;

    public static FindLectureDto newInstance(
            final Long id,
            final String name,
            final String schedule,
            final String content,
            final String teacher
    ) {
        FindLectureDto dto = new FindLectureDto();
        dto.id = id;
        dto.name = name;
        dto.schedule = schedule;
        dto.content = content;
        dto.teacher = teacher;
        return dto;
    }
}
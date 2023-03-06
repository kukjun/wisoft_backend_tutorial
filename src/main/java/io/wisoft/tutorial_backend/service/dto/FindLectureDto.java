package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindLectureDto {
    private Long id;
    private String name;
    private String schedule;
    private String content;
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
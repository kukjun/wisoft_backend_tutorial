package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    private String title;
    private String content;
    private Long lectureId;

    public static PostDto newInstance(
            String title,
            String content,
            Long lectureId
    ) {
        PostDto dto = new PostDto();
        dto.title = title;
        dto.content = content;
        dto.lectureId = lectureId;
        return dto;
    }
}

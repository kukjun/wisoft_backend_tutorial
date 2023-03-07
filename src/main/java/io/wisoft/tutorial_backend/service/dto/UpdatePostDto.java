package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePostDto {
    private String title;
    private String content;

    public static UpdatePostDto newInstance(
            String title,
            String content
    ) {
        UpdatePostDto dto = new UpdatePostDto();
        dto.title = title;
        dto.content = content;
        return dto;
    }
}

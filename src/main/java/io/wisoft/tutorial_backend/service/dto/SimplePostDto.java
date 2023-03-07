package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimplePostDto {
    private Long id;
    private String title;

    public static SimplePostDto newInstance(
            Long id,
            String title
    ) {
        SimplePostDto dto = new SimplePostDto();
        dto.id = id;
        dto.title = title;
        return dto;

    }
}

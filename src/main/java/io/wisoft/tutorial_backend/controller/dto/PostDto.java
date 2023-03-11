package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
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

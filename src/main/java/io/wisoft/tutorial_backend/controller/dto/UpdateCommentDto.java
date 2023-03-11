package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCommentDto {
    @NotBlank
    private String content;

    public static UpdateCommentDto newInstance(
            String content
    ) {
        UpdateCommentDto dto = new UpdateCommentDto();
        dto.content = content;
        return dto;
    }
}

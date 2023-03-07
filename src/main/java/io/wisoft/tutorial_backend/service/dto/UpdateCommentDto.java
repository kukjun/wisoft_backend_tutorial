package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCommentDto {
    private String content;

    public static UpdateCommentDto newInstance(
            String content
    ) {
        UpdateCommentDto dto = new UpdateCommentDto();
        dto.content = content;
        return dto;
    }
}

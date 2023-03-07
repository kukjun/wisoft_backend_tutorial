package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCommentDto {
    private String content;
    private Long postId;

    public static CreateCommentDto newInstance(
            String content,
            Long postId
    ) {
        CreateCommentDto dto = new CreateCommentDto();
        dto.content = content;
        dto.postId = postId;
        return dto;
    }
}

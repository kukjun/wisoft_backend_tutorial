package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDto {
    private Long id;
    private String content;
    private Long writerId;

    public static CommentDto newInstance(
            Long id,
            String content,
            Long writerId
    ) {
        CommentDto dto = new CommentDto();
        dto.id = id;
        dto.content = content;
        dto.writerId = writerId;
        return dto;
    }
}

package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCommentDto {
    @NotBlank
    private String content;
    @NotNull
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

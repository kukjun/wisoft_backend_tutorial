package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDto {

    @NotNull
    private Long id;
    @NotBlank
    private String content;
    @NotNull
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

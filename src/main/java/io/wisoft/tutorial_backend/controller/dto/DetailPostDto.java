package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailPostDto {

    @NotNull
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Long lectureId;
    @NotNull
    private Long writerId;
    private List<CommentDto> comments;

    public static DetailPostDto newInstance(
            Long id,
            String title,
            String content,
            Long lectureId,
            Long writerId,
            List<CommentDto> comments
    ) {
        DetailPostDto dto = new DetailPostDto();
        dto.id = id;
        dto.title = title;
        dto.content = content;
        dto.lectureId = lectureId;
        dto.writerId = writerId;
        dto.comments = comments;
        return dto;
    }

}

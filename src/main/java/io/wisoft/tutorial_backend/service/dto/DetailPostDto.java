package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailPostDto {

    private Long id;
    private String title;
    private String content;
    private Long lectureId;
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

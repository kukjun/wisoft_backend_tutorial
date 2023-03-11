package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimplePostDto {
    @NotNull
    private Long id;
    @NotBlank
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

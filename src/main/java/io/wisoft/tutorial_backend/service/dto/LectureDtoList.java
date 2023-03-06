package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureDtoList {
    private final List<LectureDto> dtoList = new ArrayList<>();

    public static LectureDtoList newInstance() {
        return new LectureDtoList();

    }
}

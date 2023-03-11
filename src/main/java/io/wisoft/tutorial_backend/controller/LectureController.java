package io.wisoft.tutorial_backend.controller;

import io.wisoft.tutorial_backend.service.LectureService;
import io.wisoft.tutorial_backend.controller.dto.FindLectureDto;
import io.wisoft.tutorial_backend.controller.dto.LectureDto;
import io.wisoft.tutorial_backend.util.jwt.JwtProvider;
import io.wisoft.tutorial_backend.util.jwt.JwtCommunicationServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures")
public class LectureController {

    private final LectureService lectureService;
    private final JwtCommunicationServlet jwtCommunicationServlet;
    private final JwtProvider jwtProvider;

    @PostMapping("")
    ResponseEntity register(HttpServletRequest request, @RequestBody @Valid final LectureDto lectureDto) {
        String token = jwtCommunicationServlet.extract(request);
        Long userId = Long.parseLong(jwtProvider.getClaims(token, "userId"));
        lectureService.createLecture(lectureDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    ResponseEntity<List<FindLectureDto>> findAll() {
        List<FindLectureDto> lectureList = lectureService.findLectureList();
        return ResponseEntity.status(HttpStatus.OK).body(lectureList);
    }

    @PatchMapping("/{lectureId}")
    ResponseEntity update(HttpServletRequest request, @PathVariable("lectureId") Long lectureId, @RequestBody @Valid final LectureDto lectureDto) {
        String token = jwtCommunicationServlet.extract(request);
        Long userId = Long.parseLong(jwtProvider.getClaims(token, "userId"));
        lectureService.updateLecture(lectureId, lectureDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

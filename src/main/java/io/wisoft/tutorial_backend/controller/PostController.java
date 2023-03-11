package io.wisoft.tutorial_backend.controller;

import io.wisoft.tutorial_backend.service.PostService;
import io.wisoft.tutorial_backend.controller.dto.DetailPostDto;
import io.wisoft.tutorial_backend.controller.dto.PostDto;
import io.wisoft.tutorial_backend.controller.dto.SimplePostDto;
import io.wisoft.tutorial_backend.controller.dto.UpdatePostDto;
import io.wisoft.tutorial_backend.util.jwt.JwtCommunicationServlet;
import io.wisoft.tutorial_backend.util.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final JwtCommunicationServlet jwtCommunicationServlet;
    private final JwtProvider jwtProvider;

    @PostMapping("")
    public ResponseEntity register(HttpServletRequest request, @RequestBody @Valid final PostDto postDto) {
        String token = jwtCommunicationServlet.extract(request);
        Long userId = Long.parseLong(jwtProvider.getClaims(token, "userId"));
        postService.createPost(postDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<List<SimplePostDto>> findAllFromLectureId(@RequestParam("lectureId") Long lectureId) {
        List<SimplePostDto> dtoList = postService.findAllPostFromLectureId(lectureId);

        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity update(HttpServletRequest request, @PathVariable("postId") Long postId, @RequestBody @Valid final UpdatePostDto updatePostDto) {
        String token = jwtCommunicationServlet.extract(request);
        Long userId = Long.parseLong(jwtProvider.getClaims(token, "userId"));
        postService.updatePost(updatePostDto, postId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<DetailPostDto> findDetail(@PathVariable("postId") Long postId) {
        DetailPostDto dto = postService.findPost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}

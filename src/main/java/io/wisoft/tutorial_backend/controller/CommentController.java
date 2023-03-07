package io.wisoft.tutorial_backend.controller;

import io.wisoft.tutorial_backend.service.CommentService;
import io.wisoft.tutorial_backend.service.dto.CreateCommentDto;
import io.wisoft.tutorial_backend.service.dto.UpdateCommentDto;
import io.wisoft.tutorial_backend.util.jwt.JwtCommunicationServlet;
import io.wisoft.tutorial_backend.util.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final JwtCommunicationServlet jwtCommunicationServlet;
    private final JwtProvider jwtProvider;

    @PostMapping("")
    public ResponseEntity register(HttpServletRequest request, @RequestBody CreateCommentDto dto) {
        String token = jwtCommunicationServlet.extract(request);
        Long userId = Long.parseLong(jwtProvider.getClaims(token, "userId"));
        commentService.registerComment(dto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity update(HttpServletRequest request, @PathVariable("commentId") Long commentId, @RequestBody UpdateCommentDto dto) {

        String token = jwtCommunicationServlet.extract(request);
        Long userId = Long.parseLong(jwtProvider.getClaims(token, "userId"));
        commentService.updateComment(dto, userId, commentId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

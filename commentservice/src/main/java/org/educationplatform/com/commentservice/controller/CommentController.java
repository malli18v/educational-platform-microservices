package org.educationplatform.com.commentservice.controller;

import org.educationplatform.com.commentservice.entity.Comment;
import org.educationplatform.com.commentservice.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(
            CommentService commentService
    ) {
        this.commentService = commentService;
    }


    // ==========================================
    // STUDENT
    // ==========================================

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/course/{courseId}")
    public ResponseEntity<Comment> createComment(
            @PathVariable Long courseId,
            @RequestBody String content,
            Authentication authentication
    ) {

        String studentEmail =
                authentication.getName();

        Comment comment =
                commentService.createComment(
                        courseId,
                        studentEmail,
                        content
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comment);
    }


    // ==========================================
    // STUDENT + TEACHER
    // ==========================================

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Comment>> getCourseComments(
            @PathVariable Long courseId
    ) {

        return ResponseEntity.ok(
                commentService
                        .getCommentsByCourse(courseId)
        );
    }


    // ==========================================
    // STUDENT
    // ==========================================

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my")
    public ResponseEntity<List<Comment>> getMyComments(
            Authentication authentication
    ) {

        String studentEmail =
                authentication.getName();

        return ResponseEntity.ok(
                commentService
                        .getCommentsByStudent(studentEmail)
        );
    }


    // ==========================================
    // DELETE
    // ==========================================

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long id
    ) {

        commentService.deleteComment(id);

        return ResponseEntity.ok(
                "Comment deleted successfully"
        );
    }
}
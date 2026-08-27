package org.educationplatform.com.commentservice.service;

import org.educationplatform.com.commentservice.entity.Comment;
import org.educationplatform.com.commentservice.repository.CommentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(
            CommentRepository commentRepository
    ) {
        this.commentRepository = commentRepository;
    }

    // Student creates comment
    public Comment createComment(
            Long courseId,
            String studentEmail,
            String content
    ) {

        Comment comment = new Comment(
                courseId,
                studentEmail,
                content
        );

        return commentRepository.save(comment);
    }

    // Get comments for a course
    public List<Comment> getCommentsByCourse(
            Long courseId
    ) {

        return commentRepository.findByCourseId(courseId);
    }

    // Get comments written by a student
    public List<Comment> getCommentsByStudent(
            String studentEmail
    ) {

        return commentRepository
                .findByStudentEmail(studentEmail);
    }

    // Delete comment
    public void deleteComment(Long id) {

        if (!commentRepository.existsById(id)) {

            throw new RuntimeException(
                    "Comment not found with id: " + id
            );
        }

        commentRepository.deleteById(id);
    }
}
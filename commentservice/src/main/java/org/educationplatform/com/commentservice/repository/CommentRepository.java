package org.educationplatform.com.commentservice.repository;

import org.educationplatform.com.commentservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {

    List<Comment> findByCourseId(Long courseId);

    List<Comment> findByStudentEmail(String studentEmail);
}

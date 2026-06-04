package com.team.user_admin_system.module.repository;

import com.team.user_admin_system.module.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByContentTypeAndContentId(Question.ContentType contentType, Integer contentId);

    List<Question> findByContentType(Question.ContentType contentType);

    @Query("SELECT q FROM Question q ORDER BY FUNCTION('RAND')")
    List<Question> findRandomQuestions(Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.contentType = :type ORDER BY FUNCTION('RAND')")
    List<Question> findRandomByType(@Param("type") Question.ContentType type, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.contentType = :type AND q.contentId = :id ORDER BY FUNCTION('RAND')")
    List<Question> findRandomByContent(@Param("type") Question.ContentType type, @Param("id") Integer id, Pageable pageable);
}
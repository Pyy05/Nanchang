package com.team.user_admin_system.module.repository;

import com.team.user_admin_system.module.entity.QuizRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface QuizRecordRepository extends JpaRepository<QuizRecord, Integer> {

    long countByUserId(Integer userId);
    
    long countByUserIdAndIsCorrect(Integer userId, Boolean isCorrect);
    
    long countByCreateTimeBetween(LocalDateTime start, LocalDateTime end);
    
    long countByUserIdAndCreateTimeBetween(Integer userId, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT r.contentType, COUNT(r) as count FROM QuizRecord r GROUP BY r.contentType")
    List<Object[]> countByContentType();
    
    @Query("SELECT r.userId, SUM(r.score) as totalScore FROM QuizRecord r WHERE r.isCorrect = true GROUP BY r.userId ORDER BY totalScore DESC")
    List<Object[]> getUserScoreRank();
    
    // 修复：使用原生 SQL 查询
    @Query(value = "SELECT DATE_FORMAT(create_time, '%m-%d') as date, COUNT(*) as count " +
                   "FROM quiz_record WHERE create_time >= :startTime GROUP BY DATE_FORMAT(create_time, '%m-%d') ORDER BY date",
           nativeQuery = true)
    List<Object[]> countRecentDays(@Param("startTime") LocalDateTime startTime);
    
    // 新增：统计所有正确答题数
    long countByIsCorrect(Boolean isCorrect);
    
    List<QuizRecord> findByUserIdOrderByCreateTimeDesc(Integer userId);
}
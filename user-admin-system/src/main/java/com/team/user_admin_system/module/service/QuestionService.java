package com.team.user_admin_system.module.service;

import com.team.user_admin_system.module.entity.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    List<Question> getRandomQuestions(int count);

    List<Question> getRandomEventQuestions(int count);

    List<Question> getRandomPersonQuestions(int count);

    List<Question> getRandomQuestionsByEvent(Integer eventId, int count);

    List<Question> getRandomQuestionsByPerson(Integer personId, int count);

    Map<String, Object> submitAnswer(Integer userId, Integer questionId, String userAnswer);

    List<Question> getAllQuestions();

    Question getQuestionById(Integer id);

    Question addQuestion(Question question);

    Question updateQuestion(Integer id, Question question);

    boolean deleteQuestion(Integer id);
}

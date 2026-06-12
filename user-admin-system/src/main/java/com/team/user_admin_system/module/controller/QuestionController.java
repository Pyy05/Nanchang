package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Question;
import com.team.user_admin_system.module.service.QuestionService;
import com.team.user_admin_system.service.UserService;
import com.team.user_admin_system.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

        /**
     * 获取随机问题
     * @param count 要获取的问题数量，默认为10
     * @return 包含随机问题列表的Result对象
     */
        @GetMapping("/random")
        public Result<List<Question>> getRandomQuestions(
                @RequestParam(defaultValue = "10") int count
        ) {
            try {
                // 调用服务层方法获取指定数量的随机问题
                List<Question> questions = questionService.getRandomQuestions(count);
                // 返回成功结果，包含随机问题列表
                return Result.success(questions);
            } catch (Exception e) {
                // 记录异常信息并返回失败结果
                e.printStackTrace();
                return Result.fail("获取题目失败：" + e.getMessage());
            }
        }

        /**
     * 获取随机的历史事件类问题
     * @param count 要获取的问题数量，默认为10
     * @return 包含随机事件问题列表的Result对象
     */
        @GetMapping("/random/event")
        public Result<List<Question>> getRandomEventQuestions(
                @RequestParam(defaultValue = "10") int count
        ) {
            try {
                // 调用服务层方法获取指定数量的随机事件类问题
                List<Question> questions = questionService.getRandomEventQuestions(count);
                // 返回成功结果，包含随机事件问题列表
                return Result.success(questions);
            } catch (Exception e) {
                // 记录异常信息并返回失败结果
                e.printStackTrace();
                return Result.fail("获取事件题目失败：" + e.getMessage());
            }
        }

        /**
     * 获取随机的历史人物类问题
     * @param count 要获取的问题数量，默认为10
     * @return 包含随机人物问题列表的Result对象
     */
        @GetMapping("/random/person")
        public Result<List<Question>> getRandomPersonQuestions(
                @RequestParam(defaultValue = "10") int count
        ) {
            try {
                // 调用服务层方法获取指定数量的随机人物类问题
                List<Question> questions = questionService.getRandomPersonQuestions(count);
                // 返回成功结果，包含随机人物问题列表
                return Result.success(questions);
            } catch (Exception e) {
                // 记录异常信息并返回失败结果
                e.printStackTrace();
                return Result.fail("获取人物题目失败：" + e.getMessage());
            }
        }

        /**
     * 根据事件ID获取相关的随机问题
     * @param eventId 事件ID
     * @param count 要获取的问题数量，默认为10
     * @return 包含与指定事件相关的随机问题列表的Result对象
     */
        @GetMapping("/random/event/{eventId}")
        public Result<List<Question>> getRandomQuestionsByEvent(
                @PathVariable Integer eventId,
                @RequestParam(defaultValue = "10") int count
        ) {
            try {
                // 调用服务层方法获取与指定事件ID相关的随机问题
                List<Question> questions = questionService.getRandomQuestionsByEvent(eventId, count);
                // 返回成功结果，包含相关事件的随机问题列表
                return Result.success(questions);
            } catch (Exception e) {
                // 记录异常信息并返回失败结果
                e.printStackTrace();
                return Result.fail("获取事件相关题目失败：" + e.getMessage());
            }
        }

        /**
     * 根据人物ID获取相关的随机问题
     * @param personId 人物ID
     * @param count 要获取的问题数量，默认为10
     * @return 包含与指定人物相关的随机问题列表的Result对象
     */
        @GetMapping("/random/person/{personId}")
        public Result<List<Question>> getRandomQuestionsByPerson(
                @PathVariable Integer personId,
                @RequestParam(defaultValue = "10") int count
        ) {
            try {
                // 调用服务层方法获取与指定人物ID相关的随机问题
                List<Question> questions = questionService.getRandomQuestionsByPerson(personId, count);
                // 返回成功结果，包含相关人物的随机问题列表
                return Result.success(questions);
            } catch (Exception e) {
                // 记录异常信息并返回失败结果
                e.printStackTrace();
                return Result.fail("获取人物相关题目失败：" + e.getMessage());
            }
        }

        /**
     * 提交用户答案
     * @param request 包含用户ID、问题ID和用户答案的请求体
     * @return 包含提交结果信息的Result对象
     */
        @PostMapping("/submit")
        public Result<Map<String, Object>> submitAnswer(@RequestBody Map<String, Object> request) {
            try {
                // 从请求体中提取参数
                Integer userId = (Integer) request.get("userId");
                Integer questionId = (Integer) request.get("questionId");
                String userAnswer = (String) request.get("answer");
    
                // 验证必要参数是否存在
                if (questionId == null || userAnswer == null) {
                    return Result.fail("参数不完整");
                }
    
                // 调用服务层方法处理答案提交逻辑
                Map<String, Object> result = questionService.submitAnswer(userId, questionId, userAnswer);
                // 返回成功结果，包含答案提交的详细信息
                return Result.success(result);
            } catch (Exception e) {
                // 记录异常信息并返回失败结果
                e.printStackTrace();
                return Result.fail("提交答案失败：" + e.getMessage());
            }
        }

        /**
     * 获取积分排行榜
     * @param limit 要返回的用户数量限制，默认为10
     * @return 包含排行榜信息的Result对象
     */
        @GetMapping("/leaderboard")
        public Result<List<Map<String, Object>>> getLeaderboard(
                @RequestParam(defaultValue = "10") int limit
        ) {
            try {
                // 调用用户服务获取积分排行榜
                List<com.team.user_admin_system.entity.SysUser> users = userService.getLeaderboard(limit);
                
                // 将用户实体转换为简单的Map格式，只保留用户名和积分
                List<Map<String, Object>> leaderboard = users.stream().map(user -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("username", user.getUsername());
                    item.put("points", user.getPoints() != null ? user.getPoints() : 0);
                    return item;
                }).toList();
                
                // 返回成功结果，包含排行榜数据
                return Result.success(leaderboard);
            } catch (Exception e) {
                // 记录异常信息并返回失败结果
                e.printStackTrace();
                return Result.fail("获取排行榜失败：" + e.getMessage());
            }
        }

        @GetMapping("/list")
    public Result<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionService.getAllQuestions();
            return Result.success(questions);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取题目列表失败：" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Question> getQuestionById(@PathVariable Integer id) {
        try {
            Question question = questionService.getQuestionById(id);
            if (question != null) {
                return Result.success(question);
            } else {
                return Result.fail("题目不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取题目失败：" + e.getMessage());
        }
    }

        @PostMapping("/add")
    public Result<Question> addQuestion(@RequestBody Question question) {
        try {
            if (question.getContent() == null || question.getContent().trim().isEmpty()) {
                return Result.fail("题目内容不能为空");
            }
            if (question.getOptionA() == null || question.getOptionA().trim().isEmpty()) {
                return Result.fail("选项A不能为空");
            }
            if (question.getOptionB() == null || question.getOptionB().trim().isEmpty()) {
                return Result.fail("选项B不能为空");
            }
            if (question.getOptionC() == null || question.getOptionC().trim().isEmpty()) {
                return Result.fail("选项C不能为空");
            }
            if (question.getOptionD() == null || question.getOptionD().trim().isEmpty()) {
                return Result.fail("选项D不能为空");
            }
            if (question.getAnswer() == null || question.getAnswer().trim().isEmpty()) {
                return Result.fail("答案不能为空");
            }
            if (question.getContentType() == null) {
                return Result.fail("关联类型不能为空");
            }
            if (question.getContentId() == null) {
                return Result.fail("关联内容不能为空");
            }
            if (question.getAnalysis() == null || question.getAnalysis().trim().isEmpty()) {
                return Result.fail("答案解析不能为空");
            }
            if (question.getScore() == null || question.getScore() < 1) {
                question.setScore(1);
            }
            Question savedQuestion = questionService.addQuestion(question);
            return Result.success(savedQuestion);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加题目失败：" + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public Result<Question> updateQuestion(@PathVariable Integer id, @RequestBody Question question) {
        try {
            if (question.getContent() == null || question.getContent().trim().isEmpty()) {
                return Result.fail("题目内容不能为空");
            }
            if (question.getOptionA() == null || question.getOptionA().trim().isEmpty()) {
                return Result.fail("选项A不能为空");
            }
            if (question.getOptionB() == null || question.getOptionB().trim().isEmpty()) {
                return Result.fail("选项B不能为空");
            }
            if (question.getOptionC() == null || question.getOptionC().trim().isEmpty()) {
                return Result.fail("选项C不能为空");
            }
            if (question.getOptionD() == null || question.getOptionD().trim().isEmpty()) {
                return Result.fail("选项D不能为空");
            }
            if (question.getAnswer() == null || question.getAnswer().trim().isEmpty()) {
                return Result.fail("答案不能为空");
            }
            if (question.getContentType() == null) {
                return Result.fail("关联类型不能为空");
            }
            if (question.getContentId() == null) {
                return Result.fail("关联内容不能为空");
            }
            if (question.getAnalysis() == null || question.getAnalysis().trim().isEmpty()) {
                return Result.fail("答案解析不能为空");
            }
            if (question.getScore() == null || question.getScore() < 1) {
                question.setScore(1);
            }
            Question updatedQuestion = questionService.updateQuestion(id, question);
            if (updatedQuestion != null) {
                return Result.success(updatedQuestion);
            } else {
                return Result.fail("题目不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("更新题目失败：" + e.getMessage());
        }
    }

    

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteQuestion(@PathVariable Integer id) {
        try {
            boolean deleted = questionService.deleteQuestion(id);
            if (deleted) {
                return Result.success("删除成功");
            } else {
                return Result.fail("题目不存在或删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除题目失败：" + e.getMessage());
        }
    }
}

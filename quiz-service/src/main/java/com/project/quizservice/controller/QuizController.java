package com.project.quizservice.controller;


import com.project.quizservice.model.QuestionWrapper;
import com.project.quizservice.model.QuizDto;
import com.project.quizservice.model.Response;
import com.project.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> creatQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable Integer id){
        return quizService.getQuizQuestion(id);

    }
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> getScore(@PathVariable Integer id,@RequestBody List<Response> response){
         return quizService.getScore(id,response);
    }
}

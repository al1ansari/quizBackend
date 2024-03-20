package com.project.quizservice.service;


import com.project.quizservice.dao.QuizDao;
import com.project.quizservice.feign.QuizInterface;
import com.project.quizservice.model.QuestionWrapper;
import com.project.quizservice.model.Quiz;
import com.project.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer>  question = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionId(question);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.OK);


    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        List<QuestionWrapper> qw = quizInterface.getQuestionsFromId(quizDao.findById(id).get().getQuestionId()).getBody();
        return new ResponseEntity<>(qw,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(Integer id, List<Response> response) {
        Integer score = quizInterface.getScore(response).getBody();
        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}

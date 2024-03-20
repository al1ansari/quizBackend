package com.project.questionservice.service;


import com.project.questionservice.QuestionServiceApplication;
import com.project.questionservice.dao.QuestionDao;
import com.project.questionservice.model.Question;
import com.project.questionservice.model.QuestionWrapper;
import com.project.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return  new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question questions) {
        try {
            questionDao.save(questions);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
       return new ResponseEntity<>("UnSuccessfull",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addAllQuestion(List<Question> question) {
        questionDao.saveAll(question);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQuestions) {
            List<Integer> questionId= questionDao.findRandomQuestionByCategory(category,numQuestions);
            return new ResponseEntity<>(questionId,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> qWrapper = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Integer qId:questionIds){

            questions.add(questionDao.findById(qId).get());
        }
        for(Question q: questions){
            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(), q.getOption4());
            qWrapper.add(questionWrapper);
        }
        return new ResponseEntity<>(qWrapper,HttpStatus.OK);
    }


    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int score=0;
        for(Response r:responses){
            if(r.getResponse().equals(questionDao.findById(r.getId()).get().getRightAnswer()))
                score++;
        }
        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}

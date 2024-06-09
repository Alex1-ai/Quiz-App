package com.chidi.quizapp.service;


import com.chidi.quizapp.dao.QuestionDao;
import com.chidi.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions(){
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
//        return questionDao.findByCategory(category);

        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {

        questionDao.save(question);

        try{
             questionDao.save(question);
             return new ResponseEntity<>("success", HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ResponseEntity<>("Error" , HttpStatus.BAD_REQUEST);
//        return "success";
    }


    public ResponseEntity<String> updateQuestion(Integer id, Question newQuestionData) {
        try{


            Optional<Question> optionalQuestion = questionDao.findById(id);
            if (optionalQuestion.isPresent()) {
                Question existingQuestion = optionalQuestion.get();
                existingQuestion.setQuestionTitle(newQuestionData.getQuestionTitle());
                existingQuestion.setOption1(newQuestionData.getOption1());
                existingQuestion.setOption2(newQuestionData.getOption2());
                existingQuestion.setOption3(newQuestionData.getOption3());
                existingQuestion.setOption4(newQuestionData.getOption4());
                existingQuestion.setRightAnswer(newQuestionData.getRightAnswer());
                existingQuestion.setDifficultyLevel(newQuestionData.getDifficultyLevel());
                existingQuestion.setCategory(newQuestionData.getCategory());
                questionDao.save(existingQuestion);
                return new ResponseEntity<>("Update success", HttpStatus.OK);
//                return "update success";
            } else {
                return new ResponseEntity<>("Bad Request" , HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ResponseEntity<>("Error" , HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try{
            Optional<Question> optionalQuestion = questionDao.findById(id);
            if (optionalQuestion.isPresent()) {
                questionDao.deleteById(id);
                return new ResponseEntity<>("Deleted Successfully" , HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Question not Found" , HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ResponseEntity<>("Error" , HttpStatus.BAD_REQUEST);


    }
}

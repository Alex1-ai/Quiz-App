package com.chidi.quizapp.controller;


import com.chidi.quizapp.model.Question;
import com.chidi.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("")
    public ResponseEntity<List<Question>> getAllQuestion(){

        return questionService.getAllQuestions();

    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable("category") String category){

        return questionService.getQuestionsByCategory(category);

    }


    @PostMapping("")
    public ResponseEntity<String> addQuestion(@RequestBody  Question question){
        return questionService.addQuestion(question);



    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") Integer id){
        return questionService.deleteQuestion( id);



    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable("id") Integer id, @RequestBody  Question question){
        return questionService.updateQuestion( id, question);



    }

}

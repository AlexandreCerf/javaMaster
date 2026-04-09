package edu.ban7.chatbotmsnmsii2527.controller;

import edu.ban7.chatbotmsnmsii2527.dao.QuestionDao;
import edu.ban7.chatbotmsnmsii2527.model.Question;
import edu.ban7.chatbotmsnmsii2527.security.AppUserDetails;
import edu.ban7.chatbotmsnmsii2527.security.IsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    protected final QuestionDao questionDao;

    @GetMapping("/question/history")
    @IsUser
    public List<Question> history(@AuthenticationPrincipal AppUserDetails userDetails){
        if(userDetails.getUser().isAdmin()){
            return questionDao.findAll();
        }
        return questionDao.findAuthorById(userDetails.getUser().getId());
    }

    public ResponseEntity<Question> getOne(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails
    ){
        Optional<Question> optional = questionDao.findById(id);
        if (optional.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Question q = optional.get();
        boolean isOwner = q.getAuthor() != null && q.getAuthor().getId().equals(userDetails.getUser().getId());

        if(!isOwner && !userDetails.getUser().isAdmin()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(q, HttpStatus.OK);
    }

}

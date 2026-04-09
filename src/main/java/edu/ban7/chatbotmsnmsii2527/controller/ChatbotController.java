package edu.ban7.chatbotmsnmsii2527.controller;

import edu.ban7.chatbotmsnmsii2527.dao.QuestionDao;
import edu.ban7.chatbotmsnmsii2527.dao.TagDao;
import edu.ban7.chatbotmsnmsii2527.dto.AskRequest;
import edu.ban7.chatbotmsnmsii2527.model.Question;
import edu.ban7.chatbotmsnmsii2527.model.Recipe;
import edu.ban7.chatbotmsnmsii2527.security.AppUserDetails;
import edu.ban7.chatbotmsnmsii2527.security.IsUser;
import edu.ban7.chatbotmsnmsii2527.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatbotController {

    protected final AiService aiService;
    protected final QuestionDao questionDao;
    protected final TagDao tagDao;

    @PostMapping("/ask")
    @IsUser
    public ResponseEntity<String> ask(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody AskRequest request) {

        List<Recipe> recipes = aiService.filterRecipes(
                request.includedTagIds(),
                request.excludedTagIds()
        );

        String reponse = aiService.askGemini(request.content(), recipes);

        Question question = new Question();
        question.setContent(request.content());
        question.setAskedAt(LocalDateTime.now());
        question.setAuthor(userDetails.getUser());
        question.setReturnedRecipes(recipes);

        if (request.includedTagIds() != null) {
            question.setIncludedTags(tagDao.findAllById(request.includedTagIds()));
        }
        if (request.excludedTagIds() != null) {
            question.setExcludedTags(tagDao.findAllById(request.excludedTagIds()));
        }

        questionDao.save(question);

        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }
}

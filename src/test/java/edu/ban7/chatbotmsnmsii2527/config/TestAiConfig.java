package edu.ban7.chatbotmsnmsii2527.config;


import edu.ban7.chatbotmsnmsii2527.dao.RecipeDao;
import edu.ban7.chatbotmsnmsii2527.model.Recipe;
import edu.ban7.chatbotmsnmsii2527.service.AiService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

@TestConfiguration
public class TestAiConfig {
    @Bean
    @Primary
    public AiService aiServiceStub(RecipeDao recipeDao){
        return new AiService(null, recipeDao){
            @Override
            public String askGemini(String prompt, List<Recipe> recipes){
                return "Response simuléee test";
            }
        };
    }
}

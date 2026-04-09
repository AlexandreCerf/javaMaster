package edu.ban7.chatbotmsnmsii2527.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import edu.ban7.chatbotmsnmsii2527.dao.RecipeDao;
import edu.ban7.chatbotmsnmsii2527.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiService {

    protected final Client client;
    protected final RecipeDao recipeDao;
    public List<Recipe> filterRecipes(List<Integer> includedTagIds, List<Integer> excludedTagIds){
        return recipeDao.findAll().stream()
                .filter(r -> includedTagIds == null || includedTagIds.isEmpty()
                        || r.getTags().stream().anyMatch(t -> includedTagIds.contains(t.getId())))
                .filter(r -> excludedTagIds == null || excludedTagIds.isEmpty()
                        || r.getTags().stream().noneMatch(t -> excludedTagIds.contains(t.getId())))
                .toList();
    }
    public String askGemini(String prompt,List<Recipe> recipes) {


        String formattedRecipes = recipes.stream()
                .map(r -> {
                    String tagsFormatted = r.getTags().stream()
                            .map(t -> t.getName())
                            .collect(Collectors.joining(","));
                    return r.getName() + "(" + tagsFormatted + ")";
                })
                .collect(Collectors.joining(" -"));

        String finalPrompt = "Parmi les recettes listées, trouve celle correspondant le plus à cette demande, " +
                "et répond comme si tu étais un serveur de restaurant '" +
                prompt + "' recettes : " + formattedRecipes;

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash-lite", finalPrompt, null);

        return response.text();
    }
}

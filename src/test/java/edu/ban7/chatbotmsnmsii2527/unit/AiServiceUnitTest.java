package edu.ban7.chatbotmsnmsii2527.unit;

import edu.ban7.chatbotmsnmsii2527.dao.RecipeDao;
import edu.ban7.chatbotmsnmsii2527.model.Recipe;
import edu.ban7.chatbotmsnmsii2527.model.Tag;
import edu.ban7.chatbotmsnmsii2527.service.AiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class AiServiceUnitTest {

    @Test
    public void filterRecipes_shouldKeepOnlyIncludedTags() {
        Tag dessert = new Tag(1, "dessert");
        Tag sud = new Tag(2, "sud");

        Recipe tarte = new Recipe();
        tarte.setId(1);
        tarte.setName("tarte");
        tarte.setTags(List.of(dessert));

        Recipe paella = new Recipe();
        paella.setId(2);
        paella.setName("paella");
        paella.setTags(List.of(sud));

        RecipeDao dao = Mockito.mock(RecipeDao.class);
        Mockito.when(dao.findAll()).thenReturn(List.of(tarte, paella));

        AiService service = new AiService(null, dao);
        List<Recipe> result = service.filterRecipes(List.of(1), null);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("tarte", result.get(0).getName());
    }

    @Test
    public void filterRecipes_shouldExcludeTags() {
        Tag arachide = new Tag(1, "arachide");
        Tag dessert = new Tag(2, "dessert");

        Recipe tarte = new Recipe();
        tarte.setId(1);
        tarte.setName("tarte");
        tarte.setTags(List.of(dessert));

        Recipe croissant = new Recipe();
        croissant.setId(2);
        croissant.setName("croissant");
        croissant.setTags(List.of(dessert, arachide));

        RecipeDao dao = Mockito.mock(RecipeDao.class);
        Mockito.when(dao.findAll()).thenReturn(List.of(tarte, croissant));

        AiService service = new AiService(null, dao);
        List<Recipe> result = service.filterRecipes(null, List.of(1));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("tarte", result.get(0).getName());
    }
}
package edu.ban7.chatbotmsnmsii2527.integration;

import edu.ban7.chatbotmsnmsii2527.config.TestAiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import(TestAiConfig.class)
class ChatbotMsnMsii2527ApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void callHelloWorld_shouldBeOk() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void callListRecipeAsUser_shouldBeForbidden() throws Exception {
        mvc.perform(get("/recipe/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("a@a") // nécessaire pour alimenter @AuthenticationPrincipal
    void callListRecipeAsAdmin_shouldBeForbidden() throws Exception {
        mvc.perform(get("/recipe/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getUserwithId1_shouldBeTheCorrectUser() throws Exception {
        mvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithUserDetails("b@b")
    void ask_shouldReturnOk() throws Exception {
        mvc.perform(post("/ask")
                        .contentType("application/json")
                        .content("{\"content\":\"j'ai faim\",\"includedTagIds\":[],\"excludedTagIds\":[]}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("b@b")
    void history_asUser_shouldBeOk() throws Exception {
        mvc.perform(get("/question/history"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("b@b")
    void getOtherUserQuestion_shouldBeForbidden() throws Exception {
        mvc.perform(get("/question/3"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("a@a")
    void recipeStats_asAdmin_shouldBeOk() throws Exception {
        mvc.perform(get("/recipe/stats"))
                .andExpect(status().isOk());
    }

}
package edu.ban7.chatbotmsnmsii2527.dao;

import edu.ban7.chatbotmsnmsii2527.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findAuthorById(Integer authorId);
    @Query("SELECT r.name, COUNT(q) FROM Question q Join q.returnedRecipes r GROUP BY r.name")
    List<Object[]> countReturnedRecipes();
}

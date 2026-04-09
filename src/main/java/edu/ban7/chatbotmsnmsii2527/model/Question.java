package edu.ban7.chatbotmsnmsii2527.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotBlank
    @Column(length = 1000)
    protected String content;

    protected LocalDateTime askedAt;

    @ManyToOne
    protected AppUser author;

    @ManyToMany
    protected List<Tag> includedTags = new ArrayList<>();

    @ManyToMany
    protected List<Tag> excludedTags = new ArrayList<>();

    @ManyToMany
    protected List<Recipe> returnedRecipes = new ArrayList<>();

}
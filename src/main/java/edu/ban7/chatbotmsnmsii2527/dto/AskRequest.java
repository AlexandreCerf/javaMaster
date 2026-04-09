package edu.ban7.chatbotmsnmsii2527.dto;

import java.util.List;

public record AskRequest (String content, List<Integer> includedTagIds, List<Integer> excludedTagIds){
}

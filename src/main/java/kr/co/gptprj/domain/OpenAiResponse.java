package kr.co.gptprj.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenAiResponse {

    @JsonProperty("choices")
    private List<Choice> choices;

    // 생성자, 게터, 세터 등이 여기에 추가될 수 있음

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}


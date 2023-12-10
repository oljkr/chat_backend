package kr.co.gptprj.domain.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenAiResponseVO {

    @JsonProperty("choices")
    private List<ChoiceVO> choices;


    public List<ChoiceVO> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceVO> choices) {
        this.choices = choices;
    }
}


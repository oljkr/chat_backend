package kr.co.gptprj.domain.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChoiceVO {

    @JsonProperty("message")
    private MessageVO message;


    public MessageVO getMessage() {
        return message;
    }

    public void setMessage(MessageVO message) {
        this.message = message;
    }
}

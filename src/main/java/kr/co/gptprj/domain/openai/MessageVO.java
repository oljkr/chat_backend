package kr.co.gptprj.domain.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageVO {

    @JsonProperty("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


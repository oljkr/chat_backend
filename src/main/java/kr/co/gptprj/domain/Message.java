package kr.co.gptprj.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    @JsonProperty("content")
    private String content;

    // 생성자, 게터, 세터 등이 여기에 추가될 수 있음

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


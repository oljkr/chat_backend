package kr.co.gptprj.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Choice {

    @JsonProperty("message")
    private Message message;

    // 생성자, 게터, 세터 등이 여기에 추가될 수 있음

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

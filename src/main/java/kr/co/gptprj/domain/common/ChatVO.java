package kr.co.gptprj.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatVO {
	
	private Long chatId;
	private char code;
	private Long modelId;
	private String title;
	private String content;
	private String genDate;
	private String genTime;
	private String editDate;
	private String editTime;
	private String delYn;
	
}

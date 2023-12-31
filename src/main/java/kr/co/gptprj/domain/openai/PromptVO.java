package kr.co.gptprj.domain.openai;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PromptVO {
	private Long num;
	
	@NotBlank
	private String prompt;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime regDate;	

}

package kr.co.gptprj.domain.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class tagVO {
	private Long tagId;
	private String tagTitle;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime registerDt;
	
	private String delYn;

}

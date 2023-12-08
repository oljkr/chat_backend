package kr.co.gptprj.service;

import kr.co.gptprj.domain.openai.MessageVO;
import kr.co.gptprj.domain.openai.PromptVO;

public interface IChatService {
	MessageVO chatWithGPT(PromptVO request) throws Exception;
	
}

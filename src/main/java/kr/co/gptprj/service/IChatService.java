package kr.co.gptprj.service;

import kr.co.gptprj.domain.Chat;
import kr.co.gptprj.domain.Prompt;

public interface IChatService {
	String chatWithGPT(Prompt request) throws Exception;
	
}

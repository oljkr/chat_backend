package kr.co.gptprj.service;

import kr.co.gptprj.domain.Message;
import kr.co.gptprj.domain.Prompt;

public interface IChatService {
	Message chatWithGPT(Prompt request) throws Exception;
	
}

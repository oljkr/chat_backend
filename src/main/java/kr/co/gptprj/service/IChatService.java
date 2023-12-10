package kr.co.gptprj.service;

import java.util.List;

import kr.co.gptprj.domain.common.ChatVO;
import kr.co.gptprj.domain.openai.MessageVO;
import kr.co.gptprj.domain.openai.PromptVO;

public interface IChatService {
	List<ChatVO> allChatList() throws Exception;
	List<ChatVO> chatWithGPT(PromptVO request) throws Exception;
//	MessageVO chatWithGPT(PromptVO request) throws Exception;
	
}

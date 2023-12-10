package kr.co.gptprj.mapper;

import java.util.List;

import kr.co.gptprj.domain.common.ChatVO;

//kr.co.gptprj.mapper.chatMapper
public interface ChatMapper {
	public List<ChatVO> selectChatList();
	public ChatVO selectChat(ChatVO chatVO);
	public int insertChat(ChatVO chatVO);	

}

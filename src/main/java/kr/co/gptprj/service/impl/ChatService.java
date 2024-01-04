package kr.co.gptprj.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.gptprj.common.DateUtil;
import kr.co.gptprj.domain.common.ChatVO;
import kr.co.gptprj.domain.openai.ChatRequest;
import kr.co.gptprj.domain.openai.MessageVO;
import kr.co.gptprj.domain.openai.OpenAiResponseVO;
import kr.co.gptprj.domain.openai.PromptVO;
import kr.co.gptprj.mapper.ChatMapper;
import kr.co.gptprj.service.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService implements IChatService {
	
	private final ChatMapper chatMapper;
	
	@Value("${api.endpoint}")
	private String openAiApiUrl;
	@Value("${api.key}")
	private String apiKey;
	
	//전체 대화 목록 조회
	@Override
	public List<ChatVO> allChatList() throws Exception {
		log.info(chatMapper.selectChatList().toString());
		return chatMapper.selectChatList();		
	}
	
	//단일 대화 조회
	public ChatVO oneChat(ChatVO chatVO) throws Exception {
		return chatMapper.selectChat(chatVO);
	}

	//대화 주고 받기
	@Override
	public List<ChatVO> chatWithGPT(PromptVO request) throws Exception {
		ChatVO chatVO = new ChatVO();
		List<ChatVO> oneChat = new ArrayList<>();
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        
        String prompt = request.getPrompt();
        String content = prompt.replace("\n", "\\n").replace("\t", "\\t").replace("\"", "\\\"").replace("\'", "\\\'");

        System.out.println(content);
        
        ObjectMapper objectMapper = new ObjectMapper();
     // 요청 바디 생성
        ChatRequest.Message message = new ChatRequest.Message("user", content);
        List<ChatRequest.Message> messagesList = Collections.singletonList(message);

        ChatRequest chatRequest = new ChatRequest(messagesList, "gpt-4-1106-preview");

        String requestBody = objectMapper.writeValueAsString(chatRequest);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        
        OpenAiResponseVO response = null;
        // 요청 보내기 & 응답받기
        RestTemplate restTemplate = new RestTemplate();
        try {
        	response = restTemplate.postForObject(openAiApiUrl, entity, OpenAiResponseVO.class);
            System.out.println(response.getChoices().get(0).getMessage().getContent().trim());
		} catch (HttpClientErrorException e) {
			// 예외 처리 코드 추가
	        System.err.println("OpenAI API 요청 실패: " + e.getRawStatusCode() + " " + e.getResponseBodyAsString());
	        throw e; // 예외를 다시 던져서 호출자에게 전달
		}
        
        chatVO.setCode('q');
        chatVO.setContent(content);
        LocalDateTime questionTime = LocalDateTime.now();
        chatVO.setGenDate(DateUtil.getCurDate(questionTime));
        chatVO.setGenTime(DateUtil.getCurTime(questionTime));
        
        log.info("question:"+chatVO.toString());
        
        // 질문을 db에 insert
        int q=chatMapper.insertChat(chatVO);
        log.info("after question:"+chatVO.toString());
        oneChat.add(oneChat(chatVO));
        log.info("question insert success:"+q);
        log.info("insertAfterOneChat1"+oneChat.toString());
        
        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
        	chatVO.setCode('a');
            chatVO.setContent(response.getChoices().get(0).getMessage().getContent().trim());
            LocalDateTime answerTime = LocalDateTime.now();
            chatVO.setGenDate(DateUtil.getCurDate(answerTime));
            chatVO.setGenTime(DateUtil.getCurTime(answerTime));
            
            log.info("answer:"+chatVO.toString());
        	//답변을 db에 insert
            int a=chatMapper.insertChat(chatVO);
            log.info("answer insert success:"+a);
            oneChat.add(oneChat(chatVO));
            log.info("insertAfterOneChat2"+oneChat.toString());

            return oneChat;
        }

        return null;
	}

}

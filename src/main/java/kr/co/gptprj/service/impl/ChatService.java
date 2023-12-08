package kr.co.gptprj.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.co.gptprj.common.DateUtil;
import kr.co.gptprj.domain.common.ChatVO;
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

	@Override
	public MessageVO chatWithGPT(PromptVO request) throws Exception {	
		ChatVO chatVO = new ChatVO();
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        
        String prompt = request.getPrompt();
        String content = prompt.replace("\n", "\\n").replace("\t", "\\t").replace("\"", "\\\"");

        System.out.println(content);
        // 요청 바디 생성
        String requestBody = String.format("{\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"model\":\"gpt-4-1106-preview\"}", content);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // 요청 보내기 & 응답받기
        RestTemplate restTemplate = new RestTemplate();
        OpenAiResponseVO response = restTemplate.postForObject(openAiApiUrl, entity, OpenAiResponseVO.class);
        System.out.println(response.getChoices().get(0).getMessage().getContent().trim());
        
        chatVO.setCode('q');
        chatVO.setContent(content);
        LocalDateTime questionTime = LocalDateTime.now();
        chatVO.setGenDate(DateUtil.getCurDate(questionTime));
        chatVO.setGenTime(DateUtil.getCurTime(questionTime));
        
        log.info("question:"+chatVO.toString());
        
        // 질문을 db에 insert
        int q=chatMapper.insertChat(chatVO);
        log.info("question insert success:"+q);
        
//        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
//            return response.getChoices().get(0).getMessage().getContent().trim();
//        }
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
            
            return response.getChoices().get(0).getMessage();
        }

        return null;
	}

}

package kr.co.gptprj.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.co.gptprj.domain.Message;
import kr.co.gptprj.domain.OpenAiResponse;
import kr.co.gptprj.domain.Prompt;
import kr.co.gptprj.service.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService implements IChatService {
	
	@Value("${api.endpoint}")
	private String openAiApiUrl;
	@Value("${api.key}")
	private String apiKey;

	@Override
	public Message chatWithGPT(Prompt request) throws Exception {
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        
        String prompt = request.getPrompt();
        String content = prompt.replace("\n", "\\n").replace("\t", "\\t").replace("\"", "\\\"");

        System.out.println(content);
        // 요청 바디 생성
        String requestBody = String.format("{\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"model\":\"gpt-4-1106-preview\"}", content);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        OpenAiResponse response = restTemplate.postForObject(openAiApiUrl, entity, OpenAiResponse.class);
        System.out.println(response.getChoices().get(0).getMessage().getContent().trim());
        
//        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
//            return response.getChoices().get(0).getMessage().getContent().trim();
//        }
        
        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage();
        }

        return null;
	}

}

package kr.co.gptprj.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.gptprj.domain.openai.MessageVO;
import kr.co.gptprj.domain.openai.PromptVO;
import kr.co.gptprj.service.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatController {

	private final IChatService service;
	
	@PostMapping
	public ResponseEntity<MessageVO> chatWithGPT(@RequestBody PromptVO request) throws Exception {
		log.info("chatWithGPT");
	
		return new ResponseEntity<>(service.chatWithGPT(request), HttpStatus.OK);
	}	

}

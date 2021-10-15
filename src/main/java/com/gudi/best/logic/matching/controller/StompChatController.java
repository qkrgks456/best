package com.gudi.best.logic.matching.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.gudi.best.logic.matching.dto.ChatMessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompChatController {

	private final SimpMessagingTemplate template; //특정 Broker 로 메세지를 전달??
	
	@MessageMapping(value = "/chat/enter")
	public void enter(ChatMessageDTO message) {
		log.info(message.toString());
		message.setMessage(message.getUsername()+"님이 채팅방에 참여했습니다.");
		template.convertAndSend("/sub/chat/room/"+message.getRoomNum(), message);
	}
	
	@MessageMapping(value = "/chat/message")
	public void message(ChatMessageDTO message) {
		log.info("찍히냐??:: "+message.toString());
		template.convertAndSend("/sub/chat/room/"+message.getRoomNum(), message);
	}
}

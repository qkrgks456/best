package com.gudi.best.logic.matching.controller;

import java.util.HashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.gudi.best.logic.matching.dto.ChatDTO;
import com.gudi.best.logic.matching.mapper.ChatMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompChatController {

	private final SimpMessagingTemplate template; //특정 Broker 로 메세지를 전달??
	private final ChatMapper chatMapper;
	
	/*
	@MessageMapping(value = "/chat/enter")
	public void enter(ChatDTO message) {
		log.info("enter 의 형태를 보자!!:: "+message);
		message.setMessage(message.getId()+"님이 접속했습니다!");
		template.convertAndSend("/sub/chat/chatSelect/"+message.getRoomNum(), message);
	}
	*/
	
	@MessageMapping(value = "/chat/message")
	public void message(ChatDTO message) {
		log.info("message 의 형태를 보자!!:: " + message); //ChatMessageDTO(roomNum=1, id=qkrgks456, dates=null, message=123, roomName=테스트)
		
		//메세지가 공백이아니고, 문자의 길이가 1000을 넘지 않는다면
		if(!message.getMessage().equals("") && message.getMessage().toString().length() < 1000 ) {
			
			//chat 테이블에 대화기록하고, chatroom 의 마지막 대화일인 lastChatDates 를 SYSDATE 로 업뎃해주자
			chatMapper.chatInsert(message.getRoomNum(),message.getId(),message.getMessage());
			chatMapper.lastChatDatesUpdate(message.getRoomNum());
			
			//lastMessage 도 업데이트 해주자!! (chatMain 에서 표시해주기 위함)
			chatMapper.lastMessageUpdate(message.getRoomNum(), message.getMessage());
			
			template.convertAndSend("/sub/chat/chatSelect/"+message.getRoomNum(), message);
			
			//상대에게 알람 보내기
			HashMap<String, String> alarmMap = new HashMap<String, String>();
			alarmMap.put("person", message.getId());
			alarmMap.put("alarmText", " 님으로부터 메세지가 도착했습니다.");
			template.convertAndSend("/sub/alarm/chatAlarm", alarmMap);
			
		}else {
			String errorMsg = "공백은 보낼수 없으며, 1000자 이하로 입력해 주세요!";
			template.convertAndSend("/sub/chat/errorMsg",errorMsg);
		}
	}



}

package com.gudi.best.logic.matching.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.gudi.best.logic.matching.dto.ChatRoomDTOTEST;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ChatRoomRepository {

	private Map<String, ChatRoomDTOTEST> chatRoomDTOMap;
	
	@PostConstruct
	private void init() {
		chatRoomDTOMap = new LinkedHashMap<>();
	}
	
	public List<ChatRoomDTOTEST> findAllRooms(){
		//채팅방 생성 순서 최근 순으로 반환
		List<ChatRoomDTOTEST> result = new ArrayList<>(chatRoomDTOMap.values());
		Collections.reverse(result);
		
		return result;
	}
	
	public ChatRoomDTOTEST findRoomByNum(String num) {
		ChatRoomDTOTEST dto = new ChatRoomDTOTEST();
		dto.setRoomName("임시방");
		dto.setRoomNum(num);
		return dto;
	}
	
	public ChatRoomDTOTEST createChatRoomDTO(String roomName) {

		ChatRoomDTOTEST room = ChatRoomDTOTEST.create(roomName);
		chatRoomDTOMap.put(room.getRoomNum(), room);
		
		return room;
	}
	
	
}

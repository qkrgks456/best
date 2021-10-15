package com.gudi.best.logic.matching.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.gudi.best.logic.matching.dto.ChatRoomDTO;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ChatRoomRepository {

	private Map<String, ChatRoomDTO> chatRoomDTOMap;
	
	@PostConstruct
	private void init() {
		chatRoomDTOMap = new LinkedHashMap<>();
	}
	
	public List<ChatRoomDTO> findAllRooms(){
		//채팅방 생성 순서 최근 순으로 반환
		List<ChatRoomDTO> result = new ArrayList<>(chatRoomDTOMap.values());
		Collections.reverse(result);
		
		return result;
	}
	
	public ChatRoomDTO findRoomByNum(String num) {
		ChatRoomDTO dto = new ChatRoomDTO();
		dto.setRoomName("임시방");
		dto.setRoomNum(num);
		return dto;
	}
	
	public ChatRoomDTO createChatRoomDTO(String roomName) {

		ChatRoomDTO room = ChatRoomDTO.create(roomName);
		chatRoomDTOMap.put(room.getRoomNum(), room);
		
		return room;
	}
	
	
}

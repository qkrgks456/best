package com.gudi.best.logic.matching.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Data
public class ChatRoomDTOTEST {

	private String roomNum;
	private String roomName;
	private String username;
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoomDTOTEST create(String roomName) {
		ChatRoomDTOTEST room = new ChatRoomDTOTEST();
		
		room.roomNum = UUID.randomUUID().toString();
		room.roomName = roomName;
		return room;
	}
	
}

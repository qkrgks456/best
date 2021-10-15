package com.gudi.best.logic.matching.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gudi.best.logic.matching.dto.ChatRoomDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {
	
	private final ChatRoomRepository repository;
	
	//채팅방 목록 조회
	@GetMapping(value = "/rooms")
	public ModelAndView rooms() {
		
		log.info("# all chat rooms");
		ModelAndView mav = new ModelAndView("chat/rooms");
		
		mav.addObject("list", repository.findAllRooms());
		
		return mav;
	}
	
	//채팅방 개설
	@PostMapping(value = "/room")
	public String create(@RequestParam String roomName, RedirectAttributes rttr) {
		
		log.info("# create chat room , roomName: " + roomName);
		rttr.addFlashAttribute("roomName" , repository.createChatRoomDTO(roomName).getRoomName());
		return "redirect:/chat/rooms";
	}
	
	//채팅방 조회
	@GetMapping("/room")
	public String getRoom(String roomNum, Model model, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		ChatRoomDTO dto = repository.findRoomByNum(roomNum);
		dto.setUsername(loginId);
		model.addAttribute("room",dto);
		return "chat/room";
	}
	
	
	
}

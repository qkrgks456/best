package com.gudi.best.logic.matching.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gudi.best.logic.matching.dto.ChatMessageDTO;
import com.gudi.best.logic.matching.mapper.ChatMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {
	
	private final ChatMapper chatMapper;
	
	//채팅방 개설
		@PostMapping(value = "/room")
		public String create(@RequestParam String roomName, @RequestParam String person, HttpSession session, RedirectAttributes rttr) {
			String loginId = (String) session.getAttribute("loginId");
			log.info("# create chat room , loginID, roomName: " + loginId + " / " + person + " / " + roomName);
			chatMapper.createRoom(loginId,person,roomName);
			rttr.addFlashAttribute("loginId" , loginId);
			//rttr.addFlashAttribute("roomName" , repository.createChatRoomDTO(roomName).getRoomName());
			return "redirect:/chat/rooms";
		}
	
	//채팅방 목록 조회
	@GetMapping(value = "/rooms")
	public ModelAndView rooms(HttpServletRequest request, HttpSession session) {
		log.info("# all chat rooms");
		ModelAndView mav = new ModelAndView("chat/rooms");
		
		//redirect 로 진입시
		if(RequestContextUtils.getInputFlashMap(request) != null) {
			Map<String, ?> reqMap = RequestContextUtils.getInputFlashMap(request);
			String loginId = (String) reqMap.get("loginId");	
			List<ChatMessageDTO> result = chatMapper.findAllRooms(loginId);
			Collections.reverse(result);
			mav.addObject("list", result);
			return mav;
		//url 로 진입시
		} else {
			String loginId = (String) session.getAttribute("loginId");
			List<ChatMessageDTO> result = chatMapper.findAllRooms(loginId);
			Collections.reverse(result);
			mav.addObject("list", result);
			return mav;
		}
	}
	

	//개인 채팅방 들어가기
	@GetMapping("/room")
	public String getRoom(String roomNum, Model model, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		
		ChatMessageDTO dto = chatMapper.findRoomByNum(roomNum);
		log.info("dto 속을보자::" + dto);
		dto.setId(loginId);
		model.addAttribute("room",dto);
		
		//이전 채팅기록도 담아서 뿌려줘야함!
		//내거, 상대방 메세지 구분 필요함
		
		return "chat/room";
	}
	
	
	
}

package com.gudi.best.logic.matching.controller;

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

import com.gudi.best.logic.matching.dto.ChatDTO;
import com.gudi.best.logic.matching.dto.ChatRoomDTO;
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
		public String create(@RequestParam String person, HttpSession session, RedirectAttributes rttr, Model model) {
			String loginId = (String) session.getAttribute("loginId");
			log.info("# create chat room , loginID, roomName: " + loginId + " / " + person + " / ");
				
			//동일한 방 생성불가능
			//상대방 아이디가 멤버에 존재하지 않는 경우도 방생성 불가능
			//나-상대방, 상대방-나 => 두개의 방이 아니라, 하나의 방으로 만들어야함
			if(chatMapper.findOverlap(loginId, person).size()>0 || chatMapper.findPerson(person)==null) {
				log.info("***방생성 불가능!!***");
				rttr.addFlashAttribute("loginId" , loginId);
				rttr.addFlashAttribute("errorMsg" , "상대방이 존재하지 않는 아이디이거나, 이미 생성된 채팅방 입니다.");
				return "redirect:/chat/rooms";
			}else{
				log.info("***방생성 쌉가능!!***");
				chatMapper.createRoom(loginId,person);
				rttr.addFlashAttribute("loginId" , loginId);
				return "redirect:/chat/rooms";
				}
			}

	
	//채팅방 목록 조회
	@GetMapping(value = "/rooms")
	public ModelAndView rooms(HttpServletRequest request, HttpSession session) {
		log.info("# all chat rooms");
		ModelAndView mav = new ModelAndView("chat/chatMain");
		
		//redirect 로 진입시
		if(RequestContextUtils.getInputFlashMap(request) != null) {
			Map<String, ?> reqMap = RequestContextUtils.getInputFlashMap(request);
			String loginId = (String) reqMap.get("loginId");	
			List<ChatRoomDTO> result = chatMapper.findAllRooms(loginId);
			mav.addObject("list", result);
			mav.addObject("loginId", loginId);
			return mav;
		//url 로 진입시
		} else {
			String loginId = (String) session.getAttribute("loginId");
			List<ChatRoomDTO> result = chatMapper.findAllRooms(loginId);
			mav.addObject("list", result);
			mav.addObject("loginId", loginId);
			return mav;
		}
	}
	

	//개인 채팅방 들어가기
	@GetMapping("/room")
	public String getRoom(String roomNum, Model model, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		model.addAttribute("loginId", loginId);
		
		ChatRoomDTO dto = chatMapper.findRoomByNum(roomNum);
		log.info("dto 속을보자::" + dto);
		dto.setId(loginId);
		model.addAttribute("room",dto);
		
		//이전 채팅기록도 담아서 뿌려줘야함!
		//내거, 상대방 메세지 구분 필요함
		List<ChatDTO> chatDTO =  chatMapper.chatFind(roomNum);
		log.info("List ChatDTO 의 형태를 보자!! :: " + chatDTO);
		model.addAttribute("chatDTO", chatDTO);
		
		return "chat/room";
	}
	
	
	
}

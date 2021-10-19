package com.gudi.best.logic.matching.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/matching")
public class MatchingController {
	
    @GetMapping("/matchingMain")
    public String matchingMain() {
        return "logic/matching/matchingMain";
    }

    @GetMapping("/loveTalk")
    public String message() {
        return "logic/matching/loveTalk";
    }

    @GetMapping("/goodList")
    public String goodList() {
        return "logic/matching/goodList";
    }

    //===================
    
    @GetMapping("/chat")
    public String chatGET(HttpSession session, Model model) {
    	log.info("@MatchingController, chatGET()");
    	String loginId = (String) session.getAttribute("loginId");
    	log.info("현재 로그인 ID :: " + loginId);
    	model.addAttribute("loginId",loginId);
    	return "chat/chatMain";
    }
    
    
}

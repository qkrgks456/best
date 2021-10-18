package com.gudi.best.logic.admin.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudi.best.logic.admin.service.AdminService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired AdminService service;
	
	@GetMapping("/main")
    public String main(Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = service.memberList(1);
		model.addAttribute("map", map);
        return "logic/admin/main";
    }
	
	@GetMapping("/main/{page}")
    public String mainPage(Model model, @PathVariable int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = service.memberList(page);
		model.addAttribute("map", map);
        return "logic/admin/main";
    }
	
	
}

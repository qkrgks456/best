package com.gudi.best.logic.couple.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.gudi.best.dto.CalenderDTO;
import com.gudi.best.logic.couple.mapper.CoupleMapper;

@Service
public class CoupleService {

	@Autowired CoupleMapper mapper;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	CalenderDTO Cdto = new CalenderDTO();
	
	@Transactional
	public void calenderEnter(String id, HashMap<String, String> param) {
		
		Cdto.setId(id);
		Cdto.setStart(param.get("start"));
		Cdto.setEnd(param.get("end"));
		Cdto.setTitle(param.get("title"));
		Cdto.setContent(param.get("content"));
		Cdto.setColor(param.get("color"));
		mapper.calenderEnter(Cdto);
	}

	public HashMap<String,Object> readCalender(String id) {
		//Cdto.setId(id);
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<CalenderDTO> list = mapper.readCalender(id);
		 map.put("list", list );
		return map;
	}

	public ModelAndView detail(String id, int cnum) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> map = mapper.detail(id,cnum);
		map.put("sTime", map.get("start").substring(11,16));
		map.put("start",map.get("start").substring(0,10));
		if(map.get("end").length()>10) {
			map.put("eTime", map.get("end").substring(11,16));
			map.put("end",map.get("end").substring(0,10));
		}
		System.out.println(map);
		mav.addObject("map", map);
		mav.setViewName("logic/couple/calenderDetail");
		return mav;
		
	}

	public ModelAndView updateForm(String id, int cnum) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> map = mapper.detail(id,cnum);
		map.put("sTime", map.get("start").substring(11,16));
		map.put("start",map.get("start").substring(0,10));
		if(map.get("end").length()>10) {
			map.put("eTime", map.get("end").substring(11,16));
			map.put("end",map.get("end").substring(0,10));
		}
		System.out.println(map);
		mav.addObject("map", map);
		mav.setViewName("logic/couple/calenderUpdateForm");
		return mav;
	}
	
	@Transactional
	public void calenderUpdate(String id, HashMap<String, String> param) {
		Cdto.setCNum(Integer.parseInt(param.get("Cnum")));
		Cdto.setId(id);
		Cdto.setStart(param.get("start"));
		Cdto.setEnd(param.get("end"));
		Cdto.setTitle(param.get("title"));
		Cdto.setContent(param.get("content"));
		Cdto.setColor(param.get("color"));
		mapper.calenderUpdate(Cdto);
		
	}

	public void calenderDel(String id, int cnum) {
		mapper.calenderDel(id,cnum);
		System.out.println("일정 삭제");
		
	}
}

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

	public HashMap<String, Object> readMomory(String id) {
		String chk = mapper.coupleChk(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(chk.equals("없음")) {
		map.put("chk", "N");
		} else if(chk.length()>9) {
			if(chk.substring(0, 5).equals("Apply")) {
				map.put("chk", "A"); //상대방의 응답을 기다립니다 보여주기
			}	else if(chk.substring(0, 8).equals("Response")) {
				map.put("chk", "R"); // 요청자의 프로필과 수락창 보여주기 -> 만들예정
			}
			}else {
			ArrayList<CalenderDTO> list = mapper.readMomory(id,chk);
			map.put("list", list);
			map.put("chk", "Y");
			}
		return map;
	}

	public void applyCouple(String Lid, String id) {
		String Aid = "Apply"+ id; //상대방에게 요청 apply+상대방 id
    	String Rid = "Response"+Lid; //요청보낸사람에게 응답 response+요청자id
    	String p1 = Aid;
    	String p2 = Lid;
		mapper.applyCouple(p1, p2);
		p1 = Rid;
		p2 = id;
		mapper.applyCouple(p1, p2);
	}

	public void choiceApply(String id, String ok) {
		String Rid = mapper.coupleChk(id);
		if(ok.equals("N")) {
		String p1 = "없음";
		String p2 = id;
		mapper.applyCouple(p1, p2);
		p2 = Rid;
		mapper.applyCouple(p1, p2);
		}else if(ok.equals("Y")) {
			String p1 = Rid.substring(8);
			String p2 = id;
			System.out.println(p1+ " :" + p2);
			mapper.applyCouple(p1, p2);
			p1 = id;
			p2 = Rid.substring(8);
			mapper.applyCouple(p1, p2);	
		}
	}
}

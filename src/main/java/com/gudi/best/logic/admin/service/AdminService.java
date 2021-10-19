package com.gudi.best.logic.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudi.best.dto.memberDTO;
import com.gudi.best.logic.admin.mapper.AdminMapper;
import com.gudi.best.util.PageNation;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminService {

	@Autowired AdminMapper mapper;

	public HashMap<String, Object> memberList(int page) {
		int total = mapper.total();
		HashMap<String, Object> map = PageNation.pagination(page, 10, total);
		if(page==1) {
			page =0;
		}else {
			page = (page - 1) * 10 + 1;
		}
		ArrayList<memberDTO> list = mapper.memberList(page);
		map.put("list", list);
		return map;
	}

	public String change(String id) {
		 String chk = mapper.admincheck(id);
		 if(chk.equals("Y")) {
			 chk = "N";
			 mapper.change(chk,id);
		 } else {
			 chk = "Y";
			 mapper.change(chk,id);
		 }
		 return chk;
	}
	
}

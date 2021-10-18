package com.gudi.best.logic.loveInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gudi.best.logic.loveInfo.mapper.LoveInfoMapper;
import com.gudi.best.util.Scheduler;

@Controller
@RequestMapping("/dateInfo")
public class LoveInfoController {
	@Autowired
	LoveInfoMapper loveInfoMapper;
	@GetMapping("/loveInfo")
    public String loveInfo() throws Exception {
		Scheduler scheduler = new Scheduler();
//		for (int i=1 ; i<=9; i++ ) {
		String result = scheduler.dataInsert(1);
			System.out.println(result);
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String, Object> map = mapper.readValue(result, HashMap.class);
			result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map.get("response"));
			HashMap<String, Object> map2 = mapper.readValue(result, HashMap.class);
			result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map2.get("body"));
			HashMap<String, Object> map3 = mapper.readValue(result, HashMap.class);
			result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map3.get("items"));
			HashMap<String, Object> map4 = mapper.readValue(result, HashMap.class);
			result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map4.get("item"));
			TypeReference<ArrayList<HashMap<String, Object>>> typeRef = new TypeReference<ArrayList<HashMap<String, Object>>>() {};
			ArrayList<HashMap<String, Object>> resultMapList = mapper.readValue(result, typeRef);
			loveInfoMapper.dataInsert(resultMapList);
			
//		}
        return "logic/dateInfo/loveInfo";
    }
	
}

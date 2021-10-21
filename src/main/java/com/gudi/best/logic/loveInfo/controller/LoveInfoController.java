package com.gudi.best.logic.loveInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gudi.best.logic.loveInfo.mapper.LoveInfoMapper;
import com.gudi.best.util.NewApiUtil;
import com.gudi.best.util.Scheduler;

@Controller
@RequestMapping("/dateInfo")
public class LoveInfoController {
    @Autowired
    LoveInfoMapper loveInfoMapper;

    @GetMapping("/tourPick")
    public String tourPick(){
    	HashMap <String,String> header = new HashMap<String,String>();
    	HashMap <String,String> param = new HashMap<String,String>();
    	String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode";
    	param.put("ServiceKey", "J0GWcsmSKRep08rNleYyFbyLzNo8BcbkWmzsOxqFeJIyHVD3456iyVy+ULCnhVw5y9WsuzCa4ZjugOCCfA0JOA==");
        param.put("pageNo", "1");
        param.put("MobileOS", "ETC");
        param.put("MobileApp", "test");   
        param.put("areaCode","1");
        param.put("areaCode","2");
        /*
        param.put("areaCode","1");   //서울 
        param.put("areaCode","2");  //인천
        param.put("areaCode","3");  // 대전 
        param.put("areaCode","4"); //대구 
        param.put("areaCode","5");  // 광주
        param.put("areaCode","6");  // 부산 
        param.put("areaCode","7"); // 울산 
        param.put("areaCode","8"); //세종 특별자치시
        param.put("areaCode","9"); // 경기도 
        param.put("areaCode","10");  //강원도 
        */
        param.put("_type", "json");
        
   
    	NewApiUtil.sendSeverMsg(url, header, param, "GET");
    	
        return "logic/dateInfo/tourPick";

        
   
    }
    
    
    @GetMapping("/tourMenu")   //관광지
    public String tourist(String tour,Model model) {
    	model.addAttribute("tour",tour);
        return "logic/dateInfo/locationPick";
    }
    
    
    
}

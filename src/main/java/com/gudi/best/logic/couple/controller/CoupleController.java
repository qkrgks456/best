package com.gudi.best.logic.couple.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import com.gudi.best.dto.ProFileDTO;
import com.gudi.best.logic.couple.mapper.CoupleMapper;
import com.gudi.best.logic.couple.service.CoupleService;
import com.gudi.best.logic.myInfo.service.MyInfoService;

@Controller
@RequestMapping("/couple")
public class CoupleController {
	
	@Autowired CoupleMapper mapper;
	@Autowired CoupleService service;
	Logger logger = LoggerFactory.getLogger(this.getClass());

   
	@GetMapping("/loveCalender")
    public String loveCalender(HttpSession session) {
        return "logic/couple/loveCalender";
    }
	
    @GetMapping("/readCalender")
    @ResponseBody
    public HashMap<String, Object> readCalender(HttpSession session) {
		String id = (String) session.getAttribute("loginId");
        return service.readCalender(id);
    }
    
    @PostMapping("/loveCalenderEnterForm")
    public String loveCalenderEnterForm(@RequestParam Date start, @RequestParam Date end, HttpSession session, Model model) {
    	String id = (String) session.getAttribute("loginId");
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	logger.info(format.format(start)+"~"+format.format(end));
    	model.addAttribute("start", format.format(start));
    	model.addAttribute("end", format.format(end));
        return "logic/couple/loveCalenderEnter";
    }
    
    @PostMapping("/loveCalenderEnter")
    public String loveCalenderEnter(@RequestParam HashMap<String , String> param, HttpSession session) {
    	System.out.println("입력데이터"+param);
    	 String id = (String) session.getAttribute("loginId");
    	 if(param.get("sTime")!=null) {
    		param.put("start", param.get("start")+"T"+param.get("sTime"));
    		//System.out.println(param.get("start"));
    	 }
    	 if(param.get("eTime")!=null) {
     		String end = param.get("end")+"T"+param.get("eTime");
     		//System.out.println(end);
     	 }
    	 service.calenderEnter(id,param);
    	 return "logic/couple/loveCalender";
        
    }
    
    @GetMapping("/CalenderDetail/{cnum}")
    public ModelAndView CalenderDetail(HttpSession session, @PathVariable int cnum) {
		String id = (String) session.getAttribute("loginId");
		return service.detail(id,cnum);
    }
    
    @GetMapping("/calenderUpdateForm/{cnum}")
    public ModelAndView calenderUpdateForm(HttpSession session, @PathVariable int cnum) {
		String id = (String) session.getAttribute("loginId");
		return service.updateForm(id,cnum);
    }

    @PostMapping("/calenderUpdate")
    public String calenderUpdate(@RequestParam HashMap<String , String> param, HttpSession session) {
    	System.out.println("업데이트 실행");
    	System.out.println("파라미터 값"+param);
    	String id = (String) session.getAttribute("loginId");
    	 if(param.get("sTime")!=null) {
    		param.put("start", param.get("start")+"T"+param.get("sTime"));
    		//System.out.println(param.get("start"));
    	 }
    	 if(param.get("eTime")!=null) {
     		String end = param.get("end")+"T"+param.get("eTime");
     		//System.out.println(end);
     	 }
    	 service.calenderUpdate(id,param);
    	 return "redirect:/couple/CalenderDetail/"+ param.get("Cnum");
        
    }
    @GetMapping("/calenderDel/{cnum}")
    public String calenderDel(HttpSession session, @PathVariable int cnum) {
		String id = (String) session.getAttribute("loginId");
		service.calenderDel(id,cnum);
		 return "redirect:/couple/loveCalender";
    }
    

    @GetMapping("/loveMemory")
    public String loveMemory(HttpSession session, Model model) {
    	String id = (String) session.getAttribute("loginId");
    	HashMap<String, Object> map = service.readMomory(id);
    	model.addAttribute("map", map);
        return "logic/couple/loveMemory";
    }
    @GetMapping("/addCoupleIdForm")
    public String addCoupleIdForm() {
        return "logic/couple/addCoupleIdForm";
    }
    
    @ResponseBody
    @GetMapping("/search")
    public HashMap<String, Object> search(String id) {
    	ArrayList<String> list = mapper.search(id);
    	HashMap<String, Object> map =new HashMap<String, Object>();
    	map.put("id", list);
    	//System.out.println(map);
        return map;
    }
    
    @Autowired MyInfoService myInfoService;
    
    @GetMapping("/searchF")
    public String searchF(String id, Model model) {
    	ArrayList<String> list = mapper.search(id);
    if(list.isEmpty()) {
    	model.addAttribute("id", "N");
    }  else {
    		 ProFileDTO dto = myInfoService.proFileDetail(id);
    	       if (dto != null) {
    	            if (StringUtils.isEmpty(dto.getImgPath())) {
    	                dto.setImgPath("/img/noImg.png");
    	            }
    	            model.addAttribute("id", "Y");
    	            model.addAttribute("dto", dto);
    	            System.out.println(dto);
    	        }
    }
    	return "logic/couple/display";
    }
    
    @GetMapping("/display/{id}")
    public String display(@PathVariable String id, Model model) {
    	System.out.println("id확인 : "+id);
        ProFileDTO dto = myInfoService.proFileDetail(id);
        if (dto != null) {
            if (StringUtils.isEmpty(dto.getImgPath())) {
                dto.setImgPath("/img/noImg.png");
            }
            model.addAttribute("id", "Y");
            model.addAttribute("dto", dto);
            System.out.println(dto);
        }
    	//HashMap<String, Object> map = mapper.display(id);
    	//model.addAttribute("id", map);
        return "logic/couple/display";
}
    
    @GetMapping("/applyCouple")
    public String applyCouple(String id, Model model) {
    	System.out.println("id확인 : "+id);
        ProFileDTO dto = myInfoService.proFileDetail(id);
        if (dto != null) {
            if (StringUtils.isEmpty(dto.getImgPath())) {
                dto.setImgPath("/img/noImg.png");
            }
            model.addAttribute("id", "Y");
            model.addAttribute("dto", dto);
        }
    	//HashMap<String, Object> map = mapper.display(id);
    	//model.addAttribute("id", map);
        return "logic/couple/display";
}
}
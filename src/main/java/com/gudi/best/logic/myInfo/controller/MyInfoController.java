package com.gudi.best.logic.myInfo.controller;

import com.gudi.best.dto.ProFileDTO;
import com.gudi.best.logic.myInfo.service.MyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/myInfo")
public class MyInfoController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MyInfoService myInfoService;

    @GetMapping("/myInfoUpdate")
    public String myInfoUpdate() {
        return "logic/myInfo/myInfoUpdate";
    }

    @GetMapping("/proFileInputForm")
    public String proFileInputForm(Model model, HttpSession session) {
        String id = (String) session.getAttribute("loginId");
        ProFileDTO dto = myInfoService.proFileDetail(id);
        if (dto != null) {
            if (StringUtils.isEmpty(dto.getImgPath())) {
                dto.setImgPath("/img/noImg.png");
            }
            model.addAttribute("dto", dto);
        }
        return "logic/myInfo/proFileInputForm";
    }

    @PostMapping("/proFileInput")
    public String proFileInput(String[] hobby, String intro, MultipartFile proFileImg, HttpSession session) {
        String id = (String) session.getAttribute("loginId");
        myInfoService.proFileInput(hobby, intro, proFileImg, id);
        return "redirect:/myInfo/proFileInputForm";
    }
}

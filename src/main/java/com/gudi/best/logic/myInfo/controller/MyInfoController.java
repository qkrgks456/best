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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/myInfo")
public class MyInfoController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MyInfoService myInfoService;

    @GetMapping("/pwChangeForm")
    public String pwChangeForm() {
        return "/logic/myInfo/pwChange";
    }

    @PostMapping("/pwChange")
    public String pwChange(Model model, HttpSession session, String pw, String changePw) {
        String id = (String) session.getAttribute("loginId");
        boolean check = myInfoService.pwChange(id, pw, changePw);
        if (check) {
            session.removeAttribute("loginId");
            session.removeAttribute("admin");
            return "startForm/loginForm";
        } else {
            model.addAttribute("suc", false);
            return "/logic/myInfo/pwChange";
        }
    }

    @GetMapping("/proFile")
    public String proFile(Model model, HttpSession session) {
        String id = (String) session.getAttribute("loginId");
        ProFileDTO dto = myInfoService.proFileDetail(id);
        if (dto != null) {
            if (StringUtils.isEmpty(dto.getImgPath())) {
                dto.setImgPath("/img/noImg.png");
            }
            model.addAttribute("dto", dto);
        }
        return "/logic/myInfo/proFile";
    }

    @PostMapping("/proFileInput")
    public String proFileInput(String[] hobby, String name, String age, String intro,
                               MultipartFile proFileImg, HttpSession session) {
        String id = (String) session.getAttribute("loginId");
        myInfoService.proFileInput(hobby, intro, proFileImg, id, name, age);
        return "redirect:/myInfo/proFile";
    }
}

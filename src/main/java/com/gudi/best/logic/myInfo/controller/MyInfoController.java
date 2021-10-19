package com.gudi.best.logic.myInfo.controller;

import com.gudi.best.dto.ProFileDTO;
import com.gudi.best.logic.member.service.MemberService;
import com.gudi.best.logic.myInfo.mapper.MyInfoMapper;
import com.gudi.best.logic.myInfo.service.MyInfoService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@Log4j2
@RequestMapping("/myInfo")
public class MyInfoController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MyInfoService myInfoService;
    @Autowired
    MemberService memberService;
    @Autowired
    MyInfoMapper mapper;

    @GetMapping("/memberDrop")
    public String memberDropForm() {
        return "logic/myInfo/memberDrop";
    }

    @PostMapping("/memberDrop")
    public String memberDrop(Model model, HttpSession session, @RequestParam HashMap<String, Object> params) {
        String enc_pass = memberService.login((String) params.get("id"));
        if (enc_pass != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean check = encoder.matches((String) params.get("pw"), enc_pass);
            if (check) {
                mapper.memberDrop((String) params.get("id"));
                session.removeAttribute("admin");
                session.removeAttribute("loginId");
                return "redirect:/";
            } else {
                model.addAttribute("suc", false);
            }
        } else {
            model.addAttribute("suc", false);
        }
        return "logic/myInfo/memberDrop";
    }

    @GetMapping("/boardList/{page}")
    public String boardList(Model model, @PathVariable int page, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        model.addAttribute("map", myInfoService.myBoardList(page, loginId));
        return "logic/myInfo/myBoardList";
    }

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

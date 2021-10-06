package com.gudi.best.member.controller;

import com.gudi.best.util.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Controller
public class MemberController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    S3Uploader s3Uploader;

    @GetMapping(value = "/")
    public String loginForm(Model model) {
        return "startForm/loginForm";
    }

    @GetMapping(value = "/member/joinForm")
    public String joinForm() {
        return "startForm/joinForm";
    }

    @PostMapping(value = "/member/join")
    public String join(Model model, HashMap<String, String> params) {

        return "redirect:/";
    }
}

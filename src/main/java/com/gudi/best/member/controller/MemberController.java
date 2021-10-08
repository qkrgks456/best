package com.gudi.best.member.controller;

import com.gudi.best.member.service.MemberService;
import com.gudi.best.util.EmailUtil;
import com.gudi.best.util.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Random;

@Controller
public class MemberController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MemberService memberService;

    @GetMapping(value = "/")
    public String loginForm() {
        return "startForm/loginForm";
    }

    @GetMapping(value = "/member/joinForm")
    public String joinForm() {
        return "startForm/joinForm";
    }

    // 회원가입
    @PostMapping(value = "/member/join")
    public String join(Model model, @RequestParam HashMap<String, String> params) {
        memberService.join(params);
        model.addAttribute("joinSuc", "<script>alert('가입성공!')</script>");
        return "startForm/loginForm";
    }

    // 로그인
    @PostMapping(value = "/member/login")
    public String login(HttpSession session, Model model, String id, String pw) {
        String enc_pass = memberService.login(id);
        if (enc_pass != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean check = encoder.matches(pw, enc_pass);
            if (check) {
                String adminCheck = memberService.adminCheck(id);
                session.setAttribute("admin", adminCheck);
                session.setAttribute("loginId", id);
                return "redirect:/main";
            } else {
                model.addAttribute("loginSuc", true);
            }
        } else {
            model.addAttribute("loginSuc", true);
        }
        return "startForm/loginForm";
    }

    // 중복 확인
    @ResponseBody
    @PostMapping(value = "/member/idCheck")
    public boolean idCheck(String id) {
        boolean suc = false;
        if (id.length() > 8) {
            String check = memberService.idCheck(id);
            if (check == null) {
                suc = true;
            }
        }
        return suc;
    }

    // 이메일 인증 번호 발사
    @ResponseBody
    @GetMapping(value = "/member/emailCheck")
    public boolean emailCheck(String email, HttpSession session) {
        return memberService.emailCheck(email, session);
    }

    // 이메일 번호 확인
    @ResponseBody
    @GetMapping(value = "/member/codeCheck")
    public HashMap<String, Object> codeCheck(String code, HttpSession session) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (code != null) {
            String emailCheckNum = (String) session.getAttribute("emailCheckNum");
            String email = (String) session.getAttribute("myEmail");
            if (code.equals(emailCheckNum)) {
                map.put("email", email);
                return map;
            }
        }
        map.put("email", "없음");
        return map;
    }
}

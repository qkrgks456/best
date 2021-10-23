package com.gudi.best.logic.member.controller;

import com.gudi.best.logic.member.service.KaKaoService;
import com.gudi.best.util.NewApiUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@Log4j2
@RequestMapping("/kakao")
public class KaKaoController {
    @Autowired
    KaKaoService service;

    @GetMapping("/loginForm")
    public String loginForm() {
        String reqUrl =
                "redirect:https://kauth.kakao.com/oauth/authorize"
                        + "?client_id=510dfee7db026dbcc8df7b0a51993201"
                        + "&redirect_uri=http://localhost:8100/kakao/callback"
                        + "&response_type=code";
        return reqUrl;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code) throws Exception {
        String access_token = service.getAccess_Token(code);
        HashMap<String, Object> map = service.proFileData(access_token);

        return null;
    }


}

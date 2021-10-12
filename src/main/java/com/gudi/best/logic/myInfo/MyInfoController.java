package com.gudi.best.logic.myInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myInfo")
public class MyInfoController {

    @GetMapping("/myInfoDetail")
    public String myInfoDetail() {
        return "logic/myInfo/myInfoDetail";
    }
}

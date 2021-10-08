package com.gudi.best.logic.couple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/couple")
public class CoupleController {

    @GetMapping("/loveCalender")
    public String loveCalender() {
        return "couple/loveCalender";
    }

    @GetMapping("/loveInfo")
    public String loveInfo() {
        return "couple/loveInfo";
    }

    @GetMapping("/loveTalk")
    public String loveTalk() {
        return "couple/loveTalk";
    }
}

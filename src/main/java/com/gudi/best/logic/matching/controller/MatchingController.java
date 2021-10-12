package com.gudi.best.logic.matching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/matching")
public class MatchingController {
    @GetMapping("/matchingMain")
    public String matchingMain() {
        return "logic/matching/matchingMain";
    }

    @GetMapping("/loveTalk")
    public String message() {
        return "logic/matching/loveTalk";
    }

    @GetMapping("/goodList")
    public String goodList() {
        return "logic/matching/goodList";
    }
}

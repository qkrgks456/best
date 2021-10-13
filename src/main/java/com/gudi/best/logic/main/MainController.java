package com.gudi.best.logic.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String main() {

        return "logic/mainPage/main";
    }
}

package com.gudi.best.logic.loveBoard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loveBoard")
public class BoardController {
    @GetMapping("/list")
    public String list() {
        return "logic/loveBoard/boardList";
    }
}

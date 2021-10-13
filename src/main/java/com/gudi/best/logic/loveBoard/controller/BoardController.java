package com.gudi.best.logic.loveBoard.controller;

import com.gudi.best.dto.BoardDTO;
import com.gudi.best.logic.loveBoard.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/loveBoard")
public class BoardController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    BoardService boardService;

    @GetMapping("/boardWriteForm")
    public String boardWriteForm() {
        return "logic/loveBoard/boardWrite";
    }

    @GetMapping("/list/{page}")
    public String list(@PathVariable int page, Model model) {
        HashMap<String, Object> map = boardService.list(page);
        model.addAttribute("map", map);
        model.addAttribute("page", page);
        return "logic/loveBoard/boardList";
    }

    @ResponseBody
    @PostMapping("/boardWrite")
    public int boardWrite(HttpSession session, String title, String content, MultipartFile[] files) {
        String id = (String) session.getAttribute("loginId");
        return boardService.boardWrite(title, content, files, id);
    }
}

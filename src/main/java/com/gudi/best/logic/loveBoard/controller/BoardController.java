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

    @GetMapping("/boardUpdateForm/{boardNum}/{page}")
    public String boardUpdateForm(HttpSession session, @PathVariable int boardNum, @PathVariable int page, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        HashMap<String, Object> map = boardService.boardDetail(boardNum, loginId);
        model.addAttribute("map", map);
        model.addAttribute("page", page);
        return "logic/loveBoard/boardUpdate";
    }

    @GetMapping("/boardDetail/{boardNum}/{page}")
    public String boardDetail(HttpSession session, @PathVariable int boardNum, @PathVariable int page, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        HashMap<String, Object> map = boardService.boardDetail(boardNum, loginId);
        model.addAttribute("map", map);
        model.addAttribute("page", page);
        return "logic/loveBoard/boardDetail";
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
        int boardNum = boardService.boardWrite(title, content, files, id);
        return boardNum;
    }

    @ResponseBody
    @PostMapping("/boardUpdate")
    public int boardUpdate(HttpSession session, String boardNum, String title, String content, MultipartFile[] files) {
        String id = (String) session.getAttribute("loginId");
        boardService.boardUpdate(title, content, files, boardNum, id);
        return 0;
    }

    @ResponseBody
    @GetMapping("/boardDelete/{boardNum}")
    public boolean boardDelete(@PathVariable int boardNum) {
        boardService.boardDelete(boardNum);
        return true;
    }

    @ResponseBody
    @PostMapping("/imgDel")
    public int imgDel(String newFileName, String photoNum, String boardNum) {
        return boardService.imgDel(newFileName, photoNum, boardNum);
    }
}

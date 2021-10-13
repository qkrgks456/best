package com.gudi.best.logic.loveBoard.service;

import com.gudi.best.dto.BoardDTO;
import com.gudi.best.logic.loveBoard.mapper.BoardMapper;
import com.gudi.best.util.PageNation;
import com.gudi.best.util.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class BoardService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    BoardMapper mapper;
    @Autowired
    S3Uploader uploader;

    @Transactional
    public HashMap<String, Object> list(int page) {
        int total = mapper.boardTotal();
        int start = 0;
        HashMap<String, Object> map = PageNation.pagination(page, 15, total);
        if (page == 1) {
            start = 0;
        } else {
            start = (page - 1) * 15;
        }
        map.put("list", mapper.list(start));
        return map;
    }

    @Transactional
    public int boardWrite(String title, String content, MultipartFile[] files, String id) {
        // 글쓰기
        BoardDTO dto = new BoardDTO();
        dto.setId(id);
        dto.setContent(content);
        dto.setTitle(title);
        mapper.boardWrite(dto);
        int boardNum = dto.getBoardNum();
        if (files != null) {
            // 파일 업로드 하기
            ArrayList<HashMap<String, Object>> imgMapList = new ArrayList<HashMap<String, Object>>();
            for (MultipartFile file : files) {
                try {
                    HashMap<String, Object> map = uploader.upload(file);
                    map.put("id", id);
                    map.put("divisionNum", boardNum);
                    imgMapList.add(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 포토테이블에 값 넣기
            for (HashMap<String, Object> map : imgMapList) {
                mapper.photoInsert(map);
            }
        }
        return boardNum;
    }
}

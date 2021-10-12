package com.gudi.best.logic.myInfo.service;

import com.gudi.best.dto.ProFileDTO;
import com.gudi.best.logic.myInfo.mapper.MyInfoMapper;
import com.gudi.best.util.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Service
public class MyInfoService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MyInfoMapper mapper;
    @Autowired
    S3Uploader s3Uploader;
    HashMap<String, Object> map = null;

    // 프로필 데이터 넣기
    public void proFileInput(String[] hobby, String intro, MultipartFile proFileImg, String id) {
        String hobbyText = "";
        // 체크박스 값 받아서 쉼표 단위로 문자열 하나로 합치기
        if (hobby != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String text : hobby) {
                stringBuilder.append(text);
                stringBuilder.append(",");
            }
            hobbyText = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        }
        if (!proFileImg.isEmpty()) {
            // 업로드 하기
            try {
                map = s3Uploader.upload(proFileImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("hobby", hobbyText);
            map.put("intro", intro);
            map.put("id", id);
            // 포토 테이블
            mapper.photoInput(map);
        } else {
            map = new HashMap<String, Object>();
            map.put("hobby", hobbyText);
            map.put("intro", intro);
            map.put("id", id);
            map.put("path", "/img/noImg.png");
        }
        // 프로필 테이블
        mapper.proFileInput(map);

    }

    // 프로필 상세
    public ProFileDTO proFileDetail(String id) {
        return mapper.proFileDetail(id);
    }

    // 프로필 등록여부 체크
    public String proFileCheck(String id) {
        return mapper.proFileCheck(id);
    }
}

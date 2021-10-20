package com.gudi.best.logic.loveBoard.service;

import com.gudi.best.logic.loveBoard.mapper.GoodMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Log4j2
public class GoodService {
    @Autowired
    GoodMapper mapper;

    public boolean goodCheck(String divisionNum, String id) {
        boolean result = false;
        if (mapper.goodCheck(divisionNum, id) != null) {
            result = true;
        }
        return result;
    }

    public int goodCount(String divisionNum) {
        return mapper.goodCount(divisionNum);
    }

    public HashMap<String, Object> goodData(int boardNum, String id, String division) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        boolean result = false;
        if (goodCheck(String.valueOf(boardNum), id)) {
            mapper.goodDelete(boardNum, id, division);
        } else {
            mapper.goodInsert(boardNum, id, division);
            result = true;
        }
        map.put("goodCount", goodCount(String.valueOf(boardNum)));
        map.put("check", result);
        return map;
    }
}

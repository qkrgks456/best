package com.gudi.best.logic.loveBoard.mapper;

import com.gudi.best.dto.BoardDTO;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface BoardMapper {
    @Select("SELECT COUNT(boardNum) FROM board")
    int boardTotal();

    @Select("SELECT * FROM board ORDER BY boardNum DESC LIMIT ${start},15")
    ArrayList<BoardDTO> list(int start);

    @Insert(BoardSQL.BOARD_PHOTO_INSERT)
    void photoInsert(HashMap<String, Object> map);

    @Insert("INSERT INTO board(id,title,content) VALUES(#{id},#{title},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "boardNum")
    void boardWrite(BoardDTO dto);
}

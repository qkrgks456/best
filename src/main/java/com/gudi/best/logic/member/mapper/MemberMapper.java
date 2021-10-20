package com.gudi.best.logic.member.mapper;

import com.gudi.best.dto.BoardDTO;
import com.gudi.best.logic.loveBoard.mapper.BoardSQL;
import com.gudi.best.logic.myInfo.mapper.MyInfoSQL;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface MemberMapper {

    @Select("SELECT id FROM member WHERE id = #{param1}")
    String idCheck(String id);

    @Insert("INSERT INTO member(id,pw,email,admin,delCheck,gender) VALUES(#{id},#{pw},#{email},'N','N',#{gender})")
    void join(HashMap<String, String> params);

    @Select("SELECT pw FROM member WHERE id = #{param1} AND delCheck='N'")
    String login(String id);

    @Select("SELECT admin FROM member WHERE id = #{param1}")
    String adminCheck(String id);

    @SelectProvider(type = BoardSQL.class, method = "proFileBoard")
    ArrayList<BoardDTO> proFileBoard(int start, String id);
}

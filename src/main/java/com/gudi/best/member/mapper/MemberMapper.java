package com.gudi.best.member.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;

@Mapper
public interface MemberMapper {

    @Select("SELECT id FROM member WHERE id = #{param1}")
    String idCheck(String id);

    @Insert("INSERT INTO member(id,pw,email,admin,delCheck) VALUES(#{id},#{pw},#{email},'N','N')")
    void join(HashMap<String, String> params);

    @Select("SELECT pw FROM member WHERE id = #{param1}")
    String login(String id);

    @Select("SELECT admin FROM member WHERE id = #{param1}")
    String adminCheck(String id);
}

package com.gudi.best.logic.admin.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gudi.best.dto.memberDTO;

@Mapper
public interface AdminMapper {

	@Select("select id,email,gender,date,admin,coupleId from member where delCheck='N' LIMIT #{param1},10")
	ArrayList<memberDTO> memberList(int page);

	@Select("select count(id) from member")
	int total();

	@Select("select admin from member where id = #{param1}")
	String admincheck(String id);

	@Update("update member set admin = #{param1} where id = #{param2}")
	void change(String chk, String id);

}

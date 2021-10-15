package com.gudi.best.logic.couple.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gudi.best.dto.CalenderDTO;

@Mapper
public interface CoupleMapper {

	@Insert("INSERT INTO calender (id,start,end,title,content) VALUES(#{id},#{start},#{end},#{title},#{content})")
	void calenderEnter(CalenderDTO Cdto);

	@Select("select Cnum,start,end,title from calender where id=#{param1} AND del='0' ")
	ArrayList<CalenderDTO> readCalender(String id);
	
	@Select("select * from calender where id=#{param1} AND cnum = #{param2} AND del='0' ")
	HashMap<String, String> detail(String id, int cnum);

	@Update("UPDATE calender SET start = #{start}, end = #{end}, title=#{title}, content = #{content} WHERE id=#{id} AND Cnum=#{cNum}")
	void calenderUpdate(CalenderDTO cdto);
	
	@Update("UPDATE calender SET del = '1' WHERE id=#{param1} AND Cnum=#{param2}")
	void calenderDel(String id, int cnum);
}

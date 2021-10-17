package com.gudi.best.logic.matching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.gudi.best.logic.matching.dto.ChatMessageDTO;

@Mapper
public interface ChatMapper {
	
	@Insert("INSERT INTO chatRoom(id, person, roomName, dates, lastChatDates) VALUES(#{param1}, #{param2}, #{param3}, SYSDATE(), SYSDATE())")
	void createRoom(String loginId, String person,String roomName);

	@Select("SELECT *\r\n"
			+ "FROM\r\n"
			+ "(SELECT roomnum, roomname, id, person, dates, TIME(lastchatdates) AS lcd  FROM chatRoom WHERE id = #{param1}  OR person = #{param1} ) AS sub\r\n"
			+ "ORDER BY lcd DESC\r\n")
	List<ChatMessageDTO> findAllRooms(String loginId);

	@Select("SELECT * FROM chatRoom WHERE roomNum = #{param1}")
	ChatMessageDTO findRoomByNum(String roomNum);
	
	
	
}

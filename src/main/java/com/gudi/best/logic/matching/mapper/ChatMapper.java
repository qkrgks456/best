package com.gudi.best.logic.matching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gudi.best.logic.matching.dto.ChatDTO;
import com.gudi.best.logic.matching.dto.ChatMessageDTO;
import com.gudi.best.logic.matching.dto.ChatRoomDTO;

@Mapper
public interface ChatMapper {
	
	@Insert("INSERT INTO chatRoom(id, person, roomName, dates, lastChatDates) VALUES(#{param1}, #{param2}, #{param3}, SYSDATE(), SYSDATE())")
	void createRoom(String loginId, String person,String roomName);

	@Select("SELECT *\r\n"
			+ "FROM\r\n"
			+ "(SELECT roomnum, roomname, id, person, dates, TIME(lastchatdates) AS lcd  FROM chatRoom WHERE id = #{param1}  OR person = #{param1} ) AS sub\r\n"
			+ "ORDER BY lcd DESC\r\n")
	List<ChatRoomDTO> findAllRooms(String loginId);

	@Select("SELECT * FROM chatRoom WHERE roomNum = #{param1}")
	ChatRoomDTO findRoomByNum(String roomNum);

	@Insert("INSERT INTO chat(roomNum, id, message, dates) VALUES(#{param1}, #{param2}, #{param3}, SYSDATE())")
	void chatInsert(Integer roomNum, String id, String message);

	@Update("UPDATE chatRoom SET lastchatdates = SYSDATE() WHERE roomNum = #{param1}")
	void lastChatDatesUpdate(Integer roomNum);

	@Select("SELECT * FROM\r\n"
			+ "(SELECT roomnum, id, message, TIME(dates) AS dates FROM chat WHERE roomNum = 1) AS sub\r\n"
			+ "ORDER BY dates ASC")
	List<ChatDTO> chatFind(String roomNum);
	
}

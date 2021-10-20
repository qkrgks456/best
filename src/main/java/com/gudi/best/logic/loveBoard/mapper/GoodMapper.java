package com.gudi.best.logic.loveBoard.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GoodMapper {

    @Select("SELECT COUNT(goodNum) FROM good WHERE divisionNum = #{param1}")
    int goodCount(String divisionNum);

    @Select("SELECT id FROM good WHERE divisionNum=#{param1} AND id=#{param2}")
    String goodCheck(String divisionNum, String id);

    @Delete("DELETE FROM good WHERE divisionNum=#{param1} AND id=#{param2} AND division=#{param3}")
    void goodDelete(int boardNum, String id, String division);

    @Insert("INSERT INTO good(divisionNum,id,division) VALUES(#{param1},#{param2},#{param3})")
    void goodInsert(int boardNum, String id, String division);
}

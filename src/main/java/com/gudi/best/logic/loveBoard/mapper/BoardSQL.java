package com.gudi.best.logic.loveBoard.mapper;

public class BoardSQL {
    public static final String BOARD_PHOTO_INSERT =
            "INSERT INTO photo(id,division,oriFileName,newFileName,path,divisionNum)" +
                    "VALUES(#{id},'loveBoard',#{oriFileName},#{newFileName},#{path},#{divisionNum})";
    public static final String CM_PAGE_CHECK = "SELECT e.id FROM " +
            "(SELECT * FROM cm WHERE divisionNum = #{param1} AND delCheck='N' ORDER BY cmNum DESC LIMIT #{param3},8) e " +
            "WHERE cmNum = #{param2}";
}

package com.gudi.best.logic.loveBoard.mapper;

import org.apache.ibatis.jdbc.SQL;

public class BoardSQL {
    public static final String BOARD_PHOTO_INSERT =
            "INSERT INTO photo(id,division,oriFileName,newFileName,path,divisionNum)" +
                    "VALUES(#{id},'loveBoard',#{oriFileName},#{newFileName},#{path},#{divisionNum})";
    public static final String CM_PAGE_CHECK = "SELECT e.id FROM " +
            "(SELECT * FROM cm WHERE divisionNum = #{param1} AND delCheck='N' ORDER BY cmNum DESC LIMIT #{param3},8) e " +
            "WHERE cmNum = #{param2}";

    public String boardPick(int start, String division) {
        return new SQL() {{
            if (division.equals("all")) {
                SELECT("*");
                FROM("board");
                ORDER_BY("boardNum DESC");
                LIMIT("#{param1},15");
            } else {
                SELECT("*");
                FROM("board");
                ORDER_BY("boardHit DESC");
                LIMIT("#{param1},15");
            }
        }}.toString();
    }
}

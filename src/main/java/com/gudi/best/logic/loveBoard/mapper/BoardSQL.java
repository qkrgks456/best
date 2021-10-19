package com.gudi.best.logic.loveBoard.mapper;

import org.apache.ibatis.jdbc.SQL;

public class BoardSQL {
    public static final String BOARD_PHOTO_INSERT =
            "INSERT INTO photo(id,division,oriFileName,newFileName,path,divisionNum)" +
                    "VALUES(#{id},'loveBoard',#{oriFileName},#{newFileName},#{path},#{divisionNum})";
    public static final String CM_PAGE_CHECK = "SELECT e.id FROM " +
            "(SELECT * FROM cm WHERE divisionNum = #{param1} AND delCheck='N' ORDER BY cmNum DESC LIMIT #{param3},8) e " +
            "WHERE cmNum = #{param2}";
    String goodCount = new SQL() {{
        SELECT("COUNT(goodNum)");
        FROM("good");
        WHERE("boardNum=divisionNum");
    }}.toString();

    public String boardPick(int start, String division) {
        return new SQL() {{
            if (division.equals("all")) {
                SELECT("boardNum", "id", "content", "title", "date", "boardHit");
                SELECT("(" + goodCount + ")goodCount");
                FROM("board");
                ORDER_BY("boardNum DESC");
            } else {
                SELECT("boardNum", "id", "content", "title", "date", "boardHit");
                SELECT("(" + goodCount + ")goodCount");
                FROM("board");
                ORDER_BY("boardHit DESC");
            }
            LIMIT("#{param1},15");
        }}.toString();
    }

    public String boardSearch(int start, String searchText, String option) {
        return new SQL() {{
            SELECT("boardNum", "id", "content", "title", "date", "boardHit");
            SELECT("(" + goodCount + ")goodCount");
            FROM("board");
            if (option.equals("제목+내용")) {
                WHERE("title LIKE CONCAT('%',#{param2},'%')");
                OR();
                WHERE("content LIKE CONCAT('%',#{param2},'%')");
            } else if (option.equals("제목")) {
                WHERE("title LIKE CONCAT('%',#{param2},'%')");
            } else if (option.equals("내용")) {
                WHERE("content LIKE CONCAT('%',#{param2},'%')");
            } else {
                WHERE("id LIKE CONCAT('%',#{param2},'%')");
            }
            ORDER_BY("boardNum DESC");
            LIMIT("#{param1},15");
        }}.toString();
    }

    public String boardSearchCount(String searchText, String option) {
        return new SQL() {{
            SELECT("COUNT(boardNum)");
            FROM("board");
            if (option.equals("제목+내용")) {
                WHERE("title LIKE CONCAT('%',#{param1},'%')");
                OR();
                WHERE("content LIKE CONCAT('%',#{param1},'%')");
            } else if (option.equals("제목")) {
                WHERE("title LIKE CONCAT('%',#{param1},'%')");
            } else if (option.equals("내용")) {
                WHERE("content LIKE CONCAT('%',#{param1},'%')");
            } else {
                WHERE("id LIKE CONCAT('%',#{param1},'%')");
            }
        }}.toString();
    }
}

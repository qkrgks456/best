package com.gudi.best.logic.loveBoard.mapper;

public class BoardSQL {
    public static final String BOARD_PHOTO_INSERT =
            "INSERT INTO photo(id,division,oriFileName,newFileName,path,divisionNum)" +
                    "VALUES(#{id},'loveBoard',#{oriFileName},#{newFileName},#{path},#{divisionNum})";
}

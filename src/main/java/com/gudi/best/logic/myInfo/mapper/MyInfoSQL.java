package com.gudi.best.logic.myInfo.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;

public class MyInfoSQL {
    public static final String PROFILE_PHOTO_INSERT =
            "INSERT INTO photo(id,division,oriFileName,newFileName,path) "
                    + "VALUES(#{id},'proFile',#{oriFileName},#{newFileName},#{path})";

    public String nullCheck(HashMap<String, Object> map) {

        return new SQL() {{
            INSERT_INTO("proFile");
            INTO_COLUMNS("id", "imgPath");
            INTO_VALUES("'" + map.get("id") + "'", "'" + map.get("path") + "'");
            if (!map.get("hobby").equals("")) {
                INTO_COLUMNS("hobby");
                INTO_VALUES("'" + map.get("hobby") + "'");
            }
            if (!map.get("intro").equals("")) {
                INTO_COLUMNS("intro");
                INTO_VALUES("'" + map.get("intro") + "'");
            }
        }}.toString();
    }
}

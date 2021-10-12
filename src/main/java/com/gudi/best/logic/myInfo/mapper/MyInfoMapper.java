package com.gudi.best.logic.myInfo.mapper;

import com.gudi.best.dto.ProFileDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;

@Mapper
public interface MyInfoMapper {
    @InsertProvider(type = MyInfoSQL.class, method = "nullCheck")
    void proFileInput(HashMap<String, Object> map);

    @Insert(MyInfoSQL.PROFILE_PHOTO_INSERT)
    void photoInput(HashMap<String, Object> map);

    @Select("SELECT * FROM proFile WHERE id = #{id}")
    ProFileDTO proFileDetail(String id);

    @Select("SELECT id FROM proFile WHERE id = #{id}")
    String proFileCheck(String id);
}

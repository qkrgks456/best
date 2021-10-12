package com.gudi.best.logic.myInfo.mapper;

import com.gudi.best.dto.ProFileDTO;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;

@Mapper
public interface MyInfoMapper {
    @InsertProvider(type = MyInfoSQL.class, method = "insertNullCheck")
    void proFileInput(HashMap<String, Object> map);

    @Insert(MyInfoSQL.PROFILE_PHOTO_INSERT)
    void photoInput(HashMap<String, Object> map);

    @Select("SELECT * FROM proFile WHERE id = #{id}")
    ProFileDTO proFileDetail(String id);

    @Select("SELECT id FROM proFile WHERE id = #{id}")
    String proFileCheck(String id);

    @Select("SELECT newFileName FROM photo WHERE id = #{id} AND division = 'proFile'")
    String photoCheck(String id);

    @Delete("DELETE FROM photo WHERE id = #{id}")
    void photoDelete(String id);

    @UpdateProvider(type = MyInfoSQL.class, method = "updateNullCheck")
    void proFileUpdate(HashMap<String, Object> map);
}

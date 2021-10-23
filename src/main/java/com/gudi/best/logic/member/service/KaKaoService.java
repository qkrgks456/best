package com.gudi.best.logic.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gudi.best.util.NewApiUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Log4j2
public class KaKaoService {


    public String getAccess_Token(String code) throws Exception {
        // 헤더
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // 파라미터
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", "510dfee7db026dbcc8df7b0a51993201");
        params.put("redirect_uri", "http://localhost:8100/kakao/callback");
        params.put("code", code);
        // url
        String url = "https://kauth.kakao.com/oauth/token";
        String result = NewApiUtil.sendSeverMsg(url, header, params, "POST");
        // 결과값에서 엑세스 토큰 가져오기
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(result, HashMap.class);
        return (String) map.get("access_token");
    }

    public HashMap<String, Object> proFileData(String access_token) throws Exception {
        HashMap<String, Object> proFileData = new HashMap<String, Object>();
        // 헤더
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        header.put("Authorization", "Bearer " + access_token);
        // 파라미터
        HashMap<String, String> params = new HashMap<String, String>();
        String url = "https://kapi.kakao.com/v2/user/me";
        // 결과값에서 ID,프로필이미지,닉네임 3개 가져오기
        String result = NewApiUtil.sendSeverMsg(url, header, params, "POST");
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(result, HashMap.class);
        String id = map.get("id").toString();
        result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map.get("kakao_account"));
        map = mapper.readValue(result, HashMap.class);
        result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map.get("profile"));
        map = mapper.readValue(result, HashMap.class);
        String proFile_Image = map.get("profile_image_url").toString();
        String nickName = map.get("nickname").toString();
        proFileData.put("id", id);
        proFileData.put("proFile_Image", proFile_Image);
        proFileData.put("nickName", nickName);
        return proFileData;
    }
}

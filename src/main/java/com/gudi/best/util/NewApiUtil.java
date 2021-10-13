package com.gudi.best.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewApiUtil {

    public static String sendSeverMsg(String url,
                                      HashMap<String, String> header,
                                      HashMap<String, String> param,
                                      String method) {

        // 헤더에 속성값 넣어주기
        HttpHeaders headers = new HttpHeaders();
        if (header != null && header.size() > 0) {// 헤더 값이 있는가?
            for (String key : header.keySet()) {// 있다면 있는 만큼 헤더값을 넣어 준다.
                headers.add(key, header.get(key));
            }
        }

        // 저 params 에다가 파라미터 삽입하기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (param != null && param.size() > 0) { // 파라미터 값이 있는가?
            for (String key : param.keySet()) { // 있다면 있는 만큼 파라미터값을 넣어준다
                params.add(key, param.get(key));
            }
        }

        // 헤더랑 파라미터 담은녀석
        HttpEntity<MultiValueMap<String, String>> httpEntity = null;
        if (headers.size() > 0 && header != null) {
            httpEntity = new HttpEntity<>(params, headers);
        } else {
            httpEntity = new HttpEntity<>(params);
        }

        // 어떤 메서드인지 확인
        HttpMethod httpMethod = null;
        if (method.toUpperCase().equals("GET") && param != null) {
            httpMethod = HttpMethod.GET;
        }
        if (method.toUpperCase().equals("POST") && param != null) {
            httpMethod = HttpMethod.POST;
        }

        // response 받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url, // url
                httpMethod, // method 종류
                httpEntity, // 파라미터와 헤더 담은값
                String.class
        );
        System.out.println(response);
        return response.getBody();
    }
}
package com.gudi.best.util;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class Scheduler {
    // 스케줄러 사용할 클래스 사용할 메서드는 여기에
//	@Scheduled(cron = "0 0 0 1 * *")
	public String dataInsert(int i) {
		  HashMap<String, String> header = new HashMap<String, String>();
          HashMap<String, String> params = new HashMap<String, String>();
          params.put("ServiceKey", "J0GWcsmSKRep08rNleYyFbyLzNo8BcbkWmzsOxqFeJIyHVD3456iyVy+ULCnhVw5y9WsuzCa4ZjugOCCfA0JOA==");
          params.put("pageNo", "1");
          params.put("numOfRows", "500");
          params.put("MobileOS", "ETC");
          params.put("MobileApp", "test");          
          params.put("areaCode", String.valueOf(i));
          params.put("_type", "json");
          String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList";
          String response  = NewApiUtil.sendSeverMsg(url, header, params, "GET");
          return response;
	}
}

package com.wk.common.utils;

import com.wk.common.dto.VisitUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RestTemplateUtil {

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate;

    public <T> T get(String url, ParameterizedTypeReference<Result<T>> responseType, Map<String, Object> paramMap) {
        try {
            ResponseEntity<Result<T>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType, paramMap);
            if (responseEntity != null && responseEntity.getStatusCodeValue() == 200 && responseEntity.getBody() != null && responseEntity.getBody().isSuccess()) {
                return responseEntity.getBody().getData();
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("request get url={} fail", url);
        }
        return null;
    }


    /**
     *例http://192.168.0.231:8581/user/v1/check
     * @param userPath
     * @return
     */
    public Result<VisitUser> check(String userPath){
        try {
            ResponseEntity<Result<VisitUser>> responseEntity = restTemplate.exchange(userPath, HttpMethod.GET, null, new ParameterizedTypeReference<Result<VisitUser>>() {
            }, new HashMap<>());
            if(responseEntity != null && responseEntity.getStatusCodeValue() == 200 && responseEntity.getBody() != null) {
                return responseEntity.getBody();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

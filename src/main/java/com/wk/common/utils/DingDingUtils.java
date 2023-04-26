package com.wk.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DingDingUtils {
	public static String TOKEN_KEY = "TOKEN_KEY";
//	public static String AppKey = "dingxl8uqyr1hbmz56hp";
//	public static String AppSecret = "6CtaQ6MQD3EHJpb46BiRDhQKcuQD_IOl0hcB1IIVvh78w7DEmZaNmndLCnmqM0BQ";
	
	public static String appKey;
	@Value("${dingding.appKey}")
	public void setAppKey(String appKey){
		DingDingUtils.appKey = appKey;
	}
	
	public static String appSecret;
	@Value("${dingding.appSecret}")
	public void setAppSecret(String appSecret){
		DingDingUtils.appSecret = appSecret;
	}
	
	private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
    	DingDingUtils.redisTemplate = redisTemplate;
    }
	
	public static void main(String[] args) {
//		getToken();
		String deptList = getDepts();
//		getDeptUsers(492452169,0,1);
	}
	

	public static String getToken() {
		String token = redisTemplate.opsForValue().get(TOKEN_KEY)==null?null:redisTemplate.opsForValue().get(TOKEN_KEY).toString();
		if(StringUtils.isEmpty(token)) {
			try {
	            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
				OapiGettokenRequest req = new OapiGettokenRequest();
				req.setAppkey(appKey);
				req.setAppsecret(appSecret);
				req.setHttpMethod("GET");
				OapiGettokenResponse rsp = client.execute(req);
//				System.out.println("##get token:"+rsp.getBody());
				JSONObject rspJson = JSONObject.parseObject(rsp.getBody());
				token = rspJson.getString("access_token");
				redisTemplate.opsForValue().set(TOKEN_KEY, token, 7200, TimeUnit.SECONDS);
	        } catch (ApiException e) {
	            e.printStackTrace();
	        }
		}
		return token;
		
	}

    public static String getDepts() {
    	String token = getToken();
        try {
//        	DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
//			OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
//			req.setDeptId(116717218L);
//			OapiV2DepartmentListsubResponse rsp = client.execute(req, token);
        	DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
			OapiDepartmentListRequest req = new OapiDepartmentListRequest();
			req.setFetchChild(true);
//			req.setId("1");
			req.setHttpMethod("GET");
			OapiDepartmentListResponse rsp = client.execute(req,token);
			System.out.println(rsp.getBody());
			return rsp.getBody();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getDeptUsers(Integer deptId,Integer cursor,Integer size) {
    	String token = getToken();
        try {
        	DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
        	OapiV2UserListRequest req = new OapiV2UserListRequest();
        	req.setDeptId(deptId.longValue());
        	req.setCursor(cursor.longValue());
        	req.setSize(size.longValue());
        	req.setOrderField("modify_desc");
        	req.setContainAccessLimit(false);
        	req.setLanguage("zh_CN");
        	OapiV2UserListResponse rsp = client.execute(req, token);
        	System.out.println(rsp.getBody());
			return rsp.getBody();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
}

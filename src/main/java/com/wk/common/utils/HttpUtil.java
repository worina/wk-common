package com.wk.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Http请求工具类
 */
@Slf4j
public class HttpUtil {


    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * 
     * @param url 请求的URL地址
     * @param params 请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> params)throws IOException {
        String result = null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        HttpEntity httpEntity =new UrlEncodedFormEntity(paramsList, "utf-8");
        post.setEntity(httpEntity);
        CloseableHttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        if(entity != null) result =  EntityUtils.toString(entity, "utf-8");
        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(client);
        return result;
    }

    public static String get(String url, Map<String, String> params, int timeout, Map<String, String> headers) {
        try {

            if (MapUtils.isNotEmpty(params)) {

                StringJoiner joiner = new StringJoiner("&");

                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (StringUtils.isBlank(entry.getValue()) || "null".equals(entry.getValue())) {
                        continue;
                    }

                    joiner.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
                }

                if (!url.trim().endsWith("?")) {
                    url += "?";
                }

                url += joiner.toString();
            }

            if (headers != null && headers.size() > 0) {

                Header[] header = new Header[headers.size()];

                int i = 0;
                for (Map.Entry<String, String> stringListEntry : headers.entrySet()) {
                    header[i] = new BasicHeader(stringListEntry.getKey(), stringListEntry.getValue());
                    i++;
                }

                Response response = Request.Get(url).setHeaders(header)
                        .connectTimeout(timeout).execute();

                return response.returnContent().asString(Consts.UTF_8);
            } else {
                Response response = Request.Get(url)
                        .connectTimeout(timeout).execute();
                return response.returnContent().asString(Consts.UTF_8);
            }

        } catch (IOException e) {
            log.error("请求异常:{}", e);
        }
        return "ERROR";
    }

    public static String get(String url, Map<String, String> params, int timeout) {

        return get(url, params, timeout, null);
    }


}

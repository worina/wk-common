package com.wk.common.conf;

import com.wk.common.CommonConstants;
import com.wk.common.utils.Md5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {


    public String serviceKey;

    public RestTemplateInterceptor(String serviceKey) {
        this.serviceKey = serviceKey;
    }


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpRequest = attributes.getRequest();
        HttpHeaders headers = request.getHeaders();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            String headerName;
            String headerValue;
            while (headerNames.hasMoreElements()) {
                headerName = headerNames.nextElement();
                if("authorization".equalsIgnoreCase(headerName)){
                    headerValue = httpRequest.getHeader(headerName);
                    headers.add(CommonConstants.TOKEN_HEADER, headerValue);
                }
            }
        }
        if(StringUtils.isBlank(headers.getFirst(CommonConstants.TOKEN_HEADER))) {
            String token = httpRequest.getParameter(CommonConstants.ACCESS_TOKEN);
            if(StringUtils.isNotBlank(token)) {
                headers.add(CommonConstants.TOKEN_HEADER, token);
            }
        }
        headers.add("service_token", Md5.getMD5(serviceKey));
        return execution.execute(request, body);
    }
}

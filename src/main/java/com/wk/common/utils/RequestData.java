package com.wk.common.utils;

import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求的数据对象
 *
 * @author s.li
 * @date 2019/06/01
 */
public class RequestData implements Cloneable, Serializable {
    private static final long serialVersionUID = -5856132037768296044L;
    public static ThreadLocal<RequestData> requestDataThreadLocal = new ThreadLocal<>();

    private Map<String, String> headerMap;
    private Map<String, String[]> parameterMap;
    private String requestURI;
    private StringBuffer requestRUL;
    private Cookie[] cookies;
    private String remoteAddr;

    public Map<String, String> getHeaderMap() {
        if (headerMap == null) {
            headerMap = new HashMap<>();
        }
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        if (this.headerMap == null) {
            this.headerMap = new HashMap<>();
        }
        this.headerMap = headerMap;
    }

    public Map<String, String[]> getParameterMap() {
        if (this.parameterMap == null) {
            this.parameterMap = new HashMap<>();
        }
        return parameterMap;
    }

    public void setParameterMap(Map<String, String[]> parameterMap) {
        if (this.parameterMap == null) {
            this.parameterMap = new HashMap<>();
        }
        this.parameterMap = parameterMap;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public StringBuffer getRequestRUL() {
        return requestRUL;
    }

    public void setRequestRUL(StringBuffer requestRUL) {
        this.requestRUL = requestRUL;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    @Override
    public RequestData clone() {
        try {
            return (RequestData) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

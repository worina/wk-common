package com.wk.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Web工具类型
 *
 * @author s.li
 */
public class WebUtils extends BaseUtil {
    private static Logger logger = LoggerFactory.getLogger(WebUtils.class);
    /**
     * 系统主域名
     */
    public static String MYDOMAIN = "";

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    public static String getRootDir(HttpServletRequest request) {
        String rootDir = request.getSession().getServletContext().getRealPath("/");
        return rootDir;
    }

    public static String getContextPath(HttpServletRequest request) {
        String path = request.getContextPath();
        if (!path.equals("/")) {
            return path;
        }
        return "";
    }

    /**
     * 设置Cookie
     *
     * @param response HttpServletResponse
     * @param key      Cookie键
     * @param value    Cookie的值
     * @param minuts   存放时间（单位/分）
     */
    public static void setCookieMinute(HttpServletResponse response, String key, String value, int minuts) {
        setCookieMinuteDomain(response, key, value, minuts, MYDOMAIN);
    }

    /**
     * 设置Cookie
     *
     * @param response HttpServletResponse
     * @param key      Cookie键
     * @param value    Cookie的值
     * @param minuts   存放时间（单位/分）
     * @param domain   Cookie存放域
     */
    public static void setCookieMinuteDomain(HttpServletResponse response, String key, String value, int minuts,
                                             String domain) {
        if ((key != null) && (value != null)) {
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(minuts * 60);
            cookie.setPath("/");
            if (!StringUtils.isEmpty(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        }
    }

    /**
     * 设置Session类型的Cookie
     *
     * @param response HttpServletResponse
     * @param key      Cookie键
     * @param value    Cookie值
     */
    public static void setCookieSessionTime(HttpServletResponse response, String key, String value) {
        setCookieSessionTime(response, key, value, MYDOMAIN);
    }

    /**
     * 设置Session类型的Cookie
     *
     * @param response HttpServletResponse
     * @param key      Cookie键
     * @param value    Cookie值
     * @param domain   Cookie存放域
     */
    public static void setCookieSessionTime(HttpServletResponse response, String key, String value, String domain) {
        if ((key != null) && (value != null)) {
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            if (!StringUtils.isEmpty(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        }
    }

    /**
     * 设置普通Cookie
     *
     * @param response HttpServletResponse
     * @param key      Cookie键
     * @param value    Cookie值
     * @param days     存放天数据
     */
    public static void setCookie(HttpServletResponse response, String key, String value, int days) {
        setCookie(response, key, value, days, MYDOMAIN);
    }

    /**
     * 设置普通Cookie
     *
     * @param response HttpServletResponse
     * @param key      Cookie键
     * @param value    Cookie值
     * @param days     存放天数据
     * @param domain   Cookie存放域
     */
    public static void setCookie(HttpServletResponse response, String key, String value, int days, String domain) {
        if ((key != null) && (value != null)) {
            Cookie cookie = new Cookie(key, value);

            cookie.setMaxAge(days * 24 * 60 * 60);

            cookie.setPath("/");
            if (!StringUtils.isEmpty(domain)) {
                cookie.setDomain(domain);
            }

            response.addCookie(cookie);
        }
    }

    /**
     * 获取Cookie
     *
     * @param request HttpServletRequest
     * @param key     Cookie键
     * @return 返回中的值
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        String resValue = "";
        if ((cookies != null) && (cookies.length > 0)) {
            for (int i = 0; i < cookies.length; i++) {
                if ((!key.equalsIgnoreCase(cookies[i].getName())) || (StringUtils.isEmpty(cookies[i].getValue())))
                    continue;
                resValue = cookies[i].getValue();
            }

        }
        try {
            resValue = URLDecoder.decode(resValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        }
        return resValue;
    }

    /**
     * 获取Cookie
     *
     * @param request HttpServletRequest
     * @param key     Cookie键
     * @return 返回中的值
     */
    public static String getCookie(RequestData request, String key) {
        Cookie[] cookies = request.getCookies();
        String resValue = "";
        if ((cookies != null) && (cookies.length > 0)) {
            for (int i = 0; i < cookies.length; i++) {
                if ((!key.equalsIgnoreCase(cookies[i].getName())) || (StringUtils.isEmpty(cookies[i].getValue())))
                    continue;
                resValue = cookies[i].getValue();
            }

        }
        return resValue;
    }

    /**
     * 删除Cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param name     要删除的Cookie名
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        deleteCookieDomain(request, response, name, MYDOMAIN);
    }

    /**
     * 删除指定域下的Cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param name     要删除的Cookie名
     * @param domain   Cookie存放域
     */
    public static void deleteCookieDomain(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            for (int i = 0; i < cookies.length; i++) {
                if (!name.equalsIgnoreCase(cookies[i].getName()))
                    continue;
                Cookie ck = new Cookie(cookies[i].getName(), null);
                ck.setPath("/");
                if (!StringUtils.isEmpty(domain)) {
                    ck.setDomain(domain);
                }
                ck.setMaxAge(0);
                response.addCookie(ck);
                return;
            }
        }
    }

    /**
     * 创建CookieMap
     *
     * @param response   HttpServletResponse
     * @param nameValues 存放Cookie键=值的 Hashtable
     * @param days       存放天数据
     */
    public static void createCookieFromMap(HttpServletResponse response, Hashtable<String, String> nameValues, int days) {
        createCookieFromMapDomain(response, nameValues, days, MYDOMAIN);
    }

    /**
     * 创建CookieMap
     *
     * @param response   HttpServletResponse
     * @param nameValues 存放Cookie键=值的 Hashtable
     * @param days       存放天数据
     * @param domain     Cookie存放域
     */
    public static void createCookieFromMapDomain(HttpServletResponse response, Hashtable<String, String> nameValues, int days, String domain) {
        Set<String> set = nameValues.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            String value = (String) nameValues.get(name);
            Cookie cookie = new Cookie(name, value);
            if (!StringUtils.isEmpty(domain)) {
                cookie.setDomain(domain);
            }
            cookie.setSecure(false);
            cookie.setMaxAge(days * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    /**
     * 获取CookieMap
     *
     * @param request HttpServletRequest
     * @return 返回存放Cookie的Hashtable
     */
    public static Hashtable<String, String> getCookiesForMap(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Hashtable cookieHt = new Hashtable();
        if (cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                cookieHt.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieHt;
    }

    /**
     * 修改Cookie
     *
     * @param request HttpServletRequest
     * @param name    Cookie键
     * @param value   Cookie值
     */
    public static void updateCookie(HttpServletRequest request, String name, String value) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0)
            for (int i = 0; i < cookies.length; i++)
                if (name.equalsIgnoreCase(cookies[i].getName())) {
                    cookies[i].setValue(value);
                    return;
                }
    }

    /**
     * 删除所有Cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void deleteAllCookie(HttpServletRequest request, HttpServletResponse response) {
        deleteAllCookieDomain(request, response, MYDOMAIN);
    }

    /**
     * 删除所有Cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param domain   Cookie指定域
     */
    public static void deleteAllCookieDomain(HttpServletRequest request, HttpServletResponse response, String domain) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];

                Cookie ck = new Cookie(cookie.getName(), null);
                ck.setPath("/");
                if (!StringUtils.isEmpty(domain)) {
                    ck.setDomain(domain);
                }
                ck.setMaxAge(0);
                response.addCookie(ck);
            }
        }
    }

    /**
     * 获取请求来源IP地址
     *
     * @param request HttpServletRequest
     * @return 返回 IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = getRemoteAddr(request.getRemoteAddr());
        }

        if ((ipAddress != null) && (ipAddress.length() > 15)) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    //获取IP
    public static String getIpAddrOne(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取请求来源IP地址
     *
     * @param request HttpServletRequest
     * @return 返回 IP地址
     */
    public static String getIpAddr(RequestData request) {
        String ipAddress = request.getHeaderMap().get("x-forwarded-for");
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getHeaderMap().get("Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getHeaderMap().get("WL-Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = getRemoteAddr(request.getRemoteAddr());
        }

        if ((ipAddress != null) && (ipAddress.length() > 15)) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    private static String getRemoteAddr(String remoteAddr) {
        if (remoteAddr.equals("127.0.0.1")) {
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            if(inet != null) {
                remoteAddr = inet.getHostAddress();
            }
        }
        return remoteAddr;
    }

    /**
     * 判断是不是Ajax请求
     *
     * @param request HttpServletRequest
     * @return true是，false不是
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String her = request.getHeader("X-Requested-With");
        String accept = request.getHeader("Accept");
        if (StringUtils.isNotEmpty(her) && accept.contains("application/json")) {
            return true;
        }
        return false;
    }

    /**
     * 判断非Ajax请求
     *
     * @param request HttpServletRequest
     * @return true是，false不是
     */
    public static boolean isNotAjaxRequest(HttpServletRequest request) {
        return !isAjaxRequest(request);
    }


    /**
     * 替换掉html内容<>
     *
     * @param src
     * @return
     */
    public static String replaceTagHTML(String src) {
        String regex = "\\<(.+?)\\>";
        if (StringUtils.isNotEmpty(src)) {
            return src.replaceAll(regex, "");
        }
        return "";
    }

    /**
     * 获取请求的所有参数
     *
     * @param request HttpServletRequest
     * @return a=b&c=d&e=g
     */
    public static String getRequestParameter(HttpServletRequest request) {
        StringBuffer params = new StringBuffer();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            params.append(name).append("=").append(value).append("&");
        }
        return params.toString();
    }

    /**
     * 获取请求的所有参数
     *
     * @param request HttpServletRequest
     * @return a=b&c=d&e=g
     */
    public static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            params.put(name, value);
        }
        return params;
    }

    /**
     * 获取请求的完整路径（包含参数）
     *
     * @param request
     * @return
     */
    public static String getCompleteUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String params = getRequestParameter(request);
        if (StringUtils.isNotEmpty(params)) {
            url.append("?").append(params);
        }

        return url.toString();
    }

    /**
     * 验证来源
     *
     * @param request
     * @return false验证成功，true验证成功
     */
    public static boolean validateReferer(HttpServletRequest request) {
        String Referer = request.getHeader("Referer");
        if (StringUtils.isNotEmpty(Referer)) {
            Referer = Referer.replace("http://", "").replace("https://", "");
            if (Referer.contains(":")) {
                Referer = Referer.split(":")[1];
            }
            if (Referer.contains(MYDOMAIN)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取IP的城市地址
     * @param request
     * @return 地址
     */
	/*public static String getIpToAddressStr(HttpServletRequest request)throws IOException{
		//获取当前IP的地址
		List<String> addressList = getIpToAddress(request);
		if (ObjectUtils.isNotEmpty(addressList) && addressList.size() > 2) {
			String region = addressList.get(2);
			if (StringUtils.isNotEmpty(region)) {
				region = region.replaceAll("省", "").replaceAll("市", "");
				return region;
			}
		}
		return null;
	}*/

    /**
     * 通过IP获取IP所在地址
     * @param request HttpServletRequest
     * @return 地区
     */
	/*public static List<String> getIpToAddress(HttpServletRequest request) {
		try{
			if(ObjectUtils.isNotEmpty(request)){
				String ip = getIpAddr(request);
				String idData = HttpUtil.doGet("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
				Map<String,Object> resultMap = gson.fromJson(idData,new TypeToken<Map<String,Object>>(){}.getType());
				if(ObjectUtils.isNotEmpty(resultMap)){
					Map<String,String> dataMap = (Map<String,String>)resultMap.get("data");
					List<String> vals = new ArrayList<String>();
					if(ObjectUtils.isNotEmpty(dataMap)){
						String region_id = dataMap.get("region_id");
						if(!region_id.equals("-1")){
							String country = dataMap.get("country");//国家
							if(StringUtils.isNotEmpty(country)){
								vals.add(country);
							}
							String area = dataMap.get("area");//地理区域（华北、华南等）
							if(StringUtils.isNotEmpty(area)){
								vals.add(area);
							}
							String region = dataMap.get("region");//地理区域（省，直辖区区）
							if(StringUtils.isNotEmpty(region)){
								vals.add(region);
							}
							String city = dataMap.get("city");//市
							if(StringUtils.isNotEmpty(city)){
								vals.add(city);
							}
							String county = dataMap.get("county");//县
							if(StringUtils.isNotEmpty(county)){
								vals.add(county);
							}
						}
					}
					if(ObjectUtils.isNotEmpty(vals)){
						return vals;
					}
				}
			}
		}catch (Exception e){
		}
		return null;
	}*/
}

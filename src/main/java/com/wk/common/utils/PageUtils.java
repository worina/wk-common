package com.wk.common.utils;

import com.github.pagehelper.PageHelper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class PageUtils {

    /**
     * 分页
     */
    public static void startPage() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Integer page = 1;
        Integer pageSize = 10;

        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");
        try {
            if (pageStr != null && Integer.valueOf(pageStr) > 0) {
                page = Integer.valueOf(pageStr);
            }
        } catch (Exception e) {

        }
        try {
            if (pageSizeStr != null && Integer.valueOf(pageSizeStr) > 0) {
                pageSize = Integer.valueOf(pageSizeStr);
            }
        } catch (Exception e) {

        }
        PageHelper.startPage(page, pageSize);
    }
}

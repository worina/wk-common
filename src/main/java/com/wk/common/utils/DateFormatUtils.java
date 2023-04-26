package com.wk.common.utils;


import com.dingtalk.api.DingTalkClient;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: DateFormatUtils
 * @description:
 * @author: dm
 * @create: 2020-08-17 19:04
 */
public class DateFormatUtils {

    private static final FastHashMap datePartenMap = new FastHashMap();

    static {
        List<String> propList = new ArrayList<>();
        propList.add("yyyy-MM-dd");
        propList.add("yyyy-MM-dd HH:mm:ss");
        propList.add("yyyyMMddHHmmss");
        datePartenMap.setFast(true);
        for(String dateParten : propList) {
            if(StringUtils.startsWith(dateParten, "#")) continue;
            datePartenMap.put(StringUtils.trim(dateParten), new SimpleDateFormat(dateParten));
        }
    }

    public static Date safeParseDate(final String dateParten, final String dateStr) throws Exception {
        return getFormat(dateParten).parse(dateStr);
    }

    public static String safeFormatDate(final String dateParten, final Date date) {
        return getFormat(dateParten).format(date);
    }

    private static ThreadLocal<FastHashMap> threadLocal = new ThreadLocal<FastHashMap>(){
        protected synchronized FastHashMap initialValue() {
            return (FastHashMap)datePartenMap.clone();
        }
    };

    private static DateFormat getFormat(final String dateParten){
        return (DateFormat)threadLocal.get().get(dateParten);
    }

}

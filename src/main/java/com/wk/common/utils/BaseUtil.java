package com.wk.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class BaseUtil {
    public static Logger logger = LoggerFactory.getLogger(BaseUtil.class);
    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").registerTypeAdapter(new TypeToken<Map<String, Object>>() {
    }.getType(), new MapTypeAdapter()).create();
}

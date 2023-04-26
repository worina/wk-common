package com.wk.common.conf;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.wk.common.dto.VisitUser;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {
    private static final ThreadLocal<Map<String, Object>> CTX_HOLDER = new TransmittableThreadLocal<>();
    private static final String VISITOR_KEY = "visitor";
    private static final String CLIENT_KEY = "client";
    private static final String TOKEN_KEY = "token";

    static {
        CTX_HOLDER.set(new HashMap<>(3));
    }

    public final static void init() {
        CTX_HOLDER.set(new HashMap<String, Object>(3));
    }

    public final static void putContext(String key, Object value) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            init();
            ctx = CTX_HOLDER.get();
        }
        ctx.put(key, value);
    }

    /**
     * 从线程上下文中获取内容
     *
     * @param key
     */
    public final static <T extends Object> T getContext(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }

    /**
     * 获取线程上下文
     */
    public final static Map<String, Object> getContext() {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return ctx;
    }

    /**
     * 删除上下文中的key
     *
     * @param key
     */
    public final static void remove(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            ctx.remove(key);
        }
    }

    /**
     * 上下文中是否包含此key
     *
     * @param key
     * @return
     */
    public final static boolean contains(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            return ctx.containsKey(key);
        }
        return false;
    }

    /**
     * 清空线程上下文
     */
    public final static void clean() {
        CTX_HOLDER.remove();
    }

    /**
     * @param
     * @param
     */
    public final static void putVisitor(VisitUser userJson) {
        putContext(VISITOR_KEY, userJson);
    }

    /**
     * @param
     * @return
     */
    public final static VisitUser getVisitor() {
        return getContext(VISITOR_KEY);
    }

    /**
     * @param client
     */
    public final static void putClient(String client) {
        putContext(CLIENT_KEY, client);
    }

    /**
     * @return
     */
    public final static String getClient() {
        return getContext(CLIENT_KEY);
    }

    public final static void putToken(String token) {
        putContext(TOKEN_KEY, token);
    }

    public final static String getToken() {
        return getContext(TOKEN_KEY);
    }
}

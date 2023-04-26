package com.wk.common.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：本地线程相关变量的存取
 * 
 * @author admin
 * 
 */

public class ThreadUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

	@SuppressWarnings("rawtypes")
	private static final ThreadLocal ctx = new TransmittableThreadLocal();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void put(String key, Object value) {
//		if("accessToken".equals(key)) {
			//token不能在整个jvm内存中共用，因此使用本地线程变量存储, 以保证在整个线程周期内存在并且不会与其他线程产生冲突
			Map m = (Map) ctx.get();
			if (m == null) {
				m = new HashMap();
			}
			m.put(key, value);
			ctx.set(m);
	}

	@SuppressWarnings("rawtypes")
	public static Object get(String key) {
			Object value = null;
			Map m = (Map) ctx.get();
			if (m != null) {
				value = m.get(key);
			}
			return value;
	}
	
	/**
	 * 
	 * 在一次请求完成后需要清理线程本地变量
	 * 
	 */
	public static void clearThreadVarible() {
		ctx.remove();
	}

}

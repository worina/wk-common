package com.wk.common.utils;

import java.util.List;

public class BeanUtils extends org.springframework.beans.BeanUtils{

	@SuppressWarnings("unchecked")
	public static void copyListProperties(List sourceList,List targetList,Class clazz) {
		if(sourceList!=null&&sourceList.size()>0) {
			sourceList.stream().forEach(s -> {
				try {
					Object newInstance = clazz.newInstance();
					BeanUtils.copyProperties(s, newInstance);
					targetList.add(newInstance);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		}
	}
}

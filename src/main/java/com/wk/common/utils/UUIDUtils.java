package com.wk.common.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String getToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}

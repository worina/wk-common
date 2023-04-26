package com.wk.common.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class DataUtils {

	private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");

	public static void main(String[] args) {
		//System.out.println(isMobile("1354475546"));
		LocalDate today = LocalDate.now();



	}
	
	public static boolean isMobile(String mobile) {
		return CHINA_PATTERN.matcher(mobile).matches();
	}



	
}

package com.minibank.util;

import java.util.Calendar;
import java.util.Date;

public class MiniBankUtil {

	public static int getMonth() {
		java.util.Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		return month;
	}
}

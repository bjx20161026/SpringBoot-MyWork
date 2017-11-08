package org.springboot.workorder.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDate {
	public Date getDay(int i) throws ParseException {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		now.add(Calendar.DAY_OF_MONTH,i);
		return sdf.parse(sdf.format(now.getTime()));
	}
	public String getDayString(int i){
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		now.add(Calendar.DAY_OF_MONTH,i);
		return sdf.format(now.getTime());
	}
}

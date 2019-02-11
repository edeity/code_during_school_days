package net.edeity.demo.hms.appearance;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 规范日期的字符串
 * @author Javer
 *
 */
public class DateString {
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	public static String format(Calendar c) {
		return dateFormat.format(c.getTime());
	}
}

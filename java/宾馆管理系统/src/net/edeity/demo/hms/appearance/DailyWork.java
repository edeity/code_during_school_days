package net.edeity.demo.hms.appearance;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.edeity.demo.hms.database.Arrange;
import net.edeity.demo.hms.database.DB;

/**
 * 每天需对数据库进行的操作 该类将根据每天的情况更新数据库中关于Arrange的信息 以使Arrange的安排时刻与当天日期保持一致
 * 该类还提供对时间的基本格式的规定
 * @author Javer
 */
public class DailyWork {
	// 正确的格式"yyyy-MM-dd HH:mm:ss E", yyyy MM dd HH mm ss E 一定要正确
	static SimpleDateFormat dateFormate = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) {
		// Calendar c = Calendar.getInstance();
		// String date = dateFormate.format(c.getTime());
//		deleteBeforeDay();
		addNextDay();
		System.out.println("操作成功");
	}

	/**
	 * 删除今天的前一天的房间安排记录 这是因为前一天的安排记录将不再用到
	 */
	public static void deleteBeforeDay() {
		Calendar tempC = Calendar.getInstance();
		tempC.add(Calendar.DATE, -1);
		// 但''不可用时,可以用``来代替
		String sql = "alter table room_arrange drop column `"
				+ dateFormate.format(tempC.getTime()) + "`";
		try {
			DB.alter(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加今天往后TIME_LIMIT的时间安排 TIME_LIMIT代表的是从今天起可预定的日子的最大值
	 */
	public static void addNextDay() {
		Calendar tempC = Calendar.getInstance();
		tempC.add(Calendar.DATE, Arrange.TIME_LIMIT);
		// 但''不可用时,可以用``来代替
		String sql = "alter table room_arrange add column `"
				+ dateFormate.format(tempC.getTime()) + "` int(11) default 0";
		try {
			DB.alter(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package net.edeity.demo.hms.appearance;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.edeity.demo.hms.database.Arrange;
import net.edeity.demo.hms.database.DB;

/**
 * ÿ��������ݿ���еĲ��� ���ཫ����ÿ�������������ݿ��й���Arrange����Ϣ ��ʹArrange�İ���ʱ���뵱�����ڱ���һ��
 * ���໹�ṩ��ʱ��Ļ�����ʽ�Ĺ涨
 * @author Javer
 */
public class DailyWork {
	// ��ȷ�ĸ�ʽ"yyyy-MM-dd HH:mm:ss E", yyyy MM dd HH mm ss E һ��Ҫ��ȷ
	static SimpleDateFormat dateFormate = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) {
		// Calendar c = Calendar.getInstance();
		// String date = dateFormate.format(c.getTime());
//		deleteBeforeDay();
		addNextDay();
		System.out.println("�����ɹ�");
	}

	/**
	 * ɾ�������ǰһ��ķ��䰲�ż�¼ ������Ϊǰһ��İ��ż�¼�������õ�
	 */
	public static void deleteBeforeDay() {
		Calendar tempC = Calendar.getInstance();
		tempC.add(Calendar.DATE, -1);
		// ��''������ʱ,������``������
		String sql = "alter table room_arrange drop column `"
				+ dateFormate.format(tempC.getTime()) + "`";
		try {
			DB.alter(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ӽ�������TIME_LIMIT��ʱ�䰲�� TIME_LIMIT������Ǵӽ������Ԥ�������ӵ����ֵ
	 */
	public static void addNextDay() {
		Calendar tempC = Calendar.getInstance();
		tempC.add(Calendar.DATE, Arrange.TIME_LIMIT);
		// ��''������ʱ,������``������
		String sql = "alter table room_arrange add column `"
				+ dateFormate.format(tempC.getTime()) + "` int(11) default 0";
		try {
			DB.alter(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

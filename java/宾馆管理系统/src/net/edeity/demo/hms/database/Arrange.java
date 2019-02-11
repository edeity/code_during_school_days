package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import net.edeity.demo.hms.appearance.DateString;

/**
 * �������ڲ����˵���Щ�����ʱ���ڷǿ��еķ���
 * @author Javer
 *
 */
public class Arrange {
	public static final int TIME_LIMIT = 30;//���������ʱ�䳤��
	public static final int SPARE = 0;//����
	public static final int BOOK = 1;//Ԥ��
	public static final int CHECK_IN = 2;//��ס
	int year;
	int month;
	int date;
	int lastDay;
	public Arrange(int year, int month, int date, int lastDay) {
		this.year = year;
		this.month = month;
		this.date = date;
		this.lastDay = lastDay;
	}
	/**
	 * ����ʱ����Ϣ���˵���Щ�����ʱ��β����еķ���
	 * @param rooms Ҫ�����˵ļ���
	 * @return
	 */
	public  ArrayList<Room> filterByRoomState(ArrayList<Room> rooms) {
		ArrayList<Room> newRooms = new ArrayList<Room>();
		for(Room room : rooms) {
			if(filterByRoomState(room) != null)
				newRooms.add(room);
		}
		return newRooms;
	}

	/**
	 * �����ڸ�����ʱ���ڸ÷���һֱ����,�򷵻�true
	 * @param room
	 * @return
	 */
	public Room filterByRoomState(Room room) {
		Calendar cal = Calendar.getInstance();//�����µ���������
		cal.set(year, month, date);
		String roomID = room.getID();
		String sql = "select * from room_arrange where room_id = '" + roomID + " ' ";
		try {
			ResultSet rs = DB.select(sql);
			rs.next();
			for(int i = 0; i<lastDay; i++) {
				//�������ӵķ��䰲��
				String day = DateString.format(cal);
				int state = rs.getInt(day);
//				System.out.println("���ʱ�� : " + day + "  ���� : " + roomID + "  ����״̬ : " + state);
				//�����Ѿ�������ס��Ԥ��,�����÷��䲻�ڿ���,�ǵð�
				if(state != SPARE) {
					room = null;
					break;
				}else{
					cal.add(Calendar.DATE, 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close();
		}
		return room;
	}
	public static void changeArrange(String roomID, int arrangeKind, Calendar functionDate,
			int lastDay) throws SQLException {
		for(int i = 0; i<lastDay; i++) {
			String sql = "update room_arrange set `" + DateString.format(functionDate) + "` = " + arrangeKind +
					" where room_id = '" + roomID + "'";
			System.out.println(sql);
			DB.update(sql);
			functionDate.add(Calendar.DATE, 1);
		}
	}
	/**
	 * Ϊ��roomID���ſ���
	 * @param roomID
	 */
	public static boolean init(String roomID) {
		String sql = "insert into room_arrange (room_id) values (" + roomID + ")";
		try {
			DB.insert(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

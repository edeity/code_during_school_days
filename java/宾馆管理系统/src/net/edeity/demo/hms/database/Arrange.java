package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import net.edeity.demo.hms.appearance.DateString;

/**
 * 根据日期查表过滤掉那些在这段时间内非空闲的房间
 * @author Javer
 *
 */
public class Arrange {
	public static final int TIME_LIMIT = 30;//允许操作的时间长度
	public static final int SPARE = 0;//空闲
	public static final int BOOK = 1;//预定
	public static final int CHECK_IN = 2;//入住
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
	 * 根据时间信息过滤掉那些在这段时间段不空闲的房间
	 * @param rooms 要被过滤的集合
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
	 * 假如在给定的时间内该房间一直空闲,则返回true
	 * @param room
	 * @return
	 */
	public Room filterByRoomState(Room room) {
		Calendar cal = Calendar.getInstance();//建立新的日历规则
		cal.set(year, month, date);
		String roomID = room.getID();
		String sql = "select * from room_arrange where room_id = '" + roomID + " ' ";
		try {
			ResultSet rs = DB.select(sql);
			rs.next();
			for(int i = 0; i<lastDay; i++) {
				//检测该日子的房间安排
				String day = DateString.format(cal);
				int state = rs.getInt(day);
//				System.out.println("检测时间 : " + day + "  房号 : " + roomID + "  房间状态 : " + state);
				//假如已经有人入住或预定,则代表该房间不在空闲,记得把
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
	 * 为该roomID安排空闲
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

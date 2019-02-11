package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * 代表一个特殊的房间
 * 该类供查询房间条件所需
 * @author Javer
 *
 */
public class Room implements Inquirable{
	public static final int SINGLE_BED = 1;//单人房
	public static final int DOUBLE_BED = 2;//双人房
	public static final int ECONOMIC_LEVEL = 1;//经济房
	public static final int BUSSINESS_LEVEL = 2;//商务房
	private String ID;
	private int bedNum = 0;
	private int level = 0;
	private int price;
	public Room() {
		
	}
	public Room(String ID, int bedNum, int level, int price) {
		this.ID = ID;
		this.bedNum = bedNum;
		this.level = level;
		this.price = price;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getBedNum() {
		return bedNum;
	}

	public void setBedNum(int bedNum) {
		this.bedNum = bedNum;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPrice() {
		return price;
	}

	public String getRoomID() {
		return ID;
	}
	public int getRoomPrice() {
		return price;
	}
	public String getSimpleDescribe() {
		return "  房号 : " + ID + " (￥ " + price +  " ) ";
	}
	public String getDetailDescribe() {
		String detail = "";
		detail += "------------房间具体信息------------\n";
		detail += " 房间号 : " + ID + "\n";
		switch(bedNum) {
		case SINGLE_BED:
			detail += " 单人间 \n";break;
		case DOUBLE_BED:
			detail += " 双人间 \n";break;
		}
		switch(level) {
		case BUSSINESS_LEVEL:
			detail += " 商务房 \n";break;
		case ECONOMIC_LEVEL:
			detail += " 经济房 \n";break;
		}
		detail += " 房间价格(￥) : " + price + "\n";
		return detail;
	}
	/**
	 * 根据抽象的ROOM信息寻找出具有共同特性的所有房间
	 * @param abRoom
	 */
	public static ArrayList<Room> searchByCondition(Room abRoom) {
		String sql = "select id, price from room_info where bed_num = '" 
		+ abRoom.getBedNum() + " ' and " + "level = '" + abRoom.getLevel() + "'";
//		System.out.println(sql);
		ArrayList<Room> rooms = new ArrayList<Room>();
		try {
			ResultSet rs = DB.select(sql);
			while(rs.next()) {
				Room tempRoom = new Room(rs.getString("id"), abRoom.bedNum, abRoom.level, rs.getInt("price"));
				rooms.add(tempRoom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close();
		}
		return rooms;
	}
	/**
	 * 根据RoomID查找房间
	 * @param roomID
	 * @return 拥有给RoomID的房间
	 */
	public static Room searchByID(String roomID) {
		String sql = "select bed_num, level, price from room_info where id = '"  + roomID +  " '";
		Room tempRoom = null;
		try {
			ResultSet rs = DB.select(sql);
			rs.next();
			tempRoom = new Room(roomID, rs.getInt("bed_num"), rs.getInt("level"), rs.getInt("price"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "没有找到相应的房间");
		} finally {
			DB.close();
		}
		return tempRoom;
	}
	@Override
	public String[][] inquire(int index, int length) {
		int columnNum = getTitle().length;
		String[][] tempResult = new String[length][columnNum];// 为8个column,
		//这个显示时不应该根据条数,而应该是所有的房间
		String sql = "select id, bed_num, level, price from room_info";
			try {
				ResultSet rs = DB.select(sql);
				for (int i = 0; i < length; i++) {
					rs.next();
					tempResult[i][0] = rs.getString("id");
					tempResult[i][1] = rs.getString("bed_num");
					tempResult[i][2] = rs.getString("level");
					tempResult[i][3] = rs.getString("price");
				}
			} catch (SQLException e) {
//				 e.printStackTrace(); 
				return tempResult;
			}
		return tempResult;
	}
	@Override
	public String[] getTitle() {
		String[] title = {"房号","床位", "类型", "价格"};
		return title;
	}
	/**
	 * 注册新房间的属性
	 * @param roomID
	 * @param bedNum
	 * @param level
	 * @param roomPrice
	 * @return
	 */
	public static boolean register(String roomID, int bedNum, int level,
			String roomPrice) {
		String sql = "insert into room_info ( id, bed_num, level, price ) values (" + roomID + "," + bedNum + "," + level + "," + roomPrice + ")";
		try {
			DB.insert(sql);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			if(e.getErrorCode() == 1062)//已有房间
				JOptionPane.showMessageDialog(null, "已有房号为"+roomID+"的房间");
//			e.printStackTrace();
		}
		return false;
	}
}

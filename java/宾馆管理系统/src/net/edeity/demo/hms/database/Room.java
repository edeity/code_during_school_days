package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * ����һ������ķ���
 * ���๩��ѯ������������
 * @author Javer
 *
 */
public class Room implements Inquirable{
	public static final int SINGLE_BED = 1;//���˷�
	public static final int DOUBLE_BED = 2;//˫�˷�
	public static final int ECONOMIC_LEVEL = 1;//���÷�
	public static final int BUSSINESS_LEVEL = 2;//����
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
		return "  ���� : " + ID + " (�� " + price +  " ) ";
	}
	public String getDetailDescribe() {
		String detail = "";
		detail += "------------���������Ϣ------------\n";
		detail += " ����� : " + ID + "\n";
		switch(bedNum) {
		case SINGLE_BED:
			detail += " ���˼� \n";break;
		case DOUBLE_BED:
			detail += " ˫�˼� \n";break;
		}
		switch(level) {
		case BUSSINESS_LEVEL:
			detail += " ���� \n";break;
		case ECONOMIC_LEVEL:
			detail += " ���÷� \n";break;
		}
		detail += " ����۸�(��) : " + price + "\n";
		return detail;
	}
	/**
	 * ���ݳ����ROOM��ϢѰ�ҳ����й�ͬ���Ե����з���
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
	 * ����RoomID���ҷ���
	 * @param roomID
	 * @return ӵ�и�RoomID�ķ���
	 */
	public static Room searchByID(String roomID) {
		String sql = "select bed_num, level, price from room_info where id = '"  + roomID +  " '";
		Room tempRoom = null;
		try {
			ResultSet rs = DB.select(sql);
			rs.next();
			tempRoom = new Room(roomID, rs.getInt("bed_num"), rs.getInt("level"), rs.getInt("price"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "û���ҵ���Ӧ�ķ���");
		} finally {
			DB.close();
		}
		return tempRoom;
	}
	@Override
	public String[][] inquire(int index, int length) {
		int columnNum = getTitle().length;
		String[][] tempResult = new String[length][columnNum];// Ϊ8��column,
		//�����ʾʱ��Ӧ�ø�������,��Ӧ�������еķ���
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
		String[] title = {"����","��λ", "����", "�۸�"};
		return title;
	}
	/**
	 * ע���·��������
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
			if(e.getErrorCode() == 1062)//���з���
				JOptionPane.showMessageDialog(null, "���з���Ϊ"+roomID+"�ķ���");
//			e.printStackTrace();
		}
		return false;
	}
}

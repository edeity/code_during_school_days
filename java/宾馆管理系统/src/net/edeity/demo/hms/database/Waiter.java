package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


/**
 * ����ƽ���ķ���Ա
 * @author Javer
 *
 */
public class Waiter {
	public static final int RANK_OF_WAITER = 1;
	public static final int RANK_OF_MANAGER = 2;
	private String waiterName;
	private String waiterID;
	private int rank;
	public Waiter(String waiterID) {
		this.waiterID = waiterID;
	}
	/**
	 * ��õ�ǰ���ʹ���ߵ�Ȩ�޵Ǽ�
	 * @return
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * ����Ա��¼���
	 * @param password ����
	 * @return 
	 */
	public boolean login(char[] password) {
			String sql = "select name, password, rank from waiter_info where id = '" + waiterID + "'";
			try {
				ResultSet rs = DB.select(sql);
				rs.next();
				if(String.valueOf(password).equals(rs.getString("password"))) {
					waiterName = rs.getString("name");
					rank = Integer.parseInt(rs.getString("rank"));
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DB.close();
			}
			return false;
	}
	/**
	 * ��öԷ���Ա��̵�����
	 * @return ����Ա��̵�����
	 */
	public String getSimpleDescribe() {
		String simpleDescribe = waiterName + " ( " + waiterID + " ) ";
		return simpleDescribe;
	}
	/**
	 * ����Աע��
	 * @param id
	 * @param name
	 * @param password
	 */
	public static boolean register(String id, String name, String password) {
		String sql = "insert into waiter_info (id, name, password, rank) values (" + id + ", '" + name + "','" + password +"'," + 1 + ")";
		try {
			DB.insert(sql);
			return true;
		} catch (SQLException e) {
			if(e.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "���֤ID�ظ�");
			}
			e.printStackTrace();
		}
		return false;
	}
}

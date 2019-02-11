package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * �ͻ�����
 * @author Javer
 *
 */
public class Customer implements Inquirable{
	public static int LEVEL_SIMPLE = 0;//��ͨ�Ļ�Ա(�൱�ڲ��ǻ�Ա)
	public static int LEVEL_PAY = 1;//��Ա��֧����Ҫ�ĵǼ�
	public static int LEVEL_DELAY = 2;//֧�ֹҵ��ĵǼ�
	private String name;
	private String sex;
	private String ID;
	private String phone;
	private String address;
	public Customer(String name, String ID, String sex){
		this.name = name;
		this.ID = ID;
		this.sex = sex;
	}
	public Customer(String name, String sex, String ID, String phone, String address) {
		this.name = name;
		this.sex = sex;
		this.ID = ID;
		this.phone = phone;
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public String getID() {
		return ID;
	}
	public String getSimpleDescribe() {
		return " �ͻ�: " + name + " ( "+ID + " ) ";
	}
	/**
	 * ��鵱ǰ�û�����Ϣ�Ƿ���ȷ
	 */
	public boolean checkInfo() {
		return true;
	}
	/**
	 * ����ǰ�û�����Ϣע�������ݿ�,�����ݿ����Ѿ����ڸ��û���Ϣ,
	 * ����Ϊ֮ǰ�����������ݿ���û��½��µ���,����true
	 * @return
	 */
	public boolean register() {
		try {
			String sql = "insert into customer_info (id, name, sex, address, phone, level, storage) values ('"+
		ID +"','" +name+"','"+sex+"','"+address+"','"+phone+"',"+0+","+0+")"; 
			DB.insert(sql);
			return true;
		} catch (SQLException e) {
//			e.printStackTrace();
			if(e.getErrorCode() == 1062) {
				//1062������primarry�ظ�,���������Ѿ����ڸ�ID����Ϣ,������Ҫ��֤��ֻ�Ǹ�ID�Ƿ����������
				String sql = "select name from customer_info where ID = '" + ID + "' ";
				try {
					ResultSet rs = DB.select(sql);
					rs.next();
					if(name.equals(rs.getString("name").trim())) {
						return true;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					return false;
				}
			}
			return false;
		}finally {
			DB.close();
		}
	}
	/**
	 * �ж��Ƿ���Vip�û�
	 * @return
	 */
	public boolean isVip() {
		String sql = "select level from customer_info where ID = '" + ID + "' ";
		try {
			ResultSet rs = DB.select(sql);
			rs.next();
			int level = rs.getInt("level");
			if(level >= LEVEL_PAY)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean canDelay() {
		String sql = "select level from customer_info where ID = '" + ID + "' ";
		try {
			ResultSet rs = DB.select(sql);
			rs.next();
			int level = rs.getInt("level");
			if(level >= LEVEL_DELAY)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean vipPay(double exceptMoney) {
		String sql = "select stroge from customer_info where ID = '" + ID + "'";
		ResultSet rs;
		try {
			rs = DB.select(sql);
			rs.next();
			int storage = rs.getInt("stroge");
			if(storage >= exceptMoney) {
				sql = "update customer_info set stroge = '" + (storage - exceptMoney) + "' where ID = " + ID + "'";
				DB.update(sql);
				return true;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "����");
		}
		return false;
	}
	/**
	 * �ο����û������
	 * @return
	 */
	public int remain() {
		String sql = "select storage from customer_info where id = '" +ID +"' and name = '" + name + "'";
		try {
			ResultSet rs = DB.select(sql);
			rs.next();
			int storage = rs.getInt("storage");
			return storage;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * vip��ֵ
	 * @param money
	 */
	public boolean recharge(String money) {
		String sql = "update customer_info set level = " + LEVEL_PAY + " where id = '" + ID + "' and name = '" + name + "'";
		int remainMoney = remain();
		try {
			DB.update(sql);
			if(remainMoney != -1) {
				String anSql = "update customer_info set storage = " + (Integer.parseInt(money)+remainMoney) + " where id = '" + ID + "' and name = '" + name + "'";
				DB.update(anSql);
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public String[][] inquire(int index, int length) {
		return null;
	}
	@Override
	public String[] getTitle() {
		return null;
	}
}

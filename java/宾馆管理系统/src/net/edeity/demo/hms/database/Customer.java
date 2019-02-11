package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * 客户的类
 * @author Javer
 *
 */
public class Customer implements Inquirable{
	public static int LEVEL_SIMPLE = 0;//普通的会员(相当于不是会员)
	public static int LEVEL_PAY = 1;//会员卡支付需要的登记
	public static int LEVEL_DELAY = 2;//支持挂单的登记
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
		return " 客户: " + name + " ( "+ID + " ) ";
	}
	/**
	 * 检查当前用户的信息是否正确
	 */
	public boolean checkInfo() {
		return true;
	}
	/**
	 * 将当前用户的信息注册入数据库,当数据库中已经存在该用户信息,
	 * 或者为之前不存在于数据库的用户新建新的列,返回true
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
				//1062代表是primarry重复,即可能是已经存在该ID的信息,接下来要验证的只是该ID是否和姓名符合
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
	 * 判断是否是Vip用户
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
			JOptionPane.showMessageDialog(null, "余额不足");
		}
		return false;
	}
	/**
	 * 参看该用户的余款
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
	 * vip充值
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

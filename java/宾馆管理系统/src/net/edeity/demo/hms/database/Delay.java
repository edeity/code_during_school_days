package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 代表挂单的数据
 * @author Javer
 *
 */
public class Delay implements Inquirable{

	@Override
	public String[][] inquire(int index, int length) {
		int columnNum = getTitle().length;
		String[][] tempResult = new String[length][columnNum];// 为8个column,
																// 10row
		for (int i = 0; i < length; i++) {
			String sql = "select bill_id, isPay from delay_info"
					+ " where index_num =" + (index + i);
			try {
				ResultSet rs = DB.select(sql);
				rs.next();
				tempResult[i][0] = rs.getString("bill_id");
				if(rs.getInt("isPay")==0) {
					tempResult[i][1] = "未支付";
				}else {
					tempResult[i][1] = "已支付";
				}
			} catch (SQLException e) {
//				 e.printStackTrace();
			return tempResult;
			}
		}
		return tempResult;
	}

	@Override
	public String[] getTitle() {
		String[] title = {"账单号", "支付情况"};
		return title;
	}

	public static boolean delayPay(String ID) {
		String sql = "insert into delay_info (bill_id) values (" +ID + ")" ;
		try {
			DB.insert(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}

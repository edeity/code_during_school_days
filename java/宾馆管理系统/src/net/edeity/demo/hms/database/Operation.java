package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import net.edeity.demo.hms.appearance.DateString;
import net.edeity.demo.hms.appearance.HU;

/**
 * 该类存储每次账单的信息 该类应负责检验账单的正确性,并将最终结果记录到数据库中 相当于记录每次对服务员每次对房间的操作
 * 
 * @author Javer
 *
 */
public class Operation implements Inquirable {
	// 账单种类
	public static final String BOOK = "预定"; // 预定
	public static final String CHECK_IN = "入住"; // 支付
	public static final String Change = "更换";//更换
	public static final String VIP_RECHARGE = "vip充值"; // vip充值
	public static final String COOPERATE_RECHARGE = "团体充值"; // 团体充值
	private String[] bill_kinds = { BOOK, CHECK_IN, VIP_RECHARGE,
			COOPERATE_RECHARGE };// 支持的操作
	// 支持的账单支付类型
	public static final String PAY_OF_CASE = "现金支付"; // 现金支付
	public static final String PAY_OF_VIP = "vip支付"; // vip卡支付
	public static final String PAY_OF_DELAY = "vip挂单";// 合作团体挂单
	private String[] pay_kinds = { PAY_OF_CASE, PAY_OF_VIP, PAY_OF_DELAY };// 支持的付款方式
	private double vipRate = 0.98; // 会员打折率
	// 账单的信息
	private String ID;// 账单号码
	private Customer customer;// 客人信息
	private String waiterName;// 服务员的名字
	private String waiterID;// 服务员的工作号
	private Room room;// 房间的信息
	private String produceDate;// 产生账单的日期
	private String billKind;// 账单的类型
	private String payKind;// 支付的方式
	private Calendar functionDate;// 账单起作用的作用
	private int lastDay;// 账单持续的时间
	// 账单总体信息
	private String formatteBill = "";// 格式化的账单信息
	private boolean isFormatte;// 是否已经格式化账单
	// 账单结算的情况
	private boolean isPay;
	private double totalPrice;

	/**
	 * 将账单记录入数据库,下面操作应该为一个事物而不应该单独分开
	 */
	public boolean pay() {
		try {
			logDB();
			int arrangeKind = -1;
			if(billKind.equals(BOOK)) 
				arrangeKind = Arrange.BOOK;
			else if(billKind.equals(CHECK_IN))
				arrangeKind = Arrange.CHECK_IN;
			Arrange.changeArrange(room.getID(), arrangeKind, functionDate, lastDay);
			if (payKind == PAY_OF_CASE) {
				isPay = true;
			} else if (payKind == PAY_OF_VIP) {
				isPay = customer.vipPay(totalPrice);
			} else if(payKind == PAY_OF_DELAY) {
				isPay = Delay.delayPay(ID);
			}
		} catch (SQLException e) {
			isPay = false;
			e.printStackTrace();
		}
		return isPay;
	}
	/**
	 * 将该账单记录在操作表中
	 * @throws SQLException 
	 */
	private void logDB() throws SQLException {
		String sql = "insert into bill_info (id, kind, day, last_day, customer_id, room_id, price, pay_mode, waiter_id) values ('" 
				+ ID + "','"+billKind + "','" + DateString.format(functionDate) + "','" + lastDay + "','" + customer.getID() + "','" + room.getID() + "','" + getTotalMoney() + "','"
				+ payKind + "','" + waiterID + "')";
		DB.insert(sql);
	}
	private double getTotalMoney() {
		if (payKind == PAY_OF_VIP) {
			totalPrice = (room.getRoomPrice() * lastDay) * vipRate;
		} else {
			totalPrice = room.getRoomPrice() * lastDay;
		}
		return totalPrice;
	}

	public Customer getCustomer() {
		return customer;
	}

	/**
	 * 设置进行该操作的服务员信息
	 * 
	 * @param waiterName
	 *            服务员姓名
	 * @param waiterID
	 *            服务员员工号
	 */
	public void setWaiterInfo(String waiterName, String waiterID) {
		this.waiterName = waiterName;
		this.waiterID = waiterID;
	}

	public void setCustomerInfo(Customer customer) {
		this.customer = customer;
	}

	public void setRoomInfo(Room chooseRoom) {
		this.room = chooseRoom;

	}

	/**
	 * 检查支付方式
	 * 
	 * @param payKind
	 *            支付的种类
	 */
	public void setPayKind(String payKind) {
		boolean isValidate = false;
		// 支持其中一种支付方式,便证明支付方式有效
		for (String i : pay_kinds) {
			if (payKind.equals(i))
				isValidate = true;
		}
		if (isValidate) {
			this.payKind = payKind;
		} else {
			HU.seriousError("支付方式");
		}
	}

	/**
	 * 设置账单的种类
	 * 
	 * @param kind
	 *            账单种类
	 */
	public void setOperationKind(String kind) {
		boolean isValidate = false;
		for (String i : bill_kinds) {
			if (kind.equals(i))
				isValidate = true;
		}
		if (isValidate) {
			this.billKind = kind;
		} else {
			HU.seriousError("账单类型");
		}
	}

	/**
	 * 设置操作起作用的时间和持续时间
	 * 
	 * @param calendar
	 *            起作用的时间,可以传入值"today"会动态生成当天的值
	 * @param lastDay
	 *            持续的时间
	 */
	public void setFunction(Calendar calendar, int lastDay) {
		this.functionDate = calendar;
		this.lastDay = lastDay;
	}

	/**
	 * 生成订单号和下订单的日期
	 */
	public void makeBillID() {
		Date tempDate = new Date();
		ID = String.valueOf(tempDate.getTime());// 精确到毫秒以保证id号码的唯一性
		produceDate = DateFormat.getDateInstance().format(tempDate);// 2014-9-xx,精确到日
	}

	/**
	 * 获得格式化后的账单显示,该方法会根据当前日期来生成账单订单号和操作日期
	 */
	public String getFormatteBill() {
		// 文字格式
		if (!isFormatte) {
			makeBillID();
			formatteBill += "订单号 : " + ID + "\n";
			formatteBill += "操作日期 : " + produceDate + "\n";
			formatteBill += "客户 : " + customer.getName() + "\n";
			formatteBill += "身份证号码 : " + customer.getID() + "\n";
			formatteBill += "操作类型 : " + billKind + "\n";
			formatteBill += billKind
					+ "时间 : "
					+ DateFormat.getDateInstance().format(
							functionDate.getTime()) + "\n";
			formatteBill += "房号 : " + room.getRoomID() + "\n";
			formatteBill += "房间价格/每日 : " + room.getRoomPrice() + "\n";
			formatteBill += "持续时间 : " + lastDay + "\n";
			if (payKind == PAY_OF_VIP) {
				formatteBill += "会员折率 : " + vipRate + "\n";
				formatteBill += "总价 : " + getTotalMoney() + "\n";
			} else {
				formatteBill += "总价 : " + getTotalMoney() + "\n";
			}
			formatteBill += "服务员 : " + waiterName + "\n";
			formatteBill += "服务员员工号 : " + waiterID + "\n";
			formatteBill += "付款方式 : " + payKind + "\n";
			isFormatte = true;
		}
		return formatteBill;
	}

	/**
	 * @return 返回简单的订单信息描述
	 */
	public String getSimpleBill() {
		return customer.getSimpleDescribe() + billKind
				+ room.getSimpleDescribe();
	}

	/**
	 * @return 返回详尽的订单信息描述
	 */
	public String getDetailedBill() {
		String detailBill = "-----------请服务员和顾客确认账单信息-------------\n";
		detailBill += getFormatteBill();
		detailBill += "--------若选择现金支付,请服务员在收取现金后再点击确认--------------\n";
		return detailBill;
	}

	public String getResultBill() {
		String resultBill = "";
		if (isPay) {
			resultBill += "-------------支付情况 : 成功--------------\n";
			resultBill += getFormatteBill();
			resultBill += "-------------感谢你的到来----------------\n";
		} else {
			resultBill += "----------支付情况 : 支付失败( 原因:未知 )---------\n";
			resultBill += getFormatteBill();
			resultBill += "-------------请稍后重试------------------";
		}
		return resultBill;
	}

	public String[] getTitle() {
		String[] title = { "账单ID", "账单类型", "下单时间", "客户身份号", "房间号", "账单金额",
				"支付模式", "服务员ID" };
		return title;
	}

	/**
	 * 查询表中的数目
	 * 
	 * @param index
	 *            index 索引
	 * @param length
	 *            返回结果的长度
	 * @return
	 */
	public String[][] inquire(int index, int length) {
		int columnNum = getTitle().length;
		String[][] tempResult = new String[length][columnNum];// 为8个column,
																// 10row
		for (int i = 0; i < length; i++) {
			String sql = "select id, kind, day, customer_id, room_id, price, pay_mode, waiter_id from bill_info"
					+ " where index_num =" + (index + i);
			try {
				ResultSet rs = DB.select(sql);
				rs.next();
				tempResult[i][0] = rs.getString("id");
				tempResult[i][1] = rs.getString("kind");
				tempResult[i][2] = rs.getString("day");
				tempResult[i][3] = rs.getString("customer_id");
				tempResult[i][4] = rs.getString("room_id");
				tempResult[i][5] = rs.getString("price");
				tempResult[i][6] = rs.getString("pay_mode");
				tempResult[i][7] = rs.getString("waiter_id");

			} catch (SQLException e) {
//				 e.printStackTrace(); 
//				return tempResult;
			}
		}
		return tempResult;
	}
}

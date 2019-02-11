package net.edeity.demo.hms.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import net.edeity.demo.hms.appearance.DateString;
import net.edeity.demo.hms.appearance.HU;

/**
 * ����洢ÿ���˵�����Ϣ ����Ӧ��������˵�����ȷ��,�������ս����¼�����ݿ��� �൱�ڼ�¼ÿ�ζԷ���Աÿ�ζԷ���Ĳ���
 * 
 * @author Javer
 *
 */
public class Operation implements Inquirable {
	// �˵�����
	public static final String BOOK = "Ԥ��"; // Ԥ��
	public static final String CHECK_IN = "��ס"; // ֧��
	public static final String Change = "����";//����
	public static final String VIP_RECHARGE = "vip��ֵ"; // vip��ֵ
	public static final String COOPERATE_RECHARGE = "�����ֵ"; // �����ֵ
	private String[] bill_kinds = { BOOK, CHECK_IN, VIP_RECHARGE,
			COOPERATE_RECHARGE };// ֧�ֵĲ���
	// ֧�ֵ��˵�֧������
	public static final String PAY_OF_CASE = "�ֽ�֧��"; // �ֽ�֧��
	public static final String PAY_OF_VIP = "vip֧��"; // vip��֧��
	public static final String PAY_OF_DELAY = "vip�ҵ�";// ��������ҵ�
	private String[] pay_kinds = { PAY_OF_CASE, PAY_OF_VIP, PAY_OF_DELAY };// ֧�ֵĸ��ʽ
	private double vipRate = 0.98; // ��Ա������
	// �˵�����Ϣ
	private String ID;// �˵�����
	private Customer customer;// ������Ϣ
	private String waiterName;// ����Ա������
	private String waiterID;// ����Ա�Ĺ�����
	private Room room;// �������Ϣ
	private String produceDate;// �����˵�������
	private String billKind;// �˵�������
	private String payKind;// ֧���ķ�ʽ
	private Calendar functionDate;// �˵������õ�����
	private int lastDay;// �˵�������ʱ��
	// �˵�������Ϣ
	private String formatteBill = "";// ��ʽ�����˵���Ϣ
	private boolean isFormatte;// �Ƿ��Ѿ���ʽ���˵�
	// �˵���������
	private boolean isPay;
	private double totalPrice;

	/**
	 * ���˵���¼�����ݿ�,�������Ӧ��Ϊһ���������Ӧ�õ����ֿ�
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
	 * �����˵���¼�ڲ�������
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
	 * ���ý��иò����ķ���Ա��Ϣ
	 * 
	 * @param waiterName
	 *            ����Ա����
	 * @param waiterID
	 *            ����ԱԱ����
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
	 * ���֧����ʽ
	 * 
	 * @param payKind
	 *            ֧��������
	 */
	public void setPayKind(String payKind) {
		boolean isValidate = false;
		// ֧������һ��֧����ʽ,��֤��֧����ʽ��Ч
		for (String i : pay_kinds) {
			if (payKind.equals(i))
				isValidate = true;
		}
		if (isValidate) {
			this.payKind = payKind;
		} else {
			HU.seriousError("֧����ʽ");
		}
	}

	/**
	 * �����˵�������
	 * 
	 * @param kind
	 *            �˵�����
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
			HU.seriousError("�˵�����");
		}
	}

	/**
	 * ���ò��������õ�ʱ��ͳ���ʱ��
	 * 
	 * @param calendar
	 *            �����õ�ʱ��,���Դ���ֵ"today"�ᶯ̬���ɵ����ֵ
	 * @param lastDay
	 *            ������ʱ��
	 */
	public void setFunction(Calendar calendar, int lastDay) {
		this.functionDate = calendar;
		this.lastDay = lastDay;
	}

	/**
	 * ���ɶ����ź��¶���������
	 */
	public void makeBillID() {
		Date tempDate = new Date();
		ID = String.valueOf(tempDate.getTime());// ��ȷ�������Ա�֤id�����Ψһ��
		produceDate = DateFormat.getDateInstance().format(tempDate);// 2014-9-xx,��ȷ����
	}

	/**
	 * ��ø�ʽ������˵���ʾ,�÷�������ݵ�ǰ�����������˵������źͲ�������
	 */
	public String getFormatteBill() {
		// ���ָ�ʽ
		if (!isFormatte) {
			makeBillID();
			formatteBill += "������ : " + ID + "\n";
			formatteBill += "�������� : " + produceDate + "\n";
			formatteBill += "�ͻ� : " + customer.getName() + "\n";
			formatteBill += "���֤���� : " + customer.getID() + "\n";
			formatteBill += "�������� : " + billKind + "\n";
			formatteBill += billKind
					+ "ʱ�� : "
					+ DateFormat.getDateInstance().format(
							functionDate.getTime()) + "\n";
			formatteBill += "���� : " + room.getRoomID() + "\n";
			formatteBill += "����۸�/ÿ�� : " + room.getRoomPrice() + "\n";
			formatteBill += "����ʱ�� : " + lastDay + "\n";
			if (payKind == PAY_OF_VIP) {
				formatteBill += "��Ա���� : " + vipRate + "\n";
				formatteBill += "�ܼ� : " + getTotalMoney() + "\n";
			} else {
				formatteBill += "�ܼ� : " + getTotalMoney() + "\n";
			}
			formatteBill += "����Ա : " + waiterName + "\n";
			formatteBill += "����ԱԱ���� : " + waiterID + "\n";
			formatteBill += "���ʽ : " + payKind + "\n";
			isFormatte = true;
		}
		return formatteBill;
	}

	/**
	 * @return ���ؼ򵥵Ķ�����Ϣ����
	 */
	public String getSimpleBill() {
		return customer.getSimpleDescribe() + billKind
				+ room.getSimpleDescribe();
	}

	/**
	 * @return �����꾡�Ķ�����Ϣ����
	 */
	public String getDetailedBill() {
		String detailBill = "-----------�����Ա�͹˿�ȷ���˵���Ϣ-------------\n";
		detailBill += getFormatteBill();
		detailBill += "--------��ѡ���ֽ�֧��,�����Ա����ȡ�ֽ���ٵ��ȷ��--------------\n";
		return detailBill;
	}

	public String getResultBill() {
		String resultBill = "";
		if (isPay) {
			resultBill += "-------------֧����� : �ɹ�--------------\n";
			resultBill += getFormatteBill();
			resultBill += "-------------��л��ĵ���----------------\n";
		} else {
			resultBill += "----------֧����� : ֧��ʧ��( ԭ��:δ֪ )---------\n";
			resultBill += getFormatteBill();
			resultBill += "-------------���Ժ�����------------------";
		}
		return resultBill;
	}

	public String[] getTitle() {
		String[] title = { "�˵�ID", "�˵�����", "�µ�ʱ��", "�ͻ���ݺ�", "�����", "�˵����",
				"֧��ģʽ", "����ԱID" };
		return title;
	}

	/**
	 * ��ѯ���е���Ŀ
	 * 
	 * @param index
	 *            index ����
	 * @param length
	 *            ���ؽ���ĳ���
	 * @return
	 */
	public String[][] inquire(int index, int length) {
		int columnNum = getTitle().length;
		String[][] tempResult = new String[length][columnNum];// Ϊ8��column,
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

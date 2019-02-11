package net.edeity.demo.hms.appearance;

import javax.swing.JOptionPane;

/**
 * HotelUtil
 * ����ͳһ һЩ���õĴ���,���ֲμ�����Ĵ�����
 * ����ܶ෽ʽ��Ӧ����д,������ݴ���
 * @author Javer
 *
 */
public class HU {
	public static void main(String[] args) {
//		isNotNull(null,"���֤");
//		illegalInput("���֤");
//		isPositiveNumber("��", "���֤");
		seriousError("�˵�����");
	}
	/**
	 * ��������ݱ���Ϊ�յĶ���
	 * @param str ������������
	 * @param message ��ʾ����Ϣ
	 * @return
	 */
	public static boolean isNotNull(String str, String tips){
		if(str == null || str.trim().equals("")) {
			JOptionPane.showMessageDialog(null, tips + "����Ϊ��");
			return false;
		}
		return true;
	}
	/**
	 * �������ش���ʱ�Ķ���
	 */
	public static void seriousError(String errorName) {
		JOptionPane.showMessageDialog(null, errorName +"�����˴���,������,����Ȼ���ִ���,����ϵ���ά����Ա");
	}
	
	/**
	 * ���벻�Ϸ�ʱ�Ķ���
	 */
	public static void illegalInput(String illegalName) {
		JOptionPane.showMessageDialog(null, illegalName + "Ϊ���Ϸ�������,�����¼����ٴ�����");
	}
	/**
	 * �����������ǲ��ǷǸ���ֵ
	 * @param money
	 * @return
	 */
	public static boolean isPositiveNumber(String money, String tips) {
		try{
			System.out.println(money);
			int tempM = Integer.parseInt(money.trim());
			if(tempM > 0)
				return true;
			else
				return false;
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, tips + "�������˷ǰ�������ֵ����");
		}
		return false;
	}
}
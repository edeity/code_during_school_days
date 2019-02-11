package net.edeity.demo.hms.appearance;

import javax.swing.JOptionPane;

/**
 * HotelUtil
 * 该类统一 一些常用的代码,出现参见错误的处理方法
 * 该类很多方式都应该重写,以提高容错率
 * @author Javer
 *
 */
public class HU {
	public static void main(String[] args) {
//		isNotNull(null,"身份证");
//		illegalInput("身份证");
//		isPositiveNumber("啊", "身份证");
		seriousError("账单类型");
	}
	/**
	 * 输入的数据必须为空的动作
	 * @param str 检测输入的数据
	 * @param message 提示的信息
	 * @return
	 */
	public static boolean isNotNull(String str, String tips){
		if(str == null || str.trim().equals("")) {
			JOptionPane.showMessageDialog(null, tips + "不可为空");
			return false;
		}
		return true;
	}
	/**
	 * 出现严重错误时的动作
	 */
	public static void seriousError(String errorName) {
		JOptionPane.showMessageDialog(null, errorName +"出现了错误,请重启,若仍然出现错误,请联系软件维护人员");
	}
	
	/**
	 * 输入不合法时的动作
	 */
	public static void illegalInput(String illegalName) {
		JOptionPane.showMessageDialog(null, illegalName + "为不合法的输入,请重新检查后再次输入");
	}
	/**
	 * 检验输入项是不是非负数值
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
			JOptionPane.showMessageDialog(null, tips + "栏输入了非阿拉伯数值的项");
		}
		return false;
	}
}
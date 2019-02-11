package net.edeity.demo.bigdatacalculator;

/**
 * 以固定的格式记录大数据
 * @author Javer
 */
public class BigNumber {
	char sign_bit;//符号位
	String intergers = "";//整数部分
	String fraction = "";//小数部分
	BigNumber(final String num) {
		sign_bit = num.charAt(0);
		String[] temp_num = num.substring(1).split("\\.");//正则表达式,以小数点分割数
		intergers =temp_num[0];
		if(temp_num.length != 1)//假如存在小数部分
			fraction = temp_num[1];
	}
	void print() {
		System.out.println(sign_bit + "   " + intergers + "   " + fraction );
	}
	public static void main(String[] args) {
		BigNumber bn = new BigNumber("+12342541.1324125");
		bn.print();
	}
}

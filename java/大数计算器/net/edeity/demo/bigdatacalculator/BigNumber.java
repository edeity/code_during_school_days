package net.edeity.demo.bigdatacalculator;

/**
 * �Թ̶��ĸ�ʽ��¼������
 * @author Javer
 */
public class BigNumber {
	char sign_bit;//����λ
	String intergers = "";//��������
	String fraction = "";//С������
	BigNumber(final String num) {
		sign_bit = num.charAt(0);
		String[] temp_num = num.substring(1).split("\\.");//������ʽ,��С����ָ���
		intergers =temp_num[0];
		if(temp_num.length != 1)//�������С������
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

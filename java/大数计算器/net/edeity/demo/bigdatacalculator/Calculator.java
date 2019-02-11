package net.edeity.demo.bigdatacalculator;


//ĿǰString��ʾ�Ķ�������������,���Ҳ������㿪ͷ
public class Calculator {
	//int:-2147483648   ��2147483648(10λ)
	public static void main(String[] args) {
		Calculator nc = new Calculator();
//		System.out.println(nc.complemanate("1545456466464623", "1748974641"));
//		System.out.println(nc.multiply("6", "7"));
	}
	/**
	 * ���ַ���ת��Ϊint����,���Ϊ8λ
	 * @param str
	 * @return
	 */
	private int[] strToArray(String str) {
		int[] array;
		if(!str.equals("")) {						//str����Ϊ��
			int str_length = str.length();
			int array_length;//����ĳ���
			if(str_length % 8 != 0)	//δ������ 8,����9λ��,Ӧ���� 9/8 + 1�洢
				array_length = str_length / 8 + 1;
			else									//������8,����16λ,��16 / 8 = 2 ���ɴ洢
				array_length = str_length / 8; 
			array = new int[array_length];
			for(int i = array_length - 1; i >0; i--) {
				int begin_index = str_length - (array_length - i) * 8;
				int end_index = str_length -(array_length -  i - 1)* 8;
				array[i] = Integer.parseInt(str.substring(begin_index, end_index));
			}
			int end_index = str_length - (array_length - 1 ) * 8;
			array[0] = Integer.parseInt(str.substring(0 , end_index));
			return array;
		} else {											//strΪ�յ�ʱ��
			array = new int[1];
			return array;
		}
	}
	/**
	 * ������ת��Ϊ�ַ���
	 * @param array
	 * @return
	 */
	private String arrayToStr(int[] array) {
		int array_length = array.length;
		String result = "", temp = "";
		int temp_length = 0;
		result += array[0];
		for(int i=1; i<array_length; i++) {
			temp = String.valueOf(array[i]);
			//���м�int����ʱ����Ҫ����,��ԭ��������100111111�����������0����������1111111
			temp_length = temp.length();
			for(int j= temp_length; j<8; j++)  {
				temp = "0" + temp;
			}
			
			result += temp;
		}
		return result;
	}
	/**
	 * �Ƚ����������Ĵ�С, if a==b ,return -1; if a > b, return 1; if a < b, return 0;
	 * @param a
	 * @param b
	 */
	public int isMax(String a, String b) {
		int[] num1 = strToArray(a);
		int[] num2 = strToArray(b);
		if(num1.length > num2.length)
			return 1;
		else if(num1.length < num2.length)
			return -1;
		else  {// num1.length == num2.length
			for(int i=0; i< num1.length; i++) {
				if(num1[i] > num2[i])
					return 1;
				else if(num1[i] < num2[i])
					return -1;
			}
			return 0;// num1 == num2
		}
	}
	/**
	 * �������ַ�����ʾ�Ĵ��������,�����ַ�����ʽ�������ǵĺ�
	 * @param a
	 * @param b
	 * @return
	 */
	public String add(String a, String b) {
		int[] num1 = strToArray(a);
		int[] num2 = strToArray(b);
		int num1_index, num2_index, result_index;//�����ڼ䱣������ֵ�����
		int temp_num1 = 0, temp_num2 = 0;//��ʱ�����������
		int result_length = Math.max(num1.length, num2.length);
		int[] result = new int[result_length];//���������
		boolean isCarry = false;//��λ��־
		
		//����ȡ��ÿ��int����������м�
		for(int i = 0; i< result_length; i++) {
			num1_index = num1.length - i - 1;
			num2_index = num2.length - i - 1;
			result_index = result_length - i - 1;
			if(num1_index >=0)		
				temp_num1 = num1[num1_index];
			else		
				temp_num1 = 0;
			if(num2_index >=0)		
				temp_num2 = num2[num2_index];
			else 		
				temp_num2 = 0;
			if(isCarry) {
				result[result_index] = temp_num1 + temp_num2 + 1;
				isCarry = false;
			}
			else
				result[result_index] = temp_num1 + temp_num2;
			
			//�ж��Ƿ��λ
			if(result[result_index] >= 100000000) {
				isCarry = true;
				result[result_index] -= 100000000;
			} else continue;
		}
		//ת�����,������
		String str_result = arrayToStr(result);
		if(isCarry) str_result = "1" + str_result;//���8nβ����Ӻ���8n+1�Ӷ�ԭ�����鲻���ռ�洢������
		return str_result;
	}
	/**
	 * �����������,����������ַ�����ʽ����
	 * @param a
	 * @param b
	 * @return
	 */
	public String minus(String a, String b) {
		if(isMax(a, b) == 0) { // a==b
			return "0";
		}
		else if(isMax(a, b) == -1)  {// a<b
			return "-" + specialMinus(b, a);
		}
		else 	 {//	a > b
			return specialMinus(a, b);
		}
	}
	/**
	 * �ϴ�����ȥ��С��
	 * @param max_a	�ϴ���
	 * @param min_b	��С��
	 * @return
	 */
	private String specialMinus(String max_a, String min_b) {
		int[] max_num = strToArray(max_a);
		int[] min_num = strToArray(min_b);
		int temp_num1 = 0, temp_num2 = 0;
		int result_length = max_num.length;
		int[] result = new int[result_length];//���������
		boolean isCarry = false;//��λ��־
		
		//����ȡ��ÿ��int����������мӼ�, ����С�ļ�λ������
		for(int i=0; i < max_num.length; i++) {
			int max_index = max_num.length - i - 1;//�����������м����λ
			int min_index = min_num.length - i - 1;//����������м����λ
			temp_num1 = max_num[max_index];
			if(i < min_num.length)
				temp_num2 = min_num[min_index];
			else 
				temp_num2 = 0;
			result[max_index] = temp_num1 - temp_num2;
			if(isCarry) {//�Ƿ���֮ǰ��Ҫ��λ
				result[max_index] -= 1;
				isCarry = false;
			}
			
			//�ж��Ƿ��λ
			if(result[max_index] < 0) {
				isCarry = true;
				result[max_index] += 100000000;//��λ�ɹ�
			} else continue;
		}
		//ת�����,������
		String str_result = arrayToStr(result);
		//�޲���Ϊ��8n+1λ���8nλ�������ַ���ǰ�������Ľ��
		if(str_result.charAt(0) == '0')
			str_result = str_result.substring(1);
		return str_result;
	}
	/**
	 * �����������
	 * @param a
	 * @param b
	 * @return
	 */
	public String multiply(String a, String b) {
		String result = "0";
		for(int i = b.length(); i>0; i--) {
			// result + "0"��Ϊ�˽�λ�� b.charAt(i-1) - 48��Ϊ�˰�charתΪ��ֵ
			result = add(result, specialMultiply(a, b.charAt(i-1) - 48));
			a = a + "0";//�ݴ�ļ�����+��0�����൱�ڳ�10�� �����´ν�λ�ļӷ�
			}
		return result;
	}
	/**
	 * ������һλ���ĳ˷�
	 * @return
	 */
	public String specialMultiply(String a, int single_num) {
		String result = "0";
		for(;single_num>0; single_num--) {
			result = add(result, a);
		}
		return result;
	}
	/**
	 * �����ĳ���
	 * @param a
	 * @param b
	 * @return
	 */
	public String divide(String a, String b) {
		int compare = isMax(a, b);
		if(compare == -1) { //a<b
			return "0";
		}else if(compare == 0) {//a = b
			return "1";
		}else {//a > b
			int a_length = a.length();//��������λ��
			int end_index = b.length();//������λ��
			int quotient_num = 0;//�ݴ�һλ��
			String quotient = "";//�����̵Ľ��
			String dividend = a.substring(0, end_index  - 1);//�տ�ʼȡ���ĳ���
			while(end_index <= a_length) {//aû�б�����
				dividend += a.charAt(end_index - 1);//�Ӷ�һλ
				compare = isMax(dividend, b);
				while(compare == 1 || compare == 0) {//dividend > b(==1) �� dividend == b(== 0)
					dividend = specialMinus(dividend, b);
					quotient_num++;
					compare = isMax(dividend, b);
				}
				end_index++;
				quotient+=quotient_num;//���õ���һλ�̱������ַ�����
				quotient_num = 0;//���¿�ʼ
			}
			return quotient;
		}
	}
	
	/**
	 * ��������
	 */
	public String complemanate(String a, String b) {
		int compare = isMax(a, b);
		if(compare == -1) {
			return  a;
		}else if(compare == 0) {
			return "0";
		}else {
			int a_length = a.length();
			int end_index = b.length();
			String dividend = a.substring(0, end_index - 1);
			while(end_index <= a_length) {
				dividend += a.charAt(end_index - 1);
				compare = isMax(dividend, b);
				while(compare == 1 || compare == 0) {
					dividend = specialMinus(dividend, b);
					compare = isMax(dividend, b);
				}
				end_index ++;
			}
			if(dividend.equals(""))//�պó���
				return "0";
			else
				return dividend;
		}
	}
}
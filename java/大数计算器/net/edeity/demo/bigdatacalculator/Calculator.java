package net.edeity.demo.bigdatacalculator;


//目前String表示的都是正的正整数,并且不是以零开头
public class Calculator {
	//int:-2147483648   到2147483648(10位)
	public static void main(String[] args) {
		Calculator nc = new Calculator();
//		System.out.println(nc.complemanate("1545456466464623", "1748974641"));
//		System.out.println(nc.multiply("6", "7"));
	}
	/**
	 * 将字符串转化为int数组,最大为8位
	 * @param str
	 * @return
	 */
	private int[] strToArray(String str) {
		int[] array;
		if(!str.equals("")) {						//str不能为空
			int str_length = str.length();
			int array_length;//数组的长度
			if(str_length % 8 != 0)	//未能整除 8,比如9位数,应该用 9/8 + 1存储
				array_length = str_length / 8 + 1;
			else									//能整除8,比如16位,用16 / 8 = 2 即可存储
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
		} else {											//str为空的时候
			array = new int[1];
			return array;
		}
	}
	/**
	 * 将数组转化为字符串
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
			//当中间int有零时，需要补充,即原本可能是100111111，如果不补充0，结果将变成1111111
			temp_length = temp.length();
			for(int j= temp_length; j<8; j++)  {
				temp = "0" + temp;
			}
			
			result += temp;
		}
		return result;
	}
	/**
	 * 比较两个大数的大小, if a==b ,return -1; if a > b, return 1; if a < b, return 0;
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
	 * 两个用字符串表示的大数据相加,并以字符串形式返回它们的和
	 * @param a
	 * @param b
	 * @return
	 */
	public String add(String a, String b) {
		int[] num1 = strToArray(a);
		int[] num2 = strToArray(b);
		int num1_index, num2_index, result_index;//运算期间保存的数字的索引
		int temp_num1 = 0, temp_num2 = 0;//暂时参与运算的数
		int result_length = Math.max(num1.length, num2.length);
		int[] result = new int[result_length];//保留结果集
		boolean isCarry = false;//进位标志
		
		//依次取出每个int保存的数进行加
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
			
			//判断是否进位
			if(result[result_index] >= 100000000) {
				isCarry = true;
				result[result_index] -= 100000000;
			} else continue;
		}
		//转化结果,并返回
		String str_result = arrayToStr(result);
		if(isCarry) str_result = "1" + str_result;//解决8n尾数相加后变成8n+1从而原本数组不够空间存储的问题
		return str_result;
	}
	/**
	 * 两个大数相减,并将结果以字符串形式返回
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
	 * 较大数减去较小数
	 * @param max_a	较大数
	 * @param min_b	较小数
	 * @return
	 */
	private String specialMinus(String max_a, String min_b) {
		int[] max_num = strToArray(max_a);
		int[] min_num = strToArray(min_b);
		int temp_num1 = 0, temp_num2 = 0;
		int result_length = max_num.length;
		int[] result = new int[result_length];//保留结果集
		boolean isCarry = false;//借位标志
		
		//依次取出每个int保存的数进行加减, 从最小的几位计算起
		for(int i=0; i < max_num.length; i++) {
			int max_index = max_num.length - i - 1;//代表被减数进行计算的位
			int min_index = min_num.length - i - 1;//代表减数进行计算的位
			temp_num1 = max_num[max_index];
			if(i < min_num.length)
				temp_num2 = min_num[min_index];
			else 
				temp_num2 = 0;
			result[max_index] = temp_num1 - temp_num2;
			if(isCarry) {//是否在之前需要借位
				result[max_index] -= 1;
				isCarry = false;
			}
			
			//判断是否进位
			if(result[max_index] < 0) {
				isCarry = true;
				result[max_index] += 100000000;//借位成功
			} else continue;
		}
		//转化结果,并返回
		String str_result = arrayToStr(result);
		//修补因为从8n+1位变成8n位，导致字符串前面出现零的结果
		if(str_result.charAt(0) == '0')
			str_result = str_result.substring(1);
		return str_result;
	}
	/**
	 * 两个大数相乘
	 * @param a
	 * @param b
	 * @return
	 */
	public String multiply(String a, String b) {
		String result = "0";
		for(int i = b.length(); i>0; i--) {
			// result + "0"是为了进位， b.charAt(i-1) - 48是为了把char转为数值
			result = add(result, specialMultiply(a, b.charAt(i-1) - 48));
			a = a + "0";//暂存的加数，+“0”，相当于乘10， 方便下次进位的加法
			}
		return result;
	}
	/**
	 * 大数和一位数的乘法
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
	 * 大数的除法
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
			int a_length = a.length();//被除数的位数
			int end_index = b.length();//除数的位数
			int quotient_num = 0;//暂存一位商
			String quotient = "";//保存商的结果
			String dividend = a.substring(0, end_index  - 1);//刚开始取到的除数
			while(end_index <= a_length) {//a没有被除进
				dividend += a.charAt(end_index - 1);//加多一位
				compare = isMax(dividend, b);
				while(compare == 1 || compare == 0) {//dividend > b(==1) 或 dividend == b(== 0)
					dividend = specialMinus(dividend, b);
					quotient_num++;
					compare = isMax(dividend, b);
				}
				end_index++;
				quotient+=quotient_num;//将得到的一位商保存在字符串中
				quotient_num = 0;//重新开始
			}
			return quotient;
		}
	}
	
	/**
	 * 大数求余
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
			if(dividend.equals(""))//刚好除尽
				return "0";
			else
				return dividend;
		}
	}
}
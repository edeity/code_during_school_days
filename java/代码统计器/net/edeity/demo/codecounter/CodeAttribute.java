package net.edeity.demo.codecounter;
/**
 * 因为反射类的不成功，所以以这类来对属性进行统计
 * 之所以不在MyCodecount里进行，一方面是为了减少这类对其他类的依赖性，
 * 另一方面，好吧，我承认，MyCodeCount的算法够麻烦的了
 * @author javer
 */
public class CodeAttribute
{
	private static int classNumber;
	private static int enumNumber;
	private static int interfaceNumber;
	private static int staticNumber;
	
	private static String selectWord;
	private static int aimNumber;
	
	private static boolean startFindEndComment;
	/**
	 * 与其他类的接口，控制计算各种属性的方法
	 */
	public static  void parse(String line)
	{
		if(line.length() != 0)//去掉空白行
		{	
			if(startFindEndComment == false)
			{
				int effectivedIndex = getCommentIndex(line) - 1;//注释前的有效代码索引
				if(effectivedIndex != -2 && effectivedIndex != -1)//-2 代表没有找到注释符号， -1代表注释符号出现在开头, 0代表仅仅是没实际意义的代码
				{
System.out.println(effectivedIndex);
//System.out.println("这是有效代码");
					String effectivedLine = line.substring(0, effectivedIndex + 1);//老是忘记substring的endIndex 是传进去的index -1
					countAimWordNumber(effectivedLine, "abstract");
					countClassNumber(effectivedLine);
					countEnumNumber(effectivedLine);
					countInterfaceNumber(effectivedLine);
					countStaticNumber(effectivedLine);
				}
			}
			else
			{
//System.out.println("开始寻找结尾！");
				int endCommentIndex = getCommentEndIndex(line);
				if(endCommentIndex != -1)//找到了 *（和谐）/
				{
					startFindEndComment = false;//不再需要执行找*（和谐）/工作
					parse(line.substring(endCommentIndex + 1, line.length()));
				}
			}
		}
	}
	
	/**
	 * 通过获得注释代码出现的索引值间接获得有效代码
	 */
	private static int getCommentIndex(String line)
	{
		int lineIndex = line.indexOf("//");
		int otherIndex = line.indexOf("/*");
		
		
		if(lineIndex == -1 && otherIndex == -1)//两者都没有找到
		{ 
//System.out.println("这行没找到注释符号");
			return line.length();
		}
		else if(lineIndex > otherIndex)		//  反斜杠可能起到作用
		{
//System.out.println("这行出现了注释符号//");
			int endIndex = 0;
			if((endIndex = getStringEndIndex(line, lineIndex)) != -1)//出现在字符串内
			{
//System.out.println("注释出现字符串中");
				return getCommentIndex(line.substring((endIndex + 1), line.length()));//继续找有效的注释
			}
			else{
				return lineIndex;
				}//不出现在字符串内，返回//的注释Index
		}
		else			//	OtherIndex > lineIndex	blockComment或doccomment可能起到作用
		{
//System.out.println("这行出现了注释符号/*");
			int endIndex = 0;
			if((endIndex = getStringEndIndex(line, lineIndex)) != -1)//出现在字符串内
			{
//System.out.println("注释出现字符串中");
				return getCommentIndex(line.substring(endIndex, line.length()));
			}
			else//没有出现在字符串中，开始寻找结尾
			{
//System.out.println("开始寻找结尾！");
				startFindEndComment = true;//开始找*（和谐）/工作
				if(isSurplusCode(line, endIndex))//还有代码
				{
//System.out.println("在本行中找*（和谐）/");
					int endCommentIndex = line.indexOf("*/");
					if(endCommentIndex != -1 && endCommentIndex > otherIndex)//在剩余行中找到，并且在/*的后面
					{
//System.out.println("endCommentIndex  : "+ endCommentIndex);
//System.out.println("在本行中找到*（和谐）/");
						startFindEndComment = false;//寻找*（和谐）/工作已经结束
						if(isSurplusCode(line, endCommentIndex))
							parse(line.substring((endCommentIndex + 1), line.length()));
					}
				}
			}
			return otherIndex;
		}
			
	}
	
	/**
	 * 这个方法是为了寻找 *（和谐）/  即使这个出现在字符串中也无所谓，因为那样在源代码就会造成错误
	 * @param line
	 */
	private static int getCommentEndIndex(String remainLine)
	{
		int endCommentIndex = remainLine.indexOf("*/");
		if(endCommentIndex == -1)//证明这行没有*/
		{
			return -1;
		}
		else//证明这行包括*/	
		{
			startFindEndComment = true;
//System.out.println("找到结尾了");
			return endCommentIndex;
		}
	}
	
	/**
	 * 判断特殊符号是否在String里面
	 * -1表示特殊符号不出现在String里面
	 * @return 
	 */
	private static int getStringEndIndex(String line, int index)//line是指要判断的那一行， index指出现特殊符号 的位置,
	{
		String str = "\"\"";
		int firstIndex = line.indexOf(str);
		if(firstIndex != -1)//证明这行有String类型出现
		{
			int secondIndex = line.indexOf(str, firstIndex);//firstIndex开始，但不包括它
			if(firstIndex < index && index < secondIndex)//当符号出现在两个双引号中间，就是表示属于字符串的一部分
			{
				return secondIndex;
			}//如果在两个双引号之间，就是string的一部分
			else	//因为index > secondIndex 故一定还有代码      继续寻找是否在下一个String里面
			{
				return getStringEndIndex(line.substring(secondIndex + 1), index);//递归寻找，直到没有找到字符串或index在字符串里面位置
			}
		}
		else { return -1;}//这行没有String类型出现
	}
	
	/**
	 * 检查是否还有剩余代码
	 * @param Line
	 * @param index
	 * @return
	 */
	private static boolean isSurplusCode(String line, int endIndex)//endIndex : 出现指定字符的索引值
	{
		if((endIndex + 1) > (line.length() - 1))
		{
//System.out.println("后面不再更有代码");
			return false;
		}
		else
			return true;
	}
	
	/**
	 * 寻找目标字符的数量
	 * @param effectivedLine
	 */
	public static void countAimWordNumber(String effectivedLine, String aimWord)
	{
//System.out.println(effectivedLine);
		selectWord = aimWord;
		String str = aimWord;
		int index = effectivedLine.indexOf(str);
		if(index != -1)//证明找到了目标字符
		{
			int stringEndIndex = getStringEndIndex(effectivedLine, index);
			if(stringEndIndex ==  -1)//证明目标字符没有出现在字符内
			{
				if(isEffectivedWord(effectivedLine, index, str))
				{
					aimNumber++;
//System.out.println("" + str + "  :  "  +  aimNumber);
				}
				if(isSurplusCode(effectivedLine, index + aimWord.length()))//假如后面还有代码，接着寻找
				{
					countAimWordNumber(effectivedLine.substring(index + aimWord.length(), effectivedLine.length()), aimWord);
				}
				else{ return;} //
			}
			else//目标字符出现在字符串内，继续寻找
			{
//System.out.println(aimWord + "出现在字符串中");
				if(isSurplusCode(effectivedLine, stringEndIndex + 1))//假如后面还有代码，接着寻找
				{
					countAimWordNumber(effectivedLine.substring(stringEndIndex + 1, effectivedLine.length()), aimWord);
				}
				else{  return;}
			}
		}
	}
	
	/**
	 *  行数212到339的代码，都可以参考countAimWordNumber()
	 * 	只是为了能够定位到 “静态变量”才添加下面的方法，代码会优化
	 * @param effectivedLine
	 */
	private static  void countClassNumber(String effectivedLine)
	{
		String str = "class";
		int index = effectivedLine.indexOf(str);
		if(index != -1)//证明找到了目标字符
		{
			int stringEndIndex = getStringEndIndex(effectivedLine, index);
			if(stringEndIndex ==  -1)//证明目标字符没有出现在字符内
			{
				if(isEffectivedWord(effectivedLine, index, str))
				{
					classNumber++;
//System.out.println("" + str + "  :  "  +  classNumber);
				}
				if(isSurplusCode(effectivedLine, index + str.length()))//假如后面还有代码，接着寻找
				{
					countClassNumber(effectivedLine.substring(index + str.length(), effectivedLine.length()));
				}
				else{ return;} //
			}
			else//目标字符出现在字符串内，继续寻找
			{
//System.out.println(str+ "出现在字符串中");
				if(isSurplusCode(effectivedLine, stringEndIndex + 1))//假如后面还有代码，接着寻找
				{
					countClassNumber(effectivedLine.substring(stringEndIndex + 1, effectivedLine.length()));
				}
				else{  return;}
			}
		}
	}
	private static void countEnumNumber(String effectivedLine)
	{
	String str = "enum";
	int index = effectivedLine.indexOf(str);
	if(index != -1)//证明找到了目标字符
	{
		int stringEndIndex = getStringEndIndex(effectivedLine, index);
		if(stringEndIndex ==  -1)//证明目标字符没有出现在字符内
		{
			if(isEffectivedWord(effectivedLine, index, str))
			{
				enumNumber++;
//System.out.println("" + str + "  :  "  +  enumNumber);
			}
			if(isSurplusCode(effectivedLine, index + str.length()))//假如后面还有代码，接着寻找
			{
				countClassNumber(effectivedLine.substring(index + str.length(), effectivedLine.length()));
			}
			else{ return;} 
		}
		else//目标字符出现在字符串内，继续寻找
		{
//System.out.println(str+ "出现在字符串中");
			if(isSurplusCode(effectivedLine, stringEndIndex + 1))//假如后面还有代码，接着寻找
			{
				countEnumNumber(effectivedLine.substring(stringEndIndex + 1, effectivedLine.length()));
			}
			else{  return;}
		}
	}
}
	private static void countInterfaceNumber(String effectivedLine)
	{
		String str = "interface";
		int index = effectivedLine.indexOf(str);
		if(index != -1)//证明找到了目标字符
		{
			int stringEndIndex = getStringEndIndex(effectivedLine, index);
			if(stringEndIndex ==  -1)//证明目标字符没有出现在字符内
			{
				if(isEffectivedWord(effectivedLine, index, str))
				{
					interfaceNumber++;
//System.out.println("" + str + "  :  "  +  interfaceNumber);
				}
				if(isSurplusCode(effectivedLine, index + str.length()))//假如后面还有代码，接着寻找
				{
					countClassNumber(effectivedLine.substring(index + str.length(), effectivedLine.length()));
				}
				else{ return;} 
			}
			else//目标字符出现在字符串内，继续寻找
			{
//System.out.println(str+ "出现在字符串中");
				if(isSurplusCode(effectivedLine, stringEndIndex + 1))//假如后面还有代码，接着寻找
				{
					countInterfaceNumber(effectivedLine.substring(stringEndIndex + 1, effectivedLine.length()));
				}
				else{  return;}
			}
		}
	}
	private static void countStaticNumber(String effectivedLine)
	{
		String str = "static";
		int index = effectivedLine.indexOf(str);
		if(index != -1)//证明找到了目标字符
		{
			int stringEndIndex = getStringEndIndex(effectivedLine, index);
			if(stringEndIndex ==  -1)//证明目标字符没有出现在字符内
			{
				if(isEffectivedWord(effectivedLine, index, str))
				{
					staticNumber++;
//System.out.println("" + str + "  :  "  +  staticNumber);
				}
				if(isSurplusCode(effectivedLine, index + str.length()))//假如后面还有代码，接着寻找
				{
					countStaticNumber(effectivedLine.substring(index + str.length(), effectivedLine.length()));
				}
				else{ return;}
			}
			else//目标字符出现在字符串内，继续寻找
			{
//System.out.println(str+ "出现在字符串中");
				if(isSurplusCode(effectivedLine, stringEndIndex + 1))//假如后面还有代码，接着寻找
				{
					countClassNumber(effectivedLine.substring(stringEndIndex + 1, effectivedLine.length()));
				}
				else{  return;}
			}
		}
	}
	
	/**
	 * 判断目标字符时有效字符，而不是某个字符串的一部分
	 * @param effectivedLine
	 * @param strIndex
	 * @param aimWord
	 * @return
	 */
	private static boolean isEffectivedWord(String effectivedLine, int strIndex, String aimWord)
	{
		int foreIndex = strIndex -1;//字符串前一个字符
		int laterIndex = strIndex + aimWord.length();//字符串后一个字符
		if(foreIndex != -1)//证明关键字符不在开头
		{
			if(effectivedLine.charAt(foreIndex) == ' ' &&  effectivedLine.charAt(laterIndex) == ' ')//当且仅当两个为空时才是关键字
			{
				return true;
			}
			else { return false;}
		}
		else
		{
			return true;//关键字在开头
		}
	}
	
	/**
	 * 初始化所有数据，恢复其默认值
	 */
	void initializeAll()
	{
		classNumber = 0;
		enumNumber = 0;
		interfaceNumber = 0;
		staticNumber = 0;
		
		aimNumber = 0;
		
		startFindEndComment = false;
	}
	
	public String toString()
	{
		String result = "classNumber : " + classNumber;
		result += "\n" + "enumNumber : " + enumNumber;
		result += "\n" + "interfaceNumber : " + interfaceNumber;
		result += "\n" + "staticNumber : " + staticNumber;
		result += "\n" + selectWord + " : " + aimNumber;
		return result;
	}
}
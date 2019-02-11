package net.edeity.demo.codecounter;
import java.io.*;
import java.util.regex.*;

/**
 * 计算有效代码，各种注释代码，空白行行数
 * @author javer
 *
 */
public class CodeComment
{
	private static int whiteLine;
	private static int commentWhiteLine;
	private static int effectiveCode;
	private static int docComment;
	private static int blockComment;
	private static int lineComment;
	
	private boolean startFindBlockEnd;
	private boolean startFindCommentEnd;
	
	private static boolean sameDocLine;//一下布尔判断是在后期修复一项多个comment各式各样加在一起引起的BUG，以后再优化
	private static boolean sameLine;
	private static boolean sameCommentLine;
	private static boolean sameCommentWhiteLine;
	private static boolean sameBlockLine;
	private static boolean sameWhiteLine;
	private static boolean sameLineLine;
	
	/**
	 * 分析代码
	 * @param Line
	 */
	void parse(String line)
	{
		if(line.length() == 0)//计算空白代码行
		{
			increaseWhiteLine();
System.out.println("whiteLine : " + whiteLine);
		}
		if(startFindCommentEnd == true)//寻*/工作开始
		{
			if(line.length() == 0){ commentWhiteLine++;}											
			findCommentEnd(line);
		}
		else//不执行寻找*/的工作
		{
			int index = line.indexOf('/');//开始寻找/工作，这是每一个重复步骤的最开始那步
			if(index == -1)//证明没找到了
				{
					if(line.length() != 0)
					{
						increaseEffectiveCode();
System.out.println("effectiveCode part 0: " + effectiveCode);
					return;
					}
				}
			else //证明找到了特殊符号 /
			{
System.out.println("找到了/");
				if(isBetweenChar(line, index))//特殊符号 /在Char中
				{
System.out.println("特殊符号出现在char中");
					parse(line.substring(index + 2, line.length() -1));
					return;
				}
				else//特殊符号 /不在Char中
				{
					if(index != 0)//证明注释不是在代码的开头
						{
							increaseEffectiveCode();
System.out.println("effectiveCode part 1: " + effectiveCode);
						int secondIndex;
						if((secondIndex = getStringEndIndex(line, index)) != -1)//假如出现在双引号间，即属于String的一部分，跳过判断，继续从secondIndex + 1开始寻找特殊符号
							{
System.out.println("特殊符号出现在String里面");
								parse(line.substring(secondIndex + 1));
								return;//这行已经结束
							}
						}
					char c = line.charAt(index + 1);//也不出现在字符串中
					if(c == '/')//剩余的都是行注释了
							{
								increaseLineComment();
System.out.println("lineComment part 0: " + lineComment);
						return;
					}
					else if(c == '*')
					{
						if(isSurplusCode(line, index+1) == false)//检验/*后面是否还有源代码
						{
							//后面没有源代码
									increaseBlockComment();
System.out.println("blockComment part 0: " + blockComment);
							startFindBlockEnd = true;//这个boolean值是为了区分寻*/方法中得到的是block注释还是doc注释，假如这个为true，是blockComment,否则，是docComment
							startFindCommentEnd = true;//开始寻*/工作
							return;
						}
						else // /*后面还有代码
						{
							char c1;
							if((c1 = line.charAt(index + 2)) == '*')//代表这行是doc注释
										{
System.out.println("这是doc注释");
									if(isSurplusCode(line, index+2) == false)//检验/**后面是否还有源代码
											{
												increaseDocComment();
System.out.println("docComment part 0 : " + docComment);
										startFindCommentEnd = true;//开始寻*/工作
										return;
									}
									else//假如剩余还有代码，就在剩余代码中执行寻 */ 方法
									{
										startFindCommentEnd = true;
										String remainLine = line.substring(index + 3, line.length());
										findCommentEnd(remainLine);
									}
								}
							else//有剩余代码，但剩余代码中没有*,证明着确实是block注释，执行寻*/方法
							{
								String remainLine = line.substring(index + 3, line.length());
								int blockCommentEndIndex = remainLine.indexOf("*/");
								if(blockCommentEndIndex == -1) //在本行中没有找到*（）/
								{
									startFindBlockEnd = true;
									startFindCommentEnd = true;
								}//这条看起来有点无厘头的代码是为了修正同一行正出现blockComment注释而误认为是docComment的BUG,即/**/
								else//在本行已经找到*（）/
								{
									startFindBlockEnd = false;
									startFindCommentEnd = false;
								}
							}
						}
					}
					else
					{
						System.out.println("假如你看到了这段文字可能你在代码中加入了/这个字符（比如html），或是其他情况，这个会在下一个版本修正");
					}
				}
			}
		}
	}
	
	/**
	 * 这个方法是为了寻找 *（和谐）/  即使这个出现在字符串中也无所谓，因为那样在源代码就会造成错误
	 * @param line
	 */
	private void findCommentEnd(String remainLine)
	{
System.out.println("开始寻找*/");
		int nextIndex = remainLine.indexOf("*/");
System.out.println(nextIndex);
System.out.println(remainLine);
			if(nextIndex == -1)//证明这行没有*/
			{
System.out.println("没有找到！");
				if(startFindBlockEnd == true)//与blockComment关联
				{
					increaseBlockComment ();
System.out.println("blockComment part 1: " + blockComment);
				}
				else //与docComment关联
				{
					increaseDocComment();
System.out.println("docComment  part 1: " + docComment );
				}//开始下一行的寻*/工作
			}
			else//证明这行包括*/	
			{
System.out.println("已经找到了*/");
				startFindCommentEnd = false;//寻*/工作已经结束
				if(startFindBlockEnd == true)
				{
					increaseBlockComment();
					startFindBlockEnd = false;//判断已完毕
System.out.println("blockComment part 2: " + blockComment);
				}
				else
				{
					increaseDocComment();
System.out.println("docComment part 2: " + docComment);
				}
				if(isSurplusCode(remainLine, nextIndex+1) == false)//检验*/后面是否还有源代码，true代表没有
				{
					return;
				}
				else//*/后面还有剩余代码
				{
					String reRemainLine = remainLine.substring(nextIndex+2, remainLine.length()-1);
					parse(reRemainLine);//开始下一个周期的工作
				}
			}
	}
	
	/**
	 * docComment++
	 */
	private static void increaseDocComment()
	{
		if(sameDocLine == false)
		{
			docComment ++;
			sameDocLine = true;
		}
	}
	
	/**
	 * commentWhiteLine++
	 */
	private static void increaseCommentWhiteLine()
	{
		if(sameCommentWhiteLine == false)
		{
			commentWhiteLine++;
			sameCommentWhiteLine = true;
		}
	}
	
	/**
	 * effectiveCode++
	 */
	private static void increaseEffectiveCode()
	{
		if(sameLine == false)
		{
			effectiveCode++;
			sameLine = true;
		}
	}
	
	/**
	 * blockComment++
	 */
	private static void increaseBlockComment()
	{
		if(sameBlockLine == false)
		{
			blockComment++;
			sameBlockLine = true;
		}
	}
	
	/**
	 * whiteLine++
	 */
	private static void increaseWhiteLine()
	{
		if(sameWhiteLine == false)
		{
			whiteLine++;
			sameWhiteLine = true;
		}
	}
	
	/**
	 * lineComment++
	 */
	private static void increaseLineComment()
	{
		if(sameLineLine == false)
		{
			lineComment++;
			sameLineLine = true;
		}
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
System.out.println("后面不再更有代码");
			return false;
		}
		else
			return true;
	}
	
	/**
	 * 判断特殊符号是否在String里面，返回的是第二个双引号的索引值，找不到双引号，返回-1
	 * @return 
	 */
	private static int getStringEndIndex(String line, int index)//line是指要判断的那一行， index指出现特殊符号 "/" 或 "*"的位置
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
	 * 判断特殊符号是否在char里面
	 * @return 
	 */
	private static boolean isBetweenChar(String line, int index)//line是指要判断的那一行， index指出现特殊符号 "/" 或 "*"的位置
	{
		String cha = "\'";
		int firstCharIndex = index - 1;
		int secondCharIndex = index + 1;
		if(firstCharIndex >= 0 && secondCharIndex < line.length())
		{
//System.out.println("here");
			if(line.charAt(firstCharIndex) == '\'' && line.charAt(secondCharIndex) == '\'')
			{
//System.out.println("there");
				return true;
			}
			else { return false;}
		}
		else{ return false;}
		
	}
	
	/**
	 * 修复同一行出现多次各种各样comment或是代码的BUG
	 * @param sameLine
	 */
	void initializeSameLine()
	{
		sameDocLine = false;
		sameLine = false;
		sameCommentLine = false;
		sameCommentWhiteLine = false;
		sameBlockLine = false;
		sameWhiteLine = false;
		sameLineLine = false; 
	}
	
	/**
	 * 执行初始化化工作，恢复其默认值
	 */
	void initializeAll()
	{
		whiteLine =  0;
		commentWhiteLine = 0;
		effectiveCode = 0;
		docComment = 0;
		blockComment = 0;
		lineComment = 0;
		
		startFindBlockEnd = false;
		startFindCommentEnd = false;
		
		sameDocLine = false;
		sameLine = false;
		sameCommentLine = false;
		sameCommentWhiteLine = false;
		sameBlockLine = false;
		sameWhiteLine = false;
		sameLineLine = false;
	}
	/**
	 * print
	 */
	public String toString()
	{
		String result = "effectiveCodeLine : " + effectiveCode;
		result += "\n" + "docCommentLine : " + docComment;
		result += "\n" + "blockCommentLine : " + blockComment;
		result += "\n" + "LineCommentLine : " + lineComment;
		result += "\n" + "whiteLine : " + whiteLine;
		result += "\n" + "commentWhiteLine : " + commentWhiteLine;
		return result;
	}
}

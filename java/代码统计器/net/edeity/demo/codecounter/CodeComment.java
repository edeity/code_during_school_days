package net.edeity.demo.codecounter;
import java.io.*;
import java.util.regex.*;

/**
 * ������Ч���룬����ע�ʹ��룬�հ�������
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
	
	private static boolean sameDocLine;//һ�²����ж����ں����޸�һ����comment��ʽ��������һ�������BUG���Ժ����Ż�
	private static boolean sameLine;
	private static boolean sameCommentLine;
	private static boolean sameCommentWhiteLine;
	private static boolean sameBlockLine;
	private static boolean sameWhiteLine;
	private static boolean sameLineLine;
	
	/**
	 * ��������
	 * @param Line
	 */
	void parse(String line)
	{
		if(line.length() == 0)//����հ״�����
		{
			increaseWhiteLine();
System.out.println("whiteLine : " + whiteLine);
		}
		if(startFindCommentEnd == true)//Ѱ*/������ʼ
		{
			if(line.length() == 0){ commentWhiteLine++;}											
			findCommentEnd(line);
		}
		else//��ִ��Ѱ��*/�Ĺ���
		{
			int index = line.indexOf('/');//��ʼѰ��/����������ÿһ���ظ�������ʼ�ǲ�
			if(index == -1)//֤��û�ҵ���
				{
					if(line.length() != 0)
					{
						increaseEffectiveCode();
System.out.println("effectiveCode part 0: " + effectiveCode);
					return;
					}
				}
			else //֤���ҵ���������� /
			{
System.out.println("�ҵ���/");
				if(isBetweenChar(line, index))//������� /��Char��
				{
System.out.println("������ų�����char��");
					parse(line.substring(index + 2, line.length() -1));
					return;
				}
				else//������� /����Char��
				{
					if(index != 0)//֤��ע�Ͳ����ڴ���Ŀ�ͷ
						{
							increaseEffectiveCode();
System.out.println("effectiveCode part 1: " + effectiveCode);
						int secondIndex;
						if((secondIndex = getStringEndIndex(line, index)) != -1)//���������˫���ż䣬������String��һ���֣������жϣ�������secondIndex + 1��ʼѰ���������
							{
System.out.println("������ų�����String����");
								parse(line.substring(secondIndex + 1));
								return;//�����Ѿ�����
							}
						}
					char c = line.charAt(index + 1);//Ҳ���������ַ�����
					if(c == '/')//ʣ��Ķ�����ע����
							{
								increaseLineComment();
System.out.println("lineComment part 0: " + lineComment);
						return;
					}
					else if(c == '*')
					{
						if(isSurplusCode(line, index+1) == false)//����/*�����Ƿ���Դ����
						{
							//����û��Դ����
									increaseBlockComment();
System.out.println("blockComment part 0: " + blockComment);
							startFindBlockEnd = true;//���booleanֵ��Ϊ������Ѱ*/�����еõ�����blockע�ͻ���docע�ͣ��������Ϊtrue����blockComment,������docComment
							startFindCommentEnd = true;//��ʼѰ*/����
							return;
						}
						else // /*���滹�д���
						{
							char c1;
							if((c1 = line.charAt(index + 2)) == '*')//����������docע��
										{
System.out.println("����docע��");
									if(isSurplusCode(line, index+2) == false)//����/**�����Ƿ���Դ����
											{
												increaseDocComment();
System.out.println("docComment part 0 : " + docComment);
										startFindCommentEnd = true;//��ʼѰ*/����
										return;
									}
									else//����ʣ�໹�д��룬����ʣ�������ִ��Ѱ */ ����
									{
										startFindCommentEnd = true;
										String remainLine = line.substring(index + 3, line.length());
										findCommentEnd(remainLine);
									}
								}
							else//��ʣ����룬��ʣ�������û��*,֤����ȷʵ��blockע�ͣ�ִ��Ѱ*/����
							{
								String remainLine = line.substring(index + 3, line.length());
								int blockCommentEndIndex = remainLine.indexOf("*/");
								if(blockCommentEndIndex == -1) //�ڱ�����û���ҵ�*����/
								{
									startFindBlockEnd = true;
									startFindCommentEnd = true;
								}//�����������е�����ͷ�Ĵ�����Ϊ������ͬһ��������blockCommentע�Ͷ�����Ϊ��docComment��BUG,��/**/
								else//�ڱ����Ѿ��ҵ�*����/
								{
									startFindBlockEnd = false;
									startFindCommentEnd = false;
								}
							}
						}
					}
					else
					{
						System.out.println("�����㿴����������ֿ������ڴ����м�����/����ַ�������html��������������������������һ���汾����");
					}
				}
			}
		}
	}
	
	/**
	 * ���������Ϊ��Ѱ�� *����г��/  ��ʹ����������ַ�����Ҳ����ν����Ϊ������Դ����ͻ���ɴ���
	 * @param line
	 */
	private void findCommentEnd(String remainLine)
	{
System.out.println("��ʼѰ��*/");
		int nextIndex = remainLine.indexOf("*/");
System.out.println(nextIndex);
System.out.println(remainLine);
			if(nextIndex == -1)//֤������û��*/
			{
System.out.println("û���ҵ���");
				if(startFindBlockEnd == true)//��blockComment����
				{
					increaseBlockComment ();
System.out.println("blockComment part 1: " + blockComment);
				}
				else //��docComment����
				{
					increaseDocComment();
System.out.println("docComment  part 1: " + docComment );
				}//��ʼ��һ�е�Ѱ*/����
			}
			else//֤�����а���*/	
			{
System.out.println("�Ѿ��ҵ���*/");
				startFindCommentEnd = false;//Ѱ*/�����Ѿ�����
				if(startFindBlockEnd == true)
				{
					increaseBlockComment();
					startFindBlockEnd = false;//�ж������
System.out.println("blockComment part 2: " + blockComment);
				}
				else
				{
					increaseDocComment();
System.out.println("docComment part 2: " + docComment);
				}
				if(isSurplusCode(remainLine, nextIndex+1) == false)//����*/�����Ƿ���Դ���룬true����û��
				{
					return;
				}
				else//*/���滹��ʣ�����
				{
					String reRemainLine = remainLine.substring(nextIndex+2, remainLine.length()-1);
					parse(reRemainLine);//��ʼ��һ�����ڵĹ���
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
	 * ����Ƿ���ʣ�����
	 * @param Line
	 * @param index
	 * @return
	 */
	private static boolean isSurplusCode(String line, int endIndex)//endIndex : ����ָ���ַ�������ֵ
	{
		if((endIndex + 1) > (line.length() - 1))
		{
System.out.println("���治�ٸ��д���");
			return false;
		}
		else
			return true;
	}
	
	/**
	 * �ж���������Ƿ���String���棬���ص��ǵڶ���˫���ŵ�����ֵ���Ҳ���˫���ţ�����-1
	 * @return 
	 */
	private static int getStringEndIndex(String line, int index)//line��ָҪ�жϵ���һ�У� indexָ����������� "/" �� "*"��λ��
	{
		String str = "\"\"";
		int firstIndex = line.indexOf(str);
		if(firstIndex != -1)//֤��������String���ͳ���
		{
			int secondIndex = line.indexOf(str, firstIndex);//firstIndex��ʼ������������
			if(firstIndex < index && index < secondIndex)//�����ų���������˫�����м䣬���Ǳ�ʾ�����ַ�����һ����
			{
				return secondIndex;
			}//���������˫����֮�䣬����string��һ����
			else	//��Ϊindex > secondIndex ��һ�����д���      ����Ѱ���Ƿ�����һ��String����
			{
				return getStringEndIndex(line.substring(secondIndex + 1), index);//�ݹ�Ѱ�ң�ֱ��û���ҵ��ַ�����index���ַ�������λ��
			}
		}
		else { return -1;}//����û��String���ͳ���
	}
	
	/**
	 * �ж���������Ƿ���char����
	 * @return 
	 */
	private static boolean isBetweenChar(String line, int index)//line��ָҪ�жϵ���һ�У� indexָ����������� "/" �� "*"��λ��
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
	 * �޸�ͬһ�г��ֶ�θ��ָ���comment���Ǵ����BUG
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
	 * ִ�г�ʼ�����������ָ���Ĭ��ֵ
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

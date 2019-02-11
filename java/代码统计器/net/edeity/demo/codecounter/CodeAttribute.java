package net.edeity.demo.codecounter;
/**
 * ��Ϊ������Ĳ��ɹ��������������������Խ���ͳ��
 * ֮���Բ���MyCodecount����У�һ������Ϊ�˼��������������������ԣ�
 * ��һ���棬�ðɣ��ҳ��ϣ�MyCodeCount���㷨���鷳����
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
	 * ��������Ľӿڣ����Ƽ���������Եķ���
	 */
	public static  void parse(String line)
	{
		if(line.length() != 0)//ȥ���հ���
		{	
			if(startFindEndComment == false)
			{
				int effectivedIndex = getCommentIndex(line) - 1;//ע��ǰ����Ч��������
				if(effectivedIndex != -2 && effectivedIndex != -1)//-2 ����û���ҵ�ע�ͷ��ţ� -1����ע�ͷ��ų����ڿ�ͷ, 0���������ûʵ������Ĵ���
				{
System.out.println(effectivedIndex);
//System.out.println("������Ч����");
					String effectivedLine = line.substring(0, effectivedIndex + 1);//��������substring��endIndex �Ǵ���ȥ��index -1
					countAimWordNumber(effectivedLine, "abstract");
					countClassNumber(effectivedLine);
					countEnumNumber(effectivedLine);
					countInterfaceNumber(effectivedLine);
					countStaticNumber(effectivedLine);
				}
			}
			else
			{
//System.out.println("��ʼѰ�ҽ�β��");
				int endCommentIndex = getCommentEndIndex(line);
				if(endCommentIndex != -1)//�ҵ��� *����г��/
				{
					startFindEndComment = false;//������Ҫִ����*����г��/����
					parse(line.substring(endCommentIndex + 1, line.length()));
				}
			}
		}
	}
	
	/**
	 * ͨ�����ע�ʹ�����ֵ�����ֵ��ӻ����Ч����
	 */
	private static int getCommentIndex(String line)
	{
		int lineIndex = line.indexOf("//");
		int otherIndex = line.indexOf("/*");
		
		
		if(lineIndex == -1 && otherIndex == -1)//���߶�û���ҵ�
		{ 
//System.out.println("����û�ҵ�ע�ͷ���");
			return line.length();
		}
		else if(lineIndex > otherIndex)		//  ��б�ܿ���������
		{
//System.out.println("���г�����ע�ͷ���//");
			int endIndex = 0;
			if((endIndex = getStringEndIndex(line, lineIndex)) != -1)//�������ַ�����
			{
//System.out.println("ע�ͳ����ַ�����");
				return getCommentIndex(line.substring((endIndex + 1), line.length()));//��������Ч��ע��
			}
			else{
				return lineIndex;
				}//���������ַ����ڣ�����//��ע��Index
		}
		else			//	OtherIndex > lineIndex	blockComment��doccomment����������
		{
//System.out.println("���г�����ע�ͷ���/*");
			int endIndex = 0;
			if((endIndex = getStringEndIndex(line, lineIndex)) != -1)//�������ַ�����
			{
//System.out.println("ע�ͳ����ַ�����");
				return getCommentIndex(line.substring(endIndex, line.length()));
			}
			else//û�г������ַ����У���ʼѰ�ҽ�β
			{
//System.out.println("��ʼѰ�ҽ�β��");
				startFindEndComment = true;//��ʼ��*����г��/����
				if(isSurplusCode(line, endIndex))//���д���
				{
//System.out.println("�ڱ�������*����г��/");
					int endCommentIndex = line.indexOf("*/");
					if(endCommentIndex != -1 && endCommentIndex > otherIndex)//��ʣ�������ҵ���������/*�ĺ���
					{
//System.out.println("endCommentIndex  : "+ endCommentIndex);
//System.out.println("�ڱ������ҵ�*����г��/");
						startFindEndComment = false;//Ѱ��*����г��/�����Ѿ�����
						if(isSurplusCode(line, endCommentIndex))
							parse(line.substring((endCommentIndex + 1), line.length()));
					}
				}
			}
			return otherIndex;
		}
			
	}
	
	/**
	 * ���������Ϊ��Ѱ�� *����г��/  ��ʹ����������ַ�����Ҳ����ν����Ϊ������Դ����ͻ���ɴ���
	 * @param line
	 */
	private static int getCommentEndIndex(String remainLine)
	{
		int endCommentIndex = remainLine.indexOf("*/");
		if(endCommentIndex == -1)//֤������û��*/
		{
			return -1;
		}
		else//֤�����а���*/	
		{
			startFindEndComment = true;
//System.out.println("�ҵ���β��");
			return endCommentIndex;
		}
	}
	
	/**
	 * �ж���������Ƿ���String����
	 * -1��ʾ������Ų�������String����
	 * @return 
	 */
	private static int getStringEndIndex(String line, int index)//line��ָҪ�жϵ���һ�У� indexָ����������� ��λ��,
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
	 * ����Ƿ���ʣ�����
	 * @param Line
	 * @param index
	 * @return
	 */
	private static boolean isSurplusCode(String line, int endIndex)//endIndex : ����ָ���ַ�������ֵ
	{
		if((endIndex + 1) > (line.length() - 1))
		{
//System.out.println("���治�ٸ��д���");
			return false;
		}
		else
			return true;
	}
	
	/**
	 * Ѱ��Ŀ���ַ�������
	 * @param effectivedLine
	 */
	public static void countAimWordNumber(String effectivedLine, String aimWord)
	{
//System.out.println(effectivedLine);
		selectWord = aimWord;
		String str = aimWord;
		int index = effectivedLine.indexOf(str);
		if(index != -1)//֤���ҵ���Ŀ���ַ�
		{
			int stringEndIndex = getStringEndIndex(effectivedLine, index);
			if(stringEndIndex ==  -1)//֤��Ŀ���ַ�û�г������ַ���
			{
				if(isEffectivedWord(effectivedLine, index, str))
				{
					aimNumber++;
//System.out.println("" + str + "  :  "  +  aimNumber);
				}
				if(isSurplusCode(effectivedLine, index + aimWord.length()))//������滹�д��룬����Ѱ��
				{
					countAimWordNumber(effectivedLine.substring(index + aimWord.length(), effectivedLine.length()), aimWord);
				}
				else{ return;} //
			}
			else//Ŀ���ַ��������ַ����ڣ�����Ѱ��
			{
//System.out.println(aimWord + "�������ַ�����");
				if(isSurplusCode(effectivedLine, stringEndIndex + 1))//������滹�д��룬����Ѱ��
				{
					countAimWordNumber(effectivedLine.substring(stringEndIndex + 1, effectivedLine.length()), aimWord);
				}
				else{  return;}
			}
		}
	}
	
	/**
	 *  ����212��339�Ĵ��룬�����Բο�countAimWordNumber()
	 * 	ֻ��Ϊ���ܹ���λ�� ����̬���������������ķ�����������Ż�
	 * @param effectivedLine
	 */
	private static  void countClassNumber(String effectivedLine)
	{
		String str = "class";
		int index = effectivedLine.indexOf(str);
		if(index != -1)//֤���ҵ���Ŀ���ַ�
		{
			int stringEndIndex = getStringEndIndex(effectivedLine, index);
			if(stringEndIndex ==  -1)//֤��Ŀ���ַ�û�г������ַ���
			{
				if(isEffectivedWord(effectivedLine, index, str))
				{
					classNumber++;
//System.out.println("" + str + "  :  "  +  classNumber);
				}
				if(isSurplusCode(effectivedLine, index + str.length()))//������滹�д��룬����Ѱ��
				{
					countClassNumber(effectivedLine.substring(index + str.length(), effectivedLine.length()));
				}
				else{ return;} //
			}
			else//Ŀ���ַ��������ַ����ڣ�����Ѱ��
			{
//System.out.println(str+ "�������ַ�����");
				if(isSurplusCode(effectivedLine, stringEndIndex + 1))//������滹�д��룬����Ѱ��
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
	if(index != -1)//֤���ҵ���Ŀ���ַ�
	{
		int stringEndIndex = getStringEndIndex(effectivedLine, index);
		if(stringEndIndex ==  -1)//֤��Ŀ���ַ�û�г������ַ���
		{
			if(isEffectivedWord(effectivedLine, index, str))
			{
				enumNumber++;
//System.out.println("" + str + "  :  "  +  enumNumber);
			}
			if(isSurplusCode(effectivedLine, index + str.length()))//������滹�д��룬����Ѱ��
			{
				countClassNumber(effectivedLine.substring(index + str.length(), effectivedLine.length()));
			}
			else{ return;} 
		}
		else//Ŀ���ַ��������ַ����ڣ�����Ѱ��
		{
//System.out.println(str+ "�������ַ�����");
			if(isSurplusCode(effectivedLine, stringEndIndex + 1))//������滹�д��룬����Ѱ��
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
		if(index != -1)//֤���ҵ���Ŀ���ַ�
		{
			int stringEndIndex = getStringEndIndex(effectivedLine, index);
			if(stringEndIndex ==  -1)//֤��Ŀ���ַ�û�г������ַ���
			{
				if(isEffectivedWord(effectivedLine, index, str))
				{
					interfaceNumber++;
//System.out.println("" + str + "  :  "  +  interfaceNumber);
				}
				if(isSurplusCode(effectivedLine, index + str.length()))//������滹�д��룬����Ѱ��
				{
					countClassNumber(effectivedLine.substring(index + str.length(), effectivedLine.length()));
				}
				else{ return;} 
			}
			else//Ŀ���ַ��������ַ����ڣ�����Ѱ��
			{
//System.out.println(str+ "�������ַ�����");
				if(isSurplusCode(effectivedLine, stringEndIndex + 1))//������滹�д��룬����Ѱ��
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
		if(index != -1)//֤���ҵ���Ŀ���ַ�
		{
			int stringEndIndex = getStringEndIndex(effectivedLine, index);
			if(stringEndIndex ==  -1)//֤��Ŀ���ַ�û�г������ַ���
			{
				if(isEffectivedWord(effectivedLine, index, str))
				{
					staticNumber++;
//System.out.println("" + str + "  :  "  +  staticNumber);
				}
				if(isSurplusCode(effectivedLine, index + str.length()))//������滹�д��룬����Ѱ��
				{
					countStaticNumber(effectivedLine.substring(index + str.length(), effectivedLine.length()));
				}
				else{ return;}
			}
			else//Ŀ���ַ��������ַ����ڣ�����Ѱ��
			{
//System.out.println(str+ "�������ַ�����");
				if(isSurplusCode(effectivedLine, stringEndIndex + 1))//������滹�д��룬����Ѱ��
				{
					countClassNumber(effectivedLine.substring(stringEndIndex + 1, effectivedLine.length()));
				}
				else{  return;}
			}
		}
	}
	
	/**
	 * �ж�Ŀ���ַ�ʱ��Ч�ַ���������ĳ���ַ�����һ����
	 * @param effectivedLine
	 * @param strIndex
	 * @param aimWord
	 * @return
	 */
	private static boolean isEffectivedWord(String effectivedLine, int strIndex, String aimWord)
	{
		int foreIndex = strIndex -1;//�ַ���ǰһ���ַ�
		int laterIndex = strIndex + aimWord.length();//�ַ�����һ���ַ�
		if(foreIndex != -1)//֤���ؼ��ַ����ڿ�ͷ
		{
			if(effectivedLine.charAt(foreIndex) == ' ' &&  effectivedLine.charAt(laterIndex) == ' ')//���ҽ�������Ϊ��ʱ���ǹؼ���
			{
				return true;
			}
			else { return false;}
		}
		else
		{
			return true;//�ؼ����ڿ�ͷ
		}
	}
	
	/**
	 * ��ʼ���������ݣ��ָ���Ĭ��ֵ
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
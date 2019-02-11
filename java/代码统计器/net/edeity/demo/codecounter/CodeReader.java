package net.edeity.demo.codecounter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * ��ȡ�ļ��������������������
 * @author javer
 */
public class CodeReader
{
	private FileReader myFileReader;
	private BufferedReader myBufferedReader;
	
	private static int totalLine = 1;//����ÿ�е�����
	private CodeComment codeCount;
	private CodeAttribute codeAttribute;
	
	CodeReader()
	{
		codeCount = new CodeComment();
		codeAttribute = new CodeAttribute();
	}
	/**
	 * �����������ӣ����ҿ��Ʒ������̵ķ���
	 * @param dirOrFileName
	 */
	public void codeReadAndAnalyze(File dirOrFileName)
	{
		if(dirOrFileName.isDirectory()){	readDirectory(dirOrFileName);}
		else if(dirOrFileName.isFile()) {	readFile(dirOrFileName);}
	}
	
	/**
	 * ͨ���ݹ��ȡ�ļ�����������ļ�
	 * @param directory
	 */
	private void readDirectory(File directory)
	{
		File[] dirOrFile = directory.listFiles();
		//directory.length()��ʾ�����ļ��ĳ��ȣ����ֽ�Ϊ��λ
		for(int i=0; i<dirOrFile.length; i++)
		{
			if(dirOrFile[i].isDirectory())
			{
				readDirectory(dirOrFile[i]);
			}
			else if(dirOrFile[i].getName().endsWith("java"))
			{
				readFile(dirOrFile[i]);
				System.out.println(dirOrFile[i].getName());
			}
		}
	}
	
	/**
	 * ��ȡ�ļ�   ����ǵ��ڼ���
	 * @param file
	 */
	private void readFile(File file)
	{

			try {
				myFileReader = new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			myBufferedReader = new BufferedReader(myFileReader);
			String line;
				try {
					while((line = myBufferedReader.readLine()) != null)
					{
System.out.println("���ڶ�����������	" + totalLine + "��" );
						String analyzeLine = line.trim();
						codeCount.parse(analyzeLine);
						codeCount.initializeSameLine();//û��һ�У��������sameLine�ı�false,��ʾ������һ�У�����Ϊ���޸�ͬ�г��ָ��ָ���ע�͵�BUG
						codeAttribute.parse(analyzeLine);																							//
						
						totalLine++;
//System.out.println("�Ѿ������������˵�	" + totalLine + "��" );
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * ��ʼ���������ݣ��ظ���Ĭ��ֵ
	 */
	void initializeAll()
	{
		myFileReader = null;
		myBufferedReader = null;
		
		totalLine = 1;
		codeCount .initializeAll();
		codeAttribute.initializeAll();
	}
	
	public String toString()
	{	String result = "" + totalLine;
		result += "\n" + codeCount.toString();
		result += "\n" + codeAttribute.toString();
		return result;
	}
	 
}

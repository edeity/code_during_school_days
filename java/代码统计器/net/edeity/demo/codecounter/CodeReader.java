package net.edeity.demo.codecounter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 读取文件并交给其他分析类分析
 * @author javer
 */
public class CodeReader
{
	private FileReader myFileReader;
	private BufferedReader myBufferedReader;
	
	private static int totalLine = 1;//计算每行的数量
	private CodeComment codeCount;
	private CodeAttribute codeAttribute;
	
	CodeReader()
	{
		codeCount = new CodeComment();
		codeAttribute = new CodeAttribute();
	}
	/**
	 * 与其他类连接，并且控制方法流程的方法
	 * @param dirOrFileName
	 */
	public void codeReadAndAnalyze(File dirOrFileName)
	{
		if(dirOrFileName.isDirectory()){	readDirectory(dirOrFileName);}
		else if(dirOrFileName.isFile()) {	readFile(dirOrFileName);}
	}
	
	/**
	 * 通过递归读取文件夹里的所有文件
	 * @param directory
	 */
	private void readDirectory(File directory)
	{
		File[] dirOrFile = directory.listFiles();
		//directory.length()表示的是文件的长度，以字节为单位
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
	 * 读取文件   并标记到第几行
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
System.out.println("正在读到并分析第	" + totalLine + "行" );
						String analyzeLine = line.trim();
						codeCount.parse(analyzeLine);
						codeCount.initializeSameLine();//没换一行，把里面的sameLine改变false,表示不再是一行，这是为了修复同行出现各种各样注释的BUG
						codeAttribute.parse(analyzeLine);																							//
						
						totalLine++;
//System.out.println("已经读到并分析了第	" + totalLine + "行" );
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * 初始化所有数据，回复其默认值
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

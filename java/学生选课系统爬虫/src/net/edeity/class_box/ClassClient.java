package net.edeity.class_box;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <p>ͨ����<strong>����</strong>���ɽ����ѧѧ���ĸ�����Ϣ.
 * ÿ�඼��һ��ʵ��,������һ��ѧ������Ϣ, �ɶ�ε���login��Ϣ,�����ú�ɾ��ԭ��ѧ���������Ϣ.
 * ��Ϊ�෽��Ҫ���ʽ�����վ,���Բ����ܼ�ʱ��������,Ӧʹ�ö��߳��Ա��ⲻ��Ҫ�ĵȴ�</p>
 * <p>��õ�������Ϣ������ͷ������ : Ϊ���е�ȫ�ֱ���</p>
 * <p>������ֻ��public������ֱ�ӵ���,���������޸�</p>
 * ���༫�п�����Ϊ:
 * <ol>
 * 		<li>���񴦵���վip��ַ���ӵ�ַ(·��)�ı��</li>
 * 		<li>��վԴ����ı��</li>
 * </ol>
 * ����������ȷ�Ľ��
 * @author javer
 */
public class ClassClient {
	//��¼���������
	private final String loginUrl = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.login";
	private Document htmlDoc = null; // ����JSOUP�����html�ĵ�
	private boolean isLogging;//�Ƿ��Ѿ���¼
	private boolean isInfoFormatted;
	private boolean isSemesterPointFormatted;
	private boolean isClassFormatted;
	private String useCookie = ""; // ��¼���cookie
	private String myStudentID;//ѧ��ѧ��
	private int schoolYear; //��ѧ���
	private MyInformation myInfo;//ѧ����Ϣ
	private ArrayList<MyClass> classList; //�γ���ʾ
	//��1~��8ѧ�ڵĳɼ��б�
	private ArrayList<MyPoint> firstSemesterList;
	private ArrayList<MyPoint>secondSemesterList;
	private ArrayList<MyPoint> thridSemesterList;
	private ArrayList<MyPoint> forthSemesterList;
	private ArrayList<MyPoint> fifthSemesterList;
	private ArrayList<MyPoint> sixthSemesterList;
	private ArrayList<MyPoint> seventhSemesterList;
	private ArrayList<MyPoint> eighthSemesterList;
	/**
	 * ����
	 */
	public static void main(String[] args) throws WithoutLoggingException {
		test();
	}
	public static void test() throws WithoutLoggingException {
//		long startTime = System.currentTimeMillis();
		ClassClient cf = new ClassClient();
		cf.login("201200301102", "as1993915");
//		cf.getCurrentGrade();
		System.out.println(cf.getSemesterList(3));
//		System.out.println(cf.getClassList());
//		System.out.println(cf.getUserInfo());
//		System.out.println(cf.getSemesterList(2));
//		System.out.println(cf.getUserInfo());
//		System.out.println(cf.myInfo);
//		System.out.println(cf.getSemesterList(1));
//		cf.getPointList();
//		long endTime = System.currentTimeMillis();
//		System.out.println("pass : " + (endTime - startTime));  // ƽ����ʱ����1��
	}
	/**
	 * <p>�û���¼,��ͬһʵ���������ε���login()����ʱ,
	 * ���۵�¼�ɹ����,��������(ɾ��)ǰһ�ε�½����Ϣ</p>
	 * <p>���뱣����һѧ������Ϣ,������Դ��ͻ,Ӧʵ�������ClassCLient��</p>
	 * @param myStudentID
	 *            �û���
	 * @param passWord
	 *            �˺�����
	 * @return boolean	ȷ���Ƿ��¼�ɹ�
	 */
	public boolean login(final String myStudentID, final String password) {
		if(isLogging)
			// ɾ����һ��ѧ������Ϣ
			reset();	
		//���͵�¼��Ϣ
		GetPostUtil.sendPost(loginUrl, "stuid=" + myStudentID + "&pwd=" + password);
		//��õ�¼���cookie,�����¼���ɹ�,���ؽ��Ϊnull
		useCookie = GetPostUtil.getCookie();
		if( useCookie == null)
			isLogging = false;
		else {
			this.myStudentID = myStudentID;
			this.schoolYear = Integer.parseInt(myStudentID.substring(0, 4));
			isLogging = true;
		}
		return isLogging;
	}
	/**
	 * @return 	MyInformation		���ط�װ�û���Ϣ����
	 * @throws WithoutLoggingException 
	 */
	public MyInformation getUserInfo() throws WithoutLoggingException {
		if(isLogging) {
			this.formatteUserInfo();
			return myInfo;
		} else {
			throw new WithoutLoggingException();
		}
	}
	/**
	 * @return		 ArrayList<MyPoint>��Ҫ�󷵻�classList
	 * @throws WithoutLoggingException 
	 */
	public ArrayList<MyClass> getClassList() throws WithoutLoggingException {
		if(isLogging) {
			this.formatteClassList();
			return classList;
		} else {
				throw new WithoutLoggingException();
		}
	}
	/**
	 * @param int semester  �������صڼ�ѧ�ڵĳɼ� 
	 * eg: semester = 1 �����ص�һ��ѧ�ڵĳɼ�
	 */
	public ArrayList<MyPoint> getSemesterList(int semester) throws WithoutLoggingException {
		if(isLogging) {
			this.formatteSemesterPointList();
			switch(semester) {
				case 1: return firstSemesterList;
				case 2: return secondSemesterList;
				case 3: return thridSemesterList;
				case 4: return forthSemesterList;
				case 5: return fifthSemesterList;
				case 6: return sixthSemesterList;
				case 7: return seventhSemesterList;
				case 8: return eighthSemesterList;
				default :
					System.out.println("wrong param in getSemesterList(int semester)");
					return null;
			}
		}
		throw new WithoutLoggingException();
	}
	/**
	 * 
	 */
	public void getCurrentGrade() {
		if(!isInfoFormatted) {
			String tempHtml = GetPostUtil.sendGet(loginUrl, null	, useCookie);
			htmlDoc = Jsoup.parse(tempHtml);
			Elements currentGrades = htmlDoc.getElementsByTag("table").get(3).getElementsByTag("tr");
			for(Element currentGrade : currentGrades) {
				System.out.println(currentGrade.text());
			}
		}
	}
	/**
	 * ÿ�ε��õ�½����������(��ͬ��ɾ��)ǰһ�ε���Ϣ(��ΪĬ��ֵ),Ĭ������ȫ�����
	 */
	private void reset() {
		isLogging = false;
		isInfoFormatted = false;
		isSemesterPointFormatted = false;
		isClassFormatted = false;
		useCookie = "";
		myStudentID = "";
		myInfo = null;
		classList = null;
		firstSemesterList = null;
		secondSemesterList = null;
		thridSemesterList = null;
		forthSemesterList = null;
		fifthSemesterList = null;
		sixthSemesterList = null;
		seventhSemesterList = null;
		eighthSemesterList = null;
	}
	/**
	 * ����û��ļ򵥵���Ϣ
	 */
	private void formatteUserInfo() {
		if(!isInfoFormatted) {
			String loginUrl = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.loginmessage";
			String tempHtml = GetPostUtil.sendGet(loginUrl, null	, useCookie);
			htmlDoc = Jsoup.parse(tempHtml);
			String myInformation = htmlDoc.select("span").first().text();
			String[] informations = myInformation.split(" ");//�Կո�Ϊ���
			myInfo = new MyInformation();
			myInfo.setMyAcademy(informations[0]);
			myInfo.setMySpecialty(informations[1]);
			int tempIndex = informations[2].indexOf("(");
			myInfo.setMyName(informations[2].substring(0, tempIndex));
			myInfo.setMyStudentID(myStudentID);
			formatteeAllAveGrade();
			isInfoFormatted = true;
		}
		//����ÿѧ��ɼ�����
//		System.out.println(tempInfo);
	}
	private void formatteSemesterPointList() {
		if(!isSemesterPointFormatted) {
			//ʵ���������б�
			firstSemesterList = new ArrayList<MyPoint>();
			secondSemesterList = new ArrayList<MyPoint>();
			thridSemesterList = new ArrayList<MyPoint>();
			forthSemesterList = new ArrayList<MyPoint>();
			fifthSemesterList = new ArrayList<MyPoint>();
			sixthSemesterList = new ArrayList<MyPoint>();
			seventhSemesterList = new ArrayList<MyPoint>();
			eighthSemesterList = new ArrayList<MyPoint>();
			String tempHtml = GetPostUtil.sendGet(
					"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bkscjcx.yxkc", null, useCookie);
			htmlDoc = Jsoup.parse(tempHtml);
			Elements compulsoryClasses = htmlDoc.select("table").get(3).select("p");//3�Ǳ��޵ı�
			Elements limitedClasses = htmlDoc.select("table").get(4).select("td");//4����ѡ
			Elements electiveClasses = htmlDoc.select("table").get(5).select("td");//5��ѡ��
			this.formatteCompulsoryList(compulsoryClasses, "����");
			this.formattePointList(limitedClasses, "��ѡ");
			this.formattePointList(electiveClasses, "ѡ��");
			isSemesterPointFormatted = true;
		}
	}
	/**
	 * �����һ�������·������»��Ժ�,Ӧ�ø��ĸó����semester>2��ֵ
	 * �����޿ε��ַ�����ΪMyPoint
	 * @param elements
	 * @param classAttitude
	 */
	private void formatteCompulsoryList(Elements elements, String classAttitude) {
		int column = 6;
		int comSize = elements.size();
//����ѭ����column��,��Ҫ�Ǻ��Ե�һ��������Ϣ
		for(int i = column; i < comSize; i+=column ) {
			MyPoint tempPoint = new MyPoint();
			tempPoint.setClassAttitude(classAttitude);
			tempPoint.setClassName(elements.get(i + 1).text());
			tempPoint.setClassPoint(Float.parseFloat(elements.get(i + 3).text()));
			String tempDate = elements.get(i + 4).text();
			int tempClassYear = Integer.parseInt(tempDate.substring(0, 4));
			tempPoint.setClassYear(tempClassYear);
			int tempMouth = Integer.parseInt(tempDate.substring(5, 6));
			int semester = 0;
			if(tempMouth > 2)
				semester = 1;//����ڶ���ѧ��
			tempPoint.setSemester(semester);
			tempPoint.setClassGrade(elements.get(i + 5).text());
			this.addSemesterList(tempPoint, tempClassYear, semester);
			//���֮���Բ���StringתΪint��ֵ,��Ҫ����Ϊ������ȷǷ����ɷ�
		}
	}
	/**
	 * �����һ�������·������»��Ժ�,Ӧ�ø��ĸó����semester>2��ֵ
	 * ���Ǳ��޿ε��ַ�����ΪMyPoint
	 * @param elements
	 * @param classAttitude
	 */
	private void formattePointList(Elements elements, String classAttitude) {
		int column = 6;
		int comSize = elements.size();
//����ѭ����column��,��Ҫ�Ǻ��Ե�һ��������Ϣ
		for(int i = 0; i < comSize; i+=column ) {
			MyPoint tempPoint = new MyPoint();
			tempPoint.setClassAttitude(classAttitude);
			tempPoint.setClassName(elements.get(i + 1).text());
			tempPoint.setClassPoint(Float.parseFloat(elements.get(i + 3).text()));
			String tempDate = elements.get(i + 4).text();
			int tempClassYear = Integer.parseInt(tempDate.substring(0, 4));
			tempPoint.setClassYear(tempClassYear);
			int tempMouth = Integer.parseInt(tempDate.substring(5, 6));
			int semester = 0;
			if(tempMouth > 2)
				semester = 1;//����ڶ���ѧ��
			tempPoint.setSemester(semester);
			tempPoint.setClassGrade(elements.get(i + 5).text());
			this.addSemesterList(tempPoint, tempClassYear, semester);
			//���֮���Բ���StringתΪint��ֵ,��Ҫ����Ϊ������ȷǷ����ɷ�
//			System.out.println(tempPoint);
//			System.out.println(tempClassYear + " " + semester);
		}
	}
	/**�÷�������formatteSemesterPointListʹ��*/
	private void addSemesterList(MyPoint tempPoint, int presentYear, int semester) {
		int year = presentYear - schoolYear;
		if(year == 1)//��һѧ��
			if(semester == 0)//��һѧ��,����ͬ
				firstSemesterList.add(tempPoint);
			else
				secondSemesterList.add(tempPoint);
		else if(year == 2)
			if(semester == 0)
				thridSemesterList.add(tempPoint);
			else
				forthSemesterList.add(tempPoint);
		else if(year == 3)
			if(semester == 0)
				fifthSemesterList.add(tempPoint);
			else
				sixthSemesterList.add(tempPoint);
		else if(year == 4)
			if(semester == 0)
				seventhSemesterList.add(tempPoint);
			else
				eighthSemesterList.add(tempPoint);
	}
	/**
	 * �ڲ�����classList
	 * �γ����� ѡ������˵�� �γ̺� ����� �γ����� �������� �Ͽεص� �Ͽ�ʱ�� �Ͽ��ܴ�
	 */
	private void formatteClassList() {
		if(!isClassFormatted) {
			String tempHtml = GetPostUtil.sendGet(
					"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/xk.CourseView", null,useCookie);
			htmlDoc = Jsoup.parse(tempHtml);
			Elements classes = htmlDoc.select("table").last().select("p");
			classList = new ArrayList<MyClass>();
			int column = 9;
			int eleSize = classes.size();
//			long startTime = System.currentTimeMillis();
			//����ѭ����column��,��Ҫ�Ǻ��Ե�һ��������Ϣ
			for(int i = column; i < eleSize; i+=column ) {
				try {
					MyClass tempClass = new MyClass();
					String className = classes.get(i).text();
					//������ַ���������substring()��Ϊ����Ϊjsoup�������ַ���ÿ������"?"�����ַ�,������������Ҫ��
					tempClass.setClassName(className.substring(0, className.length() - 1));
					String tempClassPlace = classes.get(i + 6).text();
					tempClass.setClassPlace(tempClassPlace.substring(0, tempClassPlace.length() - 1));
					String classTime = classes.get(i + 7).text().substring(0, 3);
					tempClass.setClassDayOfWeek(Integer.parseInt(classTime.substring(0, 1)));
					tempClass.setClassDayOfTime(Integer.parseInt(classTime.substring(2, 3)));
					classList.add(tempClass);
				}catch(NumberFormatException e) {
					System.out.println(myStudentID + " : " + "��ʽת������, �Ѻ��Ըÿ�Ŀ");
					continue;//������ĳ�ָ�ʽ��������²���ȫ����ȡ
				} catch(IndexOutOfBoundsException e1) {
					System.out.println(myStudentID + " : " + "����Խ�����, �Ѻ��Ըÿ�Ŀ");
					continue;//ͬ��
				}
				isClassFormatted = true;
			}
//			long endTime = System.currentTimeMillis();
//			System.out.println("pass : " + (endTime - startTime));
		}
	}
	/**
	 * @return			 ���㲢���سɼ�����
	 * @throws WithoutLoggingException 
	 */
	private void formatteeAllAveGrade() {
		formatteSemesterPointList();
		myInfo.setFirstAveGrade(this.getFormatteAveGrade(1));
		myInfo.setSecondAveGrade(this.getFormatteAveGrade(2));
		myInfo.setThridAveGrade(this.getFormatteAveGrade(3));
		myInfo.setForthAveGrade(this.getFormatteAveGrade(4));
	}
	/**
	 * ���㼨�㲻������
	 * @param year    ��ʾ���ǵڼ�ѧ��
	 */
	private float getFormatteAveGrade(int year) {
		float weightTotalGrade = 0;//��Ȩ���ܳɼ�
		float totalPoint = 0;
		ArrayList<MyPoint> aimFirstList = null;
		ArrayList<MyPoint> aimSecondList = null;
		//�����ȷ���ǵڼ�ѧ��
		switch(year) {
			case 1:
				aimFirstList = firstSemesterList;
				aimSecondList = secondSemesterList;
				break;
			case 2:
				aimFirstList = thridSemesterList;
				aimSecondList = forthSemesterList;
				break;
			case 3:
				aimFirstList = fifthSemesterList;
				aimSecondList = sixthSemesterList;
				break;
			case 4:
				aimFirstList = seventhSemesterList;
				aimSecondList = eighthSemesterList;
				break;
		}
		//����
		for(MyPoint tempClass : aimFirstList) {
			String tempStringGrade = tempClass.getClassGrade();
			int tempIntGrade = transLevelToPoint(tempStringGrade);
			float tempIntPoint = tempClass.getClassPoint();
			weightTotalGrade += tempIntGrade * tempIntPoint;
			totalPoint += tempIntPoint;
		}
		for(MyPoint tempClass : aimSecondList) {
			String tempStringGrade = tempClass.getClassGrade();
			int tempIntGrade = transLevelToPoint(tempStringGrade);
			float tempIntPoint = tempClass.getClassPoint();
			weightTotalGrade += tempIntGrade * tempIntPoint;
			totalPoint += tempIntPoint;
		}
		if(totalPoint == 0)
			return 0;
		else 
			return weightTotalGrade / totalPoint;
	}
	/**
	 * ���ؿγ̷�����ʵ�ʶ�Ӧֵ,��(�ȼ�����) �� (��������)<br />
	 * ---------------------��Ϊ���ֱ�׼�Ĳ�ͬ,�÷����������-------------------------
	 */
	private static int transLevelToPoint(String point) {
		int formattedPoint;
		try {
			formattedPoint = Integer.parseInt(point);
			return formattedPoint;
		} catch(NumberFormatException e) {
			if(point.equals("����"))
				formattedPoint = 95;
			else if(point.equals("����"))
				formattedPoint = 85;
			else if(point.equals("�е�"))
				formattedPoint = 75;
			else if(point.equals("����"))
				formattedPoint = 65;
			else	//������
				formattedPoint = 55;
			return formattedPoint;
		}
	}
	/**
	 * û�е�¼,���¼ʧ��,���¼��Ч���ѹ�(2013��ʱ��¼��Ч��Ϊ��Сʱ)
	 *<p> ���б����κ���������,�����׳����쳣<p>
	 * @author Javer
	 */
	@SuppressWarnings("serial")
	class WithoutLoggingException extends Exception{
		WithoutLoggingException() {
			super("�ڽ��и������ǰ,���ȵ�¼,�������ѵ�¼����������˴���,������ĵ�½��Ч���ѹ�,�����µ�½");
		}
	}
}
package net.edeity.class_box;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <p>通过简单<strong>爬网</strong>获得山东大学学生的各类信息.
 * 每类都是一个实体,仅保存一个学生的信息, 可多次调用login信息,但调用后将删除原本学生保存的信息.
 * 因为类方法要访问教务处网站,所以不可能即时返回数据,应使用多线程以避免不必要的等待</p>
 * <p>获得的有用信息均在类头部声明 : 为所有的全局变量</p>
 * <p>类有且只有public方法可直接调用,请勿轻易修改</p>
 * 该类极有可能因为:
 * <ol>
 * 		<li>教务处的网站ip地址或子地址(路径)的变更</li>
 * 		<li>网站源代码的变更</li>
 * </ol>
 * 而产生不正确的结果
 * @author javer
 */
public class ClassClient {
	//登录界面的连接
	private final String loginUrl = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.login";
	private Document htmlDoc = null; // 经过JSOUP处理的html文档
	private boolean isLogging;//是否已经登录
	private boolean isInfoFormatted;
	private boolean isSemesterPointFormatted;
	private boolean isClassFormatted;
	private String useCookie = ""; // 登录后的cookie
	private String myStudentID;//学生学号
	private int schoolYear; //入学年份
	private MyInformation myInfo;//学生信息
	private ArrayList<MyClass> classList; //课程显示
	//第1~第8学期的成绩列表
	private ArrayList<MyPoint> firstSemesterList;
	private ArrayList<MyPoint>secondSemesterList;
	private ArrayList<MyPoint> thridSemesterList;
	private ArrayList<MyPoint> forthSemesterList;
	private ArrayList<MyPoint> fifthSemesterList;
	private ArrayList<MyPoint> sixthSemesterList;
	private ArrayList<MyPoint> seventhSemesterList;
	private ArrayList<MyPoint> eighthSemesterList;
	/**
	 * 测试
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
//		System.out.println("pass : " + (endTime - startTime));  // 平均用时超过1秒
	}
	/**
	 * <p>用户登录,在同一实例化对象多次调用login()方法时,
	 * 无论登录成功与否,都将重置(删除)前一次登陆的信息</p>
	 * <p>若想保留上一学生的信息,避免资源冲突,应实例化多个ClassCLient类</p>
	 * @param myStudentID
	 *            用户名
	 * @param passWord
	 *            账号密码
	 * @return boolean	确定是否登录成功
	 */
	public boolean login(final String myStudentID, final String password) {
		if(isLogging)
			// 删除上一个学生的信息
			reset();	
		//发送登录信息
		GetPostUtil.sendPost(loginUrl, "stuid=" + myStudentID + "&pwd=" + password);
		//获得登录后的cookie,如果登录不成功,返回结果为null
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
	 * @return 	MyInformation		返回封装用户信息的类
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
	 * @return		 ArrayList<MyPoint>按要求返回classList
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
	 * @param int semester  表明返回第几学期的成绩 
	 * eg: semester = 1 代表返回第一个学期的成绩
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
	 * 每次调用登陆方法都重置(等同于删除)前一次的信息(设为默认值),默认重置全剧变量
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
	 * 获得用户的简单的信息
	 */
	private void formatteUserInfo() {
		if(!isInfoFormatted) {
			String loginUrl = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.loginmessage";
			String tempHtml = GetPostUtil.sendGet(loginUrl, null	, useCookie);
			htmlDoc = Jsoup.parse(tempHtml);
			String myInformation = htmlDoc.select("span").first().text();
			String[] informations = myInformation.split(" ");//以空格为间隔
			myInfo = new MyInformation();
			myInfo.setMyAcademy(informations[0]);
			myInfo.setMySpecialty(informations[1]);
			int tempIndex = informations[2].indexOf("(");
			myInfo.setMyName(informations[2].substring(0, tempIndex));
			myInfo.setMyStudentID(myStudentID);
			formatteeAllAveGrade();
			isInfoFormatted = true;
		}
		//补充每学年成绩绩点
//		System.out.println(tempInfo);
	}
	private void formatteSemesterPointList() {
		if(!isSemesterPointFormatted) {
			//实例化所有列表
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
			Elements compulsoryClasses = htmlDoc.select("table").get(3).select("p");//3是必修的表
			Elements limitedClasses = htmlDoc.select("table").get(4).select("td");//4是限选
			Elements electiveClasses = htmlDoc.select("table").get(5).select("td");//5是选修
			this.formatteCompulsoryList(compulsoryClasses, "必修");
			this.formattePointList(limitedClasses, "限选");
			this.formattePointList(electiveClasses, "选修");
			isSemesterPointFormatted = true;
		}
	}
	/**
	 * 假如第一个考试月份在三月或以后,应该更改该程序的semester>2的值
	 * 将必修课的字符串变为MyPoint
	 * @param elements
	 * @param classAttitude
	 */
	private void formatteCompulsoryList(Elements elements, String classAttitude) {
		int column = 6;
		int comSize = elements.size();
//下面循环从column起,主要是忽略第一行无用信息
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
				semester = 1;//代表第二个学期
			tempPoint.setSemester(semester);
			tempPoint.setClassGrade(elements.get(i + 5).text());
			this.addSemesterList(tempPoint, tempClassYear, semester);
			//这个之所以不将String转为int数值,主要是因为有优秀等非分数成分
		}
	}
	/**
	 * 假如第一个考试月份在三月或以后,应该更改该程序的semester>2的值
	 * 将非必修课的字符串变为MyPoint
	 * @param elements
	 * @param classAttitude
	 */
	private void formattePointList(Elements elements, String classAttitude) {
		int column = 6;
		int comSize = elements.size();
//下面循环从column起,主要是忽略第一行无用信息
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
				semester = 1;//代表第二个学期
			tempPoint.setSemester(semester);
			tempPoint.setClassGrade(elements.get(i + 5).text());
			this.addSemesterList(tempPoint, tempClassYear, semester);
			//这个之所以不将String转为int数值,主要是因为有优秀等非分数成分
//			System.out.println(tempPoint);
//			System.out.println(tempClassYear + " " + semester);
		}
	}
	/**该方法仅供formatteSemesterPointList使用*/
	private void addSemesterList(MyPoint tempPoint, int presentYear, int semester) {
		int year = presentYear - schoolYear;
		if(year == 1)//第一学年
			if(semester == 0)//第一学期,下类同
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
	 * 内部处理classList
	 * 课程名称 选课限制说明 课程号 课序号 课程属性 考试类型 上课地点 上课时间 上课周次
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
			//下面循环从column起,主要是忽略第一行无用信息
			for(int i = column; i < eleSize; i+=column ) {
				try {
					MyClass tempClass = new MyClass();
					String className = classes.get(i).text();
					//下面各字符串均调用substring()是为了因为jsoup处理后的字符串每个都有"?"多余字符,或其他不符合要求
					tempClass.setClassName(className.substring(0, className.length() - 1));
					String tempClassPlace = classes.get(i + 6).text();
					tempClass.setClassPlace(tempClassPlace.substring(0, tempClassPlace.length() - 1));
					String classTime = classes.get(i + 7).text().substring(0, 3);
					tempClass.setClassDayOfWeek(Integer.parseInt(classTime.substring(0, 1)));
					tempClass.setClassDayOfTime(Integer.parseInt(classTime.substring(2, 3)));
					classList.add(tempClass);
				}catch(NumberFormatException e) {
					System.out.println(myStudentID + " : " + "格式转换错误, 已忽略该科目");
					continue;//避免因某种格式错误而导致不能全部读取
				} catch(IndexOutOfBoundsException e1) {
					System.out.println(myStudentID + " : " + "数组越界错误, 已忽略该科目");
					continue;//同上
				}
				isClassFormatted = true;
			}
//			long endTime = System.currentTimeMillis();
//			System.out.println("pass : " + (endTime - startTime));
		}
	}
	/**
	 * @return			 计算并返回成绩绩点
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
	 * 计算绩点不可外用
	 * @param year    表示的是第几学年
	 */
	private float getFormatteAveGrade(int year) {
		float weightTotalGrade = 0;//加权的总成绩
		float totalPoint = 0;
		ArrayList<MyPoint> aimFirstList = null;
		ArrayList<MyPoint> aimSecondList = null;
		//用年份确定是第几学期
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
		//计算
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
	 * 返回课程分数的实际对应值,即(等级评分) → (分数评分)<br />
	 * ---------------------因为评分标准的不同,该方法存在误差-------------------------
	 */
	private static int transLevelToPoint(String point) {
		int formattedPoint;
		try {
			formattedPoint = Integer.parseInt(point);
			return formattedPoint;
		} catch(NumberFormatException e) {
			if(point.equals("优秀"))
				formattedPoint = 95;
			else if(point.equals("良好"))
				formattedPoint = 85;
			else if(point.equals("中等"))
				formattedPoint = 75;
			else if(point.equals("及格"))
				formattedPoint = 65;
			else	//不及格
				formattedPoint = 55;
			return formattedPoint;
		}
	}
	/**
	 * 没有登录,或登录失败,或登录有效期已过(2013年时登录有效期为半小时)
	 *<p> 进行本类任何其他操作,都将抛出该异常<p>
	 * @author Javer
	 */
	@SuppressWarnings("serial")
	class WithoutLoggingException extends Exception{
		WithoutLoggingException() {
			super("在进行各项操作前,请先登录,当你在已登录情况下遇到此错误,表明你的登陆有效期已过,请重新登陆");
		}
	}
}
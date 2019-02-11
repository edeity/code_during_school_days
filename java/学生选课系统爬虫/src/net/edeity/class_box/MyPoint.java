package net.edeity.class_box;
/**
 * 封装山东大学学生已修课程的类
 * <ul>
 * 		<li>className : 课程名</li>
 * 		<li>classYear : 上课的年份</li>
 * 		<li>semester : 上下学期
 * 		<li>classPoint : 该课程的学分</li>
 * 		<li>classGrade :该课程的分数或等级</li>
 * </ul>
 * @author Javer 
 */
public class MyPoint {
	/**
	 * @return 课程名
	 */
	String getClassName() {
		return className;
	}
	/**
	 * 设置课程名
	 * @param className
	 */
	void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return 课程时间(年)
	 */
	int getClassYear() {
		return classYear;
	}
	/**
	 * 设置学年为哪学年
	 * @param classYear
	 */
	void setClassYear(int classYear) {
		this.classYear = classYear;
	}
	/**
	 * @return 哪一学期
	 * 上学学期,如果是上学期,其值为0,如果是下学期,其值为1
	 */
	public int getSemester() {
		return semester;
	}
	/**
	 * 设置是第几个学期
	 * @param semester
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}
	/**
	 * @return 课程学分
	 */
	float getClassPoint() {
		return classPoint;
	}
	/**
	 * 设置该课程的学分
	 * @param classPoint
	 */
	void setClassPoint(float classPoint) {
		this.classPoint = classPoint;
	}
	/**
	 * @return 返回该课程是属于必修,选修还是限选
	 */
	public String getClassAttitude() {
		return classAttitude;
	}
	/**
	 * @param classAttitude  设置该课程是属性必修, 选修还是限选
	 */
	public void setClassAttitude(String classAttitude) {
		this.classAttitude = classAttitude;
	}
	/**
	 * @return 课程成绩(有等级和真实评分之差)
	 */
	String getClassGrade() {
		return classGrade;
	}
	/**
	 * 设置该课程的程序
	 * @param classGrade
	 */
	void setClassGrade(String classGrade) {
		this.classGrade = classGrade;
	}
	public String toString() {
		String result = "[ " + className + " : " + classYear + " , " + semester + " , "+ classPoint + " , "  + classGrade + " , " + classAttitude + " ]";
		return result;
	}
	private String className;//科目名称
	private int classYear;//确认该科目是哪一个学年的
	private int semester;//上学学期,如果是上学期,其值为0,如果是下学期,其值为1
	private float classPoint;//每科的学分,浮点因为学分不一定是int
	private String classAttitude;//科目的属性:必修,选修,限选
	private String classGrade;//每科的成绩
}

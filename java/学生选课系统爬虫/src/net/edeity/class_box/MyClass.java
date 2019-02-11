package net.edeity.class_box;
/**
 * <p>封装山东大学学生在修课程的类</p>
 * <p>该类可能因为在录入信息时发生错误而导致属性任在初始化阶段</p>
 * <ul>
 * 		<li>className : 课程名</li>
 * 		<li>classPlace : 上课的地点</li>
 * 		<li>classDayOfWeek : 上课时间:星期几</li>
 * 		<li>classDayOfTime :上课时间:第几节课</li>
 * </ul>
 * @author Javer
 *
 */
public class MyClass {
	/**
	 * @return 课程名
	 */
	String getClassName() {
		return className;
	}
	/**
	 * 设置该课程的名字
	 * @param className
	 */
	void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return 上课地点
	 */
	String getClassPlace() {
		return classPlace;
	}
	/**
	 * 设置该课程的上课地点
	 * @param classPlace
	 */
	void setClassPlace(String classPlace) {
		this.classPlace = classPlace;
	}
	/**
	 * @return 课程每周的星期几 eg:返回数值 1 代表星期 一
	 */
	int getClassDayOfWeek() {
		return classDayOfWeek;
	}
	/**
	 * 设置该课程的是一周的星期几
	 * @param classDayOfWeek
	 */
	void setClassDayOfWeek(int classDayOfWeek) {
		this.classDayOfWeek = classDayOfWeek;
	}
	/**
	 * @return 课程在一天的多少节	eg:返回数值 1 代表第一节小课(每一小时当一小节)
	 */
	int getClassDayOfTime() {
		return classDayOfTime;
	}
	/**
	 * 设置该课程是一天的第几节课
	 * @param classDayOfTime
	 */
	void setClassDayOfTime(int classDayOfTime) {
		this.classDayOfTime = classDayOfTime;
	}
	public String toString() {
		String result = "[ " + className + " : " + classPlace + " , " + classDayOfWeek + " , "+ classDayOfTime + " ]";
		return result;
	}
	private String className;
	private String classPlace;
	private int classDayOfWeek;
	private int classDayOfTime;
}

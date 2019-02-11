package net.edeity.class_box;

/**
 * 封装山东大学学生一般信息的类
 * <ul>
 * 		<li>myName  : 学生姓名</li>
 * 		<li>myStudentID : 学生学号</li>
 * 		<li>myAcademy : 学生所在学院</li>
 * 		<li>specialty : 学生所在专业</li>
 * 		<li>firstAveGrade : 第一年成绩绩点</li>
 * 		<li>secondAveGrade : 第二年成绩绩点</li>
 * 		<li>thridAveGrade : 第三年成绩绩点</li>
 * 		<li>forthAveGrade : 第四年成绩绩点</li>
 * </ul>
 * @author Javer
 */
class MyInformation {
	/**
	 * @return 返回学生名字
	 */
	String getMyName() {
		return myName;
	}
	 void setMyName(String myName) {
		this.myName = myName;
	}
	/**
	 * @return 返回学生ID
	 */
	String getMyStudentID() {
		return myStudentID;
	}
	 void setMyStudentID(String myStudentID) {
		this.myStudentID = myStudentID;
	}
	/**
	 * @return 返回学生学院
	 */
	String getMyAcademy() {
		return myAcademy;
	}
	 void setMyAcademy(String myAcademy) {
		this.myAcademy = myAcademy;
	}
	 /**
		 * @return 返回学生专业
		 */
	String getMySpecialty() {
		return mySpecialty;
	}
	 void setMySpecialty(String specialty) {
		this.mySpecialty = specialty;
	}
	 /**
	  * @return 返回第一年学生绩点
	  */
	float getFirstAveGrade() {
		return firstAveGrade;
	}
	void setFirstAveGrade(float firstAveGrade) {
		this.firstAveGrade = firstAveGrade;
	}
	 /**
	  * @return	返回第二年学生绩点
	  */
	float getSecondAveGrade() {
		return secondAveGrade;
	}
	void setSecondAveGrade(float secondAveGrade) {
		this.secondAveGrade = secondAveGrade;
	}
	 /**
	  * @return	返回第三年学生绩点
	  */
	float getThridAveGrade() {
		return thridAveGrade;
	}
	void setThridAveGrade(float thridAveGrade) {
		this.thridAveGrade = thridAveGrade;
	}
	 /**
	  * @return	返回第四年学生绩点
	  */
	float getForthAveGrade() {
		return forthAveGrade;
	}
	void setForthAveGrade(float forthAveGrade) {
		this.forthAveGrade = forthAveGrade;
	}
	 public String toString() {
		 String result =	myAcademy + " , " + mySpecialty + " , " + myName + " , " + myStudentID
				 + " , " + firstAveGrade + " , " + secondAveGrade + " , " + thridAveGrade + " , " + forthAveGrade;
		 return result;
	 }
		private String myName;
		private String myStudentID;
		private String myAcademy;//学院
		private String mySpecialty;//专业
		private float firstAveGrade;
		private float secondAveGrade;
		private float thridAveGrade;
		private float forthAveGrade;
}

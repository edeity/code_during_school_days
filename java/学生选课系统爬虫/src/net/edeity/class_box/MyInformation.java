package net.edeity.class_box;

/**
 * ��װɽ����ѧѧ��һ����Ϣ����
 * <ul>
 * 		<li>myName  : ѧ������</li>
 * 		<li>myStudentID : ѧ��ѧ��</li>
 * 		<li>myAcademy : ѧ������ѧԺ</li>
 * 		<li>specialty : ѧ������רҵ</li>
 * 		<li>firstAveGrade : ��һ��ɼ�����</li>
 * 		<li>secondAveGrade : �ڶ���ɼ�����</li>
 * 		<li>thridAveGrade : ������ɼ�����</li>
 * 		<li>forthAveGrade : ������ɼ�����</li>
 * </ul>
 * @author Javer
 */
class MyInformation {
	/**
	 * @return ����ѧ������
	 */
	String getMyName() {
		return myName;
	}
	 void setMyName(String myName) {
		this.myName = myName;
	}
	/**
	 * @return ����ѧ��ID
	 */
	String getMyStudentID() {
		return myStudentID;
	}
	 void setMyStudentID(String myStudentID) {
		this.myStudentID = myStudentID;
	}
	/**
	 * @return ����ѧ��ѧԺ
	 */
	String getMyAcademy() {
		return myAcademy;
	}
	 void setMyAcademy(String myAcademy) {
		this.myAcademy = myAcademy;
	}
	 /**
		 * @return ����ѧ��רҵ
		 */
	String getMySpecialty() {
		return mySpecialty;
	}
	 void setMySpecialty(String specialty) {
		this.mySpecialty = specialty;
	}
	 /**
	  * @return ���ص�һ��ѧ������
	  */
	float getFirstAveGrade() {
		return firstAveGrade;
	}
	void setFirstAveGrade(float firstAveGrade) {
		this.firstAveGrade = firstAveGrade;
	}
	 /**
	  * @return	���صڶ���ѧ������
	  */
	float getSecondAveGrade() {
		return secondAveGrade;
	}
	void setSecondAveGrade(float secondAveGrade) {
		this.secondAveGrade = secondAveGrade;
	}
	 /**
	  * @return	���ص�����ѧ������
	  */
	float getThridAveGrade() {
		return thridAveGrade;
	}
	void setThridAveGrade(float thridAveGrade) {
		this.thridAveGrade = thridAveGrade;
	}
	 /**
	  * @return	���ص�����ѧ������
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
		private String myAcademy;//ѧԺ
		private String mySpecialty;//רҵ
		private float firstAveGrade;
		private float secondAveGrade;
		private float thridAveGrade;
		private float forthAveGrade;
}

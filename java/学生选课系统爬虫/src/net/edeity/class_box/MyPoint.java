package net.edeity.class_box;
/**
 * ��װɽ����ѧѧ�����޿γ̵���
 * <ul>
 * 		<li>className : �γ���</li>
 * 		<li>classYear : �Ͽε����</li>
 * 		<li>semester : ����ѧ��
 * 		<li>classPoint : �ÿγ̵�ѧ��</li>
 * 		<li>classGrade :�ÿγ̵ķ�����ȼ�</li>
 * </ul>
 * @author Javer 
 */
public class MyPoint {
	/**
	 * @return �γ���
	 */
	String getClassName() {
		return className;
	}
	/**
	 * ���ÿγ���
	 * @param className
	 */
	void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return �γ�ʱ��(��)
	 */
	int getClassYear() {
		return classYear;
	}
	/**
	 * ����ѧ��Ϊ��ѧ��
	 * @param classYear
	 */
	void setClassYear(int classYear) {
		this.classYear = classYear;
	}
	/**
	 * @return ��һѧ��
	 * ��ѧѧ��,�������ѧ��,��ֵΪ0,�������ѧ��,��ֵΪ1
	 */
	public int getSemester() {
		return semester;
	}
	/**
	 * �����ǵڼ���ѧ��
	 * @param semester
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}
	/**
	 * @return �γ�ѧ��
	 */
	float getClassPoint() {
		return classPoint;
	}
	/**
	 * ���øÿγ̵�ѧ��
	 * @param classPoint
	 */
	void setClassPoint(float classPoint) {
		this.classPoint = classPoint;
	}
	/**
	 * @return ���ظÿγ������ڱ���,ѡ�޻�����ѡ
	 */
	public String getClassAttitude() {
		return classAttitude;
	}
	/**
	 * @param classAttitude  ���øÿγ������Ա���, ѡ�޻�����ѡ
	 */
	public void setClassAttitude(String classAttitude) {
		this.classAttitude = classAttitude;
	}
	/**
	 * @return �γ̳ɼ�(�еȼ�����ʵ����֮��)
	 */
	String getClassGrade() {
		return classGrade;
	}
	/**
	 * ���øÿγ̵ĳ���
	 * @param classGrade
	 */
	void setClassGrade(String classGrade) {
		this.classGrade = classGrade;
	}
	public String toString() {
		String result = "[ " + className + " : " + classYear + " , " + semester + " , "+ classPoint + " , "  + classGrade + " , " + classAttitude + " ]";
		return result;
	}
	private String className;//��Ŀ����
	private int classYear;//ȷ�ϸÿ�Ŀ����һ��ѧ���
	private int semester;//��ѧѧ��,�������ѧ��,��ֵΪ0,�������ѧ��,��ֵΪ1
	private float classPoint;//ÿ�Ƶ�ѧ��,������Ϊѧ�ֲ�һ����int
	private String classAttitude;//��Ŀ������:����,ѡ��,��ѡ
	private String classGrade;//ÿ�Ƶĳɼ�
}

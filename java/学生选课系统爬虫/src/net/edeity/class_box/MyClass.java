package net.edeity.class_box;
/**
 * <p>��װɽ����ѧѧ�����޿γ̵���</p>
 * <p>���������Ϊ��¼����Ϣʱ��������������������ڳ�ʼ���׶�</p>
 * <ul>
 * 		<li>className : �γ���</li>
 * 		<li>classPlace : �Ͽεĵص�</li>
 * 		<li>classDayOfWeek : �Ͽ�ʱ��:���ڼ�</li>
 * 		<li>classDayOfTime :�Ͽ�ʱ��:�ڼ��ڿ�</li>
 * </ul>
 * @author Javer
 *
 */
public class MyClass {
	/**
	 * @return �γ���
	 */
	String getClassName() {
		return className;
	}
	/**
	 * ���øÿγ̵�����
	 * @param className
	 */
	void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return �Ͽεص�
	 */
	String getClassPlace() {
		return classPlace;
	}
	/**
	 * ���øÿγ̵��Ͽεص�
	 * @param classPlace
	 */
	void setClassPlace(String classPlace) {
		this.classPlace = classPlace;
	}
	/**
	 * @return �γ�ÿ�ܵ����ڼ� eg:������ֵ 1 �������� һ
	 */
	int getClassDayOfWeek() {
		return classDayOfWeek;
	}
	/**
	 * ���øÿγ̵���һ�ܵ����ڼ�
	 * @param classDayOfWeek
	 */
	void setClassDayOfWeek(int classDayOfWeek) {
		this.classDayOfWeek = classDayOfWeek;
	}
	/**
	 * @return �γ���һ��Ķ��ٽ�	eg:������ֵ 1 �����һ��С��(ÿһСʱ��һС��)
	 */
	int getClassDayOfTime() {
		return classDayOfTime;
	}
	/**
	 * ���øÿγ���һ��ĵڼ��ڿ�
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

package net.edeity.demo.hms.database;
/**
 * ����֧�ֲ�ѯ��ʾ������
 * @author Javer
 *
 */
public interface Inquirable {
	/**
	 * ����������length�Բ�ѯ����������
	 * @param index
	 * @param length
	 * @return
	 */
	public  String[][] inquire(int index, int length);
	/**
	 * @return ��Ӧ���ݿ��е���Ϣ
	 */
	public String[] getTitle();
}

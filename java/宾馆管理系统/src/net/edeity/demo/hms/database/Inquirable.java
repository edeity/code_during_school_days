package net.edeity.demo.hms.database;
/**
 * 定义支持查询显示的数据
 * @author Javer
 *
 */
public interface Inquirable {
	/**
	 * 根据索引和length以查询并返回数据
	 * @param index
	 * @param length
	 * @return
	 */
	public  String[][] inquire(int index, int length);
	/**
	 * @return 对应数据库中的信息
	 */
	public String[] getTitle();
}

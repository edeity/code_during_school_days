package net.edeity.demo.hms.appearance.waiter;

import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.edeity.demo.hms.appearance.HU;
import net.edeity.demo.hms.database.Arrange;
import net.edeity.demo.hms.database.Operation;

/**
 * 该类用于选择日期的面板,
 * 检测日期是否合理
 * 若日期在今天,则为入住
 * 若日期在未来,则为预定
 * 并计算from - to的日期差
 * @author Javer
 *
 */
@SuppressWarnings("serial")
public class TimePanel extends JPanel{
	//输入年月日的信息
	private JTextField fromYearField;
	private JTextField fromMouthField;
	private JTextField fromDayField;
	//和上面TextField相对应的输入数据
	Calendar nowCal = Calendar.getInstance();
	Calendar fromCal = Calendar.getInstance();
	private int fromYear, fromMouth, fromDate;
	//根据日期检测得到的结果
	private String operateKind;//预定或进住
	private int lastDay;//持续的时间
	private JTextField lastDayField;
	
	public TimePanel() {
		super();
		
		JLabel label = new JLabel("\u65F6\u95F4\u5B89\u6392 : ");
		
		fromYearField = new JTextField();
		fromYearField.setColumns(4);
		fromYearField.setText(""+nowCal.get(Calendar.YEAR));
		
		JLabel fromYearLab = new JLabel("\u5E74");
		
		fromMouthField = new JTextField();
		fromMouthField.setColumns(2);
		fromMouthField.setText(nowCal.get(Calendar.MONTH) + 1 + "");//Calender mouth + 1
		
		JLabel fromMouthLab = new JLabel("\u6708");
		
		fromDayField = new JTextField();
		fromDayField.setColumns(2);
		fromDayField.setText("" + nowCal.get(Calendar.DATE));
		
		JLabel fromDayLab = new JLabel("\u65E5");
		
		JLabel lastDayLab = new JLabel("\u6301\u7EED\u65F6\u95F4 : ");
		
		lastDayField = new JTextField();
		lastDayField.setText("1");
		lastDayField.setColumns(2);
		
		JLabel dayLab = new JLabel("\u5929");
		GroupLayout gl_timeArrangePanel = new GroupLayout(this);
		gl_timeArrangePanel.setHorizontalGroup(
			gl_timeArrangePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_timeArrangePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_timeArrangePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addGroup(gl_timeArrangePanel.createSequentialGroup()
							.addComponent(fromYearField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(fromYearLab)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(fromMouthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(fromMouthLab)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(fromDayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(fromDayLab))
						.addGroup(gl_timeArrangePanel.createSequentialGroup()
							.addComponent(lastDayLab)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lastDayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dayLab)))
					.addContainerGap(294, Short.MAX_VALUE))
		);
		gl_timeArrangePanel.setVerticalGroup(
			gl_timeArrangePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_timeArrangePanel.createSequentialGroup()
					.addGap(17)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_timeArrangePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(fromYearField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fromYearLab)
						.addComponent(fromMouthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fromMouthLab)
						.addComponent(fromDayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fromDayLab))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_timeArrangePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_timeArrangePanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lastDayLab)
							.addComponent(lastDayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(dayLab))
					.addContainerGap(214, Short.MAX_VALUE))
		);
		this.setLayout(gl_timeArrangePanel);
	}
	/**
	 * 检测输入的日期是否符合正常的日期(如月份不能大于12)
	 */
	public boolean checkTimeValid() {
		fromCal.setLenient(false);
		//检查非空
		if(HU.isNotNull(fromYearField.getText(), "起始年份") && HU.isNotNull(fromMouthField.getText(), "起始月份") && 
				HU.isNotNull(fromDayField.getText(), "起始天数") && HU.isNotNull(lastDayField.getText(), "持续时间")) {
			fromYear = Integer.parseInt(fromYearField.getText());
			fromMouth = Integer.parseInt(fromMouthField.getText());
			fromDate = Integer.parseInt(fromDayField.getText());
			lastDay = Integer.parseInt(lastDayField.getText());
			//检查日期是否符合常识,可以察觉,代码中对月份进行了-1处理,这是因为Canlender中月份是从0开始的
			//即我们输入的是12月份,那么Canlender.set(月份时),值应该为 12 - 1 = 11;
			fromCal.set(fromYear, fromMouth - 1, fromDate);
			try {
				fromYear = fromCal.get(Calendar.YEAR);
				fromMouth = fromCal.get(Calendar.MONTH);
				fromDate = fromCal.get(Calendar.DATE);
				return true;
			} catch(IllegalArgumentException e) {
				HU.illegalInput("时间");
			}
		}
		return false;
	}
	/**
	 * 检测输入的日期是否符合预定等要求(如:如不能预定超过30天)
	 * 并确认操作方式是预定还是入住
	 * @return 
	 */
	public boolean checkDayDistance() {
		long fromNow = (int) ((fromCal.getTimeInMillis() - nowCal.getTimeInMillis()) / (3600 * 14 * 1000));
		fromCal.add(Calendar.DATE, lastDay);
		nowCal.add(Calendar.DATE, Arrange.TIME_LIMIT);
		if(fromCal.after(nowCal)) {
			JOptionPane.showMessageDialog(null, "只能对房间进行30天以内的操作");
			return false;
		}
		if(fromNow < 0) {
			JOptionPane.showMessageDialog(null, "起始时间必须在今天或今天之后");
			return false;
		}else {
			if(fromNow == 0) {
				operateKind = Operation.CHECK_IN;
			}else if(fromNow > 0) {
				operateKind = Operation.BOOK;
			}
			return true;
		}
	}
	public int getYear() {
		return fromYear;
	}
	public int getMonth() {
		return fromMouth;
	}
	public int getDate() {
		return fromDate;
	}
	public int getLastDay() {
		return lastDay;
	}
	public String getOperationKind() {
		return operateKind;
	}
	public Calendar getFunctionCal(){
		return fromCal;
	}
}

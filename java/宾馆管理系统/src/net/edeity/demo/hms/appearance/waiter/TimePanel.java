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
 * ��������ѡ�����ڵ����,
 * ��������Ƿ����
 * �������ڽ���,��Ϊ��ס
 * ��������δ��,��ΪԤ��
 * ������from - to�����ڲ�
 * @author Javer
 *
 */
@SuppressWarnings("serial")
public class TimePanel extends JPanel{
	//���������յ���Ϣ
	private JTextField fromYearField;
	private JTextField fromMouthField;
	private JTextField fromDayField;
	//������TextField���Ӧ����������
	Calendar nowCal = Calendar.getInstance();
	Calendar fromCal = Calendar.getInstance();
	private int fromYear, fromMouth, fromDate;
	//�������ڼ��õ��Ľ��
	private String operateKind;//Ԥ�����ס
	private int lastDay;//������ʱ��
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
	 * �������������Ƿ��������������(���·ݲ��ܴ���12)
	 */
	public boolean checkTimeValid() {
		fromCal.setLenient(false);
		//���ǿ�
		if(HU.isNotNull(fromYearField.getText(), "��ʼ���") && HU.isNotNull(fromMouthField.getText(), "��ʼ�·�") && 
				HU.isNotNull(fromDayField.getText(), "��ʼ����") && HU.isNotNull(lastDayField.getText(), "����ʱ��")) {
			fromYear = Integer.parseInt(fromYearField.getText());
			fromMouth = Integer.parseInt(fromMouthField.getText());
			fromDate = Integer.parseInt(fromDayField.getText());
			lastDay = Integer.parseInt(lastDayField.getText());
			//��������Ƿ���ϳ�ʶ,���Բ��,�����ж��·ݽ�����-1����,������ΪCanlender���·��Ǵ�0��ʼ��
			//�������������12�·�,��ôCanlender.set(�·�ʱ),ֵӦ��Ϊ 12 - 1 = 11;
			fromCal.set(fromYear, fromMouth - 1, fromDate);
			try {
				fromYear = fromCal.get(Calendar.YEAR);
				fromMouth = fromCal.get(Calendar.MONTH);
				fromDate = fromCal.get(Calendar.DATE);
				return true;
			} catch(IllegalArgumentException e) {
				HU.illegalInput("ʱ��");
			}
		}
		return false;
	}
	/**
	 * �������������Ƿ����Ԥ����Ҫ��(��:�粻��Ԥ������30��)
	 * ��ȷ�ϲ�����ʽ��Ԥ��������ס
	 * @return 
	 */
	public boolean checkDayDistance() {
		long fromNow = (int) ((fromCal.getTimeInMillis() - nowCal.getTimeInMillis()) / (3600 * 14 * 1000));
		fromCal.add(Calendar.DATE, lastDay);
		nowCal.add(Calendar.DATE, Arrange.TIME_LIMIT);
		if(fromCal.after(nowCal)) {
			JOptionPane.showMessageDialog(null, "ֻ�ܶԷ������30�����ڵĲ���");
			return false;
		}
		if(fromNow < 0) {
			JOptionPane.showMessageDialog(null, "��ʼʱ������ڽ�������֮��");
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

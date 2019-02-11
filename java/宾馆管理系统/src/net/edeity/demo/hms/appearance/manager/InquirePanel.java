package net.edeity.demo.hms.appearance.manager;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.edeity.demo.hms.database.Inquirable;
/**
 * 支持简单查询并显示结果的面板
 */
@SuppressWarnings("serial")
public class InquirePanel extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private int index = 0;//记录显示条目的index
	/**
	 * @param inquireData 可查询并显示的数据
	 * @param rowNum 需要显示的行数
	 */
	public InquirePanel( Inquirable inquireData, int rowNum) {
		
		JScrollPane billSP = new JScrollPane();
		
		JButton forwardBtn = new JButton("前"+rowNum+"条记录");
		forwardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index -= rowNum;
				if(index>=0) {
					inputMessage(inquireData.inquire(index, rowNum));
				}else {
					index = 0;
					JOptionPane.showMessageDialog(InquirePanel.this, "显示数据已经是前"+ rowNum + "条了");
				}
			}
		});
		
		JButton towardBtn = new JButton("后"+ rowNum + "条记录");
		towardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index += rowNum;
				inputMessage(inquireData.inquire(index, rowNum));
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(forwardBtn)
					.addPreferredGap(ComponentPlacement.RELATED, 264, Short.MAX_VALUE)
					.addComponent(towardBtn))
				.addComponent(billSP, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(billSP, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(towardBtn)
						.addComponent(forwardBtn)))
		);
		
		model  = new DefaultTableModel(inquireData.getTitle(), rowNum);
		table = new JTable(model);
		billSP.setViewportView(table);
		inputMessage(inquireData.inquire(index, rowNum));
		setLayout(groupLayout);
	}
	private void inputMessage(String[][] message) {
		for(int i = 0; i< message.length; i++)
			for(int j = 0; j<message[i].length; j++){
				model.setValueAt(message[i][j], i, j);
			}
		this.validate();
	}
}

package net.edeity.demo.hms.appearance.waiter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JTextArea;

import net.edeity.demo.hms.database.Operation;

/**
 * 支付界面
 * @author Javer
 *
 */
@SuppressWarnings("serial")
public class PayPanel extends JPanel{
	private final ButtonGroup payBGround = new ButtonGroup();
	private JTextArea textArea;
	public PayPanel(Operation operation) {
		
		JLabel payKindLabel = new JLabel("请选择支付类型 : ");
		
		JRadioButton casePayRB = new JRadioButton("现金支付");
		casePayRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation.setPayKind(Operation.PAY_OF_CASE);
			}
		});
		payBGround.add(casePayRB);
		
		JRadioButton vipPayRB = new JRadioButton("会员卡支付");
		payBGround.add(vipPayRB);
		vipPayRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operation.getCustomer().isVip()) {
					operation.setPayKind(Operation.PAY_OF_VIP);
					
				}else{
					JOptionPane.showMessageDialog(PayPanel.this, "对不起,你的会员等级不支持会员卡支付");
				}
			}
		});
		
		JRadioButton delayPayRB = new JRadioButton("挂单");
		payBGround.add(delayPayRB);
		delayPayRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operation.getCustomer().canDelay()) {
					operation.setPayKind(Operation.PAY_OF_DELAY);
				}else {
					JOptionPane.showMessageDialog(PayPanel.this, "对不起,你的会员等级不支持挂单");
				}
			}
		});
		
		//支付钱
		JButton ensurePayBtn = new JButton("确认支付");
		ensurePayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operation.pay()) {
					JOptionPane.showMessageDialog(PayPanel.this, "执行成功");
				}
			}
		});
		
		JButton textField = new JButton("生成订单");
		textField.addActionListener(new ActionListener() {
			//显示详细的账单
			public void actionPerformed(ActionEvent e) {
				textArea.setText(operation.getDetailedBill());
			}
		});
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(casePayRB)
							.addGap(18)
							.addComponent(vipPayRB)
							.addGap(18)
							.addComponent(delayPayRB))
						.addComponent(payKindLabel)))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(369, Short.MAX_VALUE)
					.addComponent(ensurePayBtn))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addComponent(payKindLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(casePayRB)
						.addComponent(vipPayRB)
						.addComponent(delayPayRB))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField)
					.addGap(18)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
					.addComponent(ensurePayBtn))
		);
		setLayout(groupLayout);
	}
}

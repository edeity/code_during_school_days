package net.edeity.demo.hms.appearance.waiter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import net.edeity.demo.hms.appearance.HU;
import net.edeity.demo.hms.database.Customer;

@SuppressWarnings("serial")
public class VipPanel extends JPanel {
	private final int recharge = 1;
	private final int remain = 2;
	private int opeKind = recharge;
	private JTextField nameField;
	private JTextField idField;
	private String sex = "男";

	private JTextField moneyField;
	private JLabel moneyLab;
	private JLabel remainLab;
	private final ButtonGroup vipGroup = new ButtonGroup();
	private final ButtonGroup sexGroup = new ButtonGroup();

	public VipPanel() {

		JRadioButton rechargeRB = new JRadioButton("\u4F1A\u5458\u5145\u503C");
		rechargeRB.setSelected(true);
		rechargeRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opeKind = recharge;
				refresh(true);
			}
		});
		vipGroup.add(rechargeRB);

		JLabel nameLab = new JLabel("\u59D3\u540D : ");

		JLabel idLab = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\u7801 : ");

		moneyLab = new JLabel("\u5145\u503C\u91D1\u989D : ");

		nameField = new JTextField();
		nameField.setColumns(10);

		idField = new JTextField();
		idField.setColumns(10);

		moneyField = new JTextField();
		moneyField.setColumns(10);

		JRadioButton remainRB = new JRadioButton("\u4F59\u989D\u67E5\u8BE2 : ");
		remainRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opeKind = remain;
				refresh(false);
			}
		});
		vipGroup.add(remainRB);

		remainLab = new JLabel("");

		JButton btnNewButton = new JButton("\u786E\u8BA4");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String ID = idField.getText();
				String money = moneyField.getText();
				if (HU.isNotNull(name, "姓名") && HU.isNotNull(ID, "身份证号码")
						&& HU.isNotNull(money, "充值金额")) {
					Customer c = new Customer(name, ID, sex);
					if (c.checkInfo()) {
						//vip充值
						if (opeKind == recharge) {
								if (HU.isPositiveNumber(money, "充值金额")) {
									if (c.register() && c.recharge(money))
										JOptionPane.showMessageDialog(
												VipPanel.this, "充值成功");
									else
										JOptionPane.showMessageDialog(
												VipPanel.this, "充值失败");
							}
						}
					} else if (opeKind == remain) {
						//vip余额查询
						if (c.isVip()) {
							int remainMoney = c.remain();
							if (c.remain() == -1)
								JOptionPane.showMessageDialog(VipPanel.this,
										"无法查询,请确认所填信息是否正确");
							else
								remainLab.setText("亲爱的会员 : " + name
										+ ", 你好,你目前会员卡余额为 : " + remainMoney);
						} else {
							remainLab.setText("亲爱的顾客 : " + name
									+ ", 你好,你目前还不是会员,请选择会员注册加入我们");
						}
					}
				} else {
					JOptionPane.showMessageDialog(VipPanel.this, "身份信息不正确");
				}
			}
		});
		
		JLabel label = new JLabel("\u6027\u522B : ");
		
		JRadioButton radioButton = new JRadioButton("\u7537");
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sex = "男";
			}
		});
		radioButton.setSelected(true);
		sexGroup.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("\u5973");
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sex = "女";
			}
		});
		sexGroup.add(radioButton_1);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(76)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(nameLab)
										.addComponent(idLab)
										.addComponent(remainLab)
										.addComponent(rechargeRB)
										.addComponent(moneyLab))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(21)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(radioButton)
													.addGap(18)
													.addComponent(radioButton_1))
												.addComponent(moneyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(18)
											.addComponent(remainRB))))
								.addComponent(label)))
						.addComponent(btnNewButton))
					.addContainerGap(183, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rechargeRB)
						.addComponent(remainRB))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameLab)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idLab)
						.addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(radioButton)
						.addComponent(radioButton_1))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(39)
							.addComponent(remainLab)
							.addPreferredGap(ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
							.addComponent(btnNewButton))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(moneyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(moneyLab))
							.addContainerGap())))
		);
		setLayout(groupLayout);
	}

	/**
	 * 因为点击按钮时注册和查找的不同,所以要来回切换
	 * 
	 * @param moneyVisible
	 */
	private void refresh(boolean moneyVisible) {
		moneyLab.setVisible(moneyVisible);
		moneyField.setVisible(moneyVisible);
		this.repaint();
		this.validate();
	}
}

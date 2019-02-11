package net.edeity.demo.hms.appearance.waiter;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import net.edeity.demo.hms.appearance.HU;
import net.edeity.demo.hms.database.Customer;
import net.edeity.demo.hms.database.Operation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 信息登记界面
 * @author Javer
 *
 */
@SuppressWarnings("serial")
public class RegistInfoPanel extends JPanel{
	private JTextField nameField;
	private JTextField IDField;
	private JTextField phoneField;
	private JTextField addressField;
	private String sex = "男";
	Customer customer;
	
	private boolean next = false;
	public JButton nextBtn;
	
	public RegistInfoPanel() {
		super();
		
		JLabel nameLab = new JLabel("名字");
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		JLabel sexLab = new JLabel("性别");
		
		JLabel IDLab = new JLabel("身份证号码");
		
		IDField = new JTextField();
		IDField.setColumns(18);
		
		JLabel phoneLab = new JLabel("联系电话");
		
		phoneField = new JTextField();
		phoneField.setColumns(32);
		
		JRadioButton maleBtn = new JRadioButton("男");
		maleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sex = "男";
			}
		});
		maleBtn.setSelected(true);
		
		JRadioButton femaleBtn = new JRadioButton("女");
		femaleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sex = "女";
			}
		});
		
		JLabel adressLab = new JLabel("地址");
		
		addressField = new JTextField();
		addressField.setColumns(32);
		
		JButton registBtn = new JButton("注册");
		registBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAndRegister();
			}
		});
		
		nextBtn = new JButton("下一步");
		nextBtn.setVisible(false);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(97)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(nameLab)
						.addComponent(sexLab)
						.addComponent(IDLab)
						.addComponent(phoneLab)
						.addComponent(adressLab))
					.addGap(47)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(addressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(phoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(IDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(maleBtn)
							.addGap(18)
							.addComponent(femaleBtn))))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(registBtn, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
					.addComponent(nextBtn))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(38)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameLab)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sexLab)
						.addComponent(maleBtn)
						.addComponent(femaleBtn))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(IDLab)
						.addComponent(IDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(phoneLab)
						.addComponent(phoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(adressLab)
						.addComponent(addressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(registBtn)
						.addComponent(nextBtn)))
		);
		setLayout(groupLayout);
		
	}
	private void checkAndRegister(){
		String name = nameField.getText();
		String id = IDField.getText();
		if(HU.isNotNull(name, "名字") && HU.isNotNull(id, "身份证号码"))
		{
			String phone = phoneField.getText();
			String address = addressField.getText();
			customer = new Customer(name, sex, id, phone, address);
			if(customer.checkInfo()) {
				if(customer.register()) { 
					next = true;
					JOptionPane.showMessageDialog(this, "注册成功");
				}
				else 
					JOptionPane.showMessageDialog(this, "注册失败,原因未知");
			}else {
				JOptionPane.showMessageDialog(this, "身份验证失败");
			}
		}
	}
	public boolean updateOperation(Operation operation) {
		if(next) {
			operation.setCustomerInfo(customer);
		}else {
			JOptionPane.showMessageDialog(RegistInfoPanel.this, "请点击注册按钮登记个人信息");
		}
		return next;
	}
}

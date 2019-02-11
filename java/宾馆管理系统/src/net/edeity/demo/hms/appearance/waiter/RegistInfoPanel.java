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
 * ��Ϣ�Ǽǽ���
 * @author Javer
 *
 */
@SuppressWarnings("serial")
public class RegistInfoPanel extends JPanel{
	private JTextField nameField;
	private JTextField IDField;
	private JTextField phoneField;
	private JTextField addressField;
	private String sex = "��";
	Customer customer;
	
	private boolean next = false;
	public JButton nextBtn;
	
	public RegistInfoPanel() {
		super();
		
		JLabel nameLab = new JLabel("����");
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		JLabel sexLab = new JLabel("�Ա�");
		
		JLabel IDLab = new JLabel("���֤����");
		
		IDField = new JTextField();
		IDField.setColumns(18);
		
		JLabel phoneLab = new JLabel("��ϵ�绰");
		
		phoneField = new JTextField();
		phoneField.setColumns(32);
		
		JRadioButton maleBtn = new JRadioButton("��");
		maleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sex = "��";
			}
		});
		maleBtn.setSelected(true);
		
		JRadioButton femaleBtn = new JRadioButton("Ů");
		femaleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sex = "Ů";
			}
		});
		
		JLabel adressLab = new JLabel("��ַ");
		
		addressField = new JTextField();
		addressField.setColumns(32);
		
		JButton registBtn = new JButton("ע��");
		registBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAndRegister();
			}
		});
		
		nextBtn = new JButton("��һ��");
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
		if(HU.isNotNull(name, "����") && HU.isNotNull(id, "���֤����"))
		{
			String phone = phoneField.getText();
			String address = addressField.getText();
			customer = new Customer(name, sex, id, phone, address);
			if(customer.checkInfo()) {
				if(customer.register()) { 
					next = true;
					JOptionPane.showMessageDialog(this, "ע��ɹ�");
				}
				else 
					JOptionPane.showMessageDialog(this, "ע��ʧ��,ԭ��δ֪");
			}else {
				JOptionPane.showMessageDialog(this, "�����֤ʧ��");
			}
		}
	}
	public boolean updateOperation(Operation operation) {
		if(next) {
			operation.setCustomerInfo(customer);
		}else {
			JOptionPane.showMessageDialog(RegistInfoPanel.this, "����ע�ᰴť�ǼǸ�����Ϣ");
		}
		return next;
	}
}

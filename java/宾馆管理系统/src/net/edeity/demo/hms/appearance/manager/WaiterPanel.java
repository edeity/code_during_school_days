package net.edeity.demo.hms.appearance.manager;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import net.edeity.demo.hms.appearance.HU;
import net.edeity.demo.hms.database.Waiter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WaiterPanel extends JPanel{
	private JTextField nameField;
	private JTextField idField;
	private JButton registerBtn;
	private JLabel passwordLab;
	private JPasswordField passwordField;
	public WaiterPanel() {
		
		JLabel nameLlab = new JLabel("\u670D\u52A1\u5458\u540D\u5B57 : ");
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		JLabel idLab = new JLabel("\u8EAB\u4EFD\u8BC1ID : ");
		
		idField = new JTextField();
		idField.setColumns(10);
		
		registerBtn = new JButton("\u6CE8\u518C");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String id = idField.getText();
				String password = passwordField.getText();
				if(HU.isNotNull(name, "名字") && HU.isNotNull(id, "身份证ID") && HU.isNotNull(password, "密码") && HU.isPositiveNumber(id, "身份证")) {
					if(Waiter.register(id, name, password))
						JOptionPane.showMessageDialog(WaiterPanel.this, "注册成功");
					else 
						JOptionPane.showMessageDialog(WaiterPanel.this, "注册失败");
				}
			}
		});
		
		passwordLab = new JLabel("\u5BC6\u7801 :");
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(44)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(nameLlab)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(idLab)
										.addComponent(passwordLab))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
						.addComponent(registerBtn))
					.addContainerGap(256, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameLlab)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idLab)
						.addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(passwordLab)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
					.addComponent(registerBtn))
		);
		setLayout(groupLayout);
	}
}

package net.edeity.demo.hms.appearance.manager;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

import net.edeity.demo.hms.appearance.HU;
import net.edeity.demo.hms.database.Arrange;
import net.edeity.demo.hms.database.Room;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomRegisterPanel extends JPanel{
	private JTextField roomIDField;
	private int bedNum = Room.SINGLE_BED;
	private int level = Room.ECONOMIC_LEVEL; 
	private final ButtonGroup bedNumGroup = new ButtonGroup();
	private final ButtonGroup levelGroup = new ButtonGroup();
	private JTextField priceField;
	public RoomRegisterPanel() {
		
		JLabel roomIDLab = new JLabel("\u623F\u95F4\u53F7 : ");
		
		roomIDField = new JTextField();
		roomIDField.setColumns(10);
		
		JLabel bedNumLab = new JLabel("\u5E8A\u4F4D\u7C7B\u578B : ");
		
		JRadioButton singleRB = new JRadioButton("\u5355\u4EBA\u95F4");
		singleRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bedNum = Room.SINGLE_BED;
			}
		});
		singleRB.setSelected(true);
		bedNumGroup.add(singleRB);
		
		JRadioButton doubleRB = new JRadioButton("\u53CC\u4EBA\u95F4");
		doubleRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bedNum = Room.DOUBLE_BED;
			}
		});
		bedNumGroup.add(doubleRB);
		
		JLabel levelLab = new JLabel("\u623F\u95F4\u7C7B\u578B : ");
		
		JRadioButton economicRB = new JRadioButton("\u7ECF\u6D4E\u623F");
		economicRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = Room.ECONOMIC_LEVEL;
			}
		});
		economicRB.setSelected(true);
		levelGroup.add(economicRB);
		
		JRadioButton businessRB = new JRadioButton("\u5546\u52A1\u623F");
		businessRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = Room.BUSSINESS_LEVEL;
			}
		});
		levelGroup.add(businessRB);
		
		JButton registerBtn = new JButton("\u767B\u8BB0");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String roomID = roomIDField.getText();
				String roomPrice = priceField.getText();
				if(HU.isNotNull(roomID, "房间号") && HU.isNotNull(roomPrice, "房间价格") &&
						HU.isPositiveNumber(roomID, "房间号") && HU.isPositiveNumber(roomPrice, "房间价格")){
					if(Room.register(roomID, bedNum, level, roomPrice) && Arrange.init(roomID))
						JOptionPane.showMessageDialog(RoomRegisterPanel.this, "注册成功");
					else
						JOptionPane.showMessageDialog(RoomRegisterPanel.this, "注册失败");
				}
			}
		});
		
		JLabel priceLab = new JLabel("\u623F\u95F4\u4EF7\u683C :");
		
		priceField = new JTextField();
		priceField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(levelLab)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(economicRB)
									.addGap(18)
									.addComponent(businessRB))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(bedNumLab)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(singleRB)
									.addGap(18)
									.addComponent(doubleRB))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(roomIDLab)
									.addGap(18)
									.addComponent(roomIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(priceLab)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(priceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addComponent(registerBtn))
					.addContainerGap(218, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(roomIDLab)
						.addComponent(roomIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(bedNumLab)
						.addComponent(singleRB)
						.addComponent(doubleRB))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(levelLab)
						.addComponent(economicRB)
						.addComponent(businessRB))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(priceLab)
						.addComponent(priceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
					.addComponent(registerBtn))
		);
		setLayout(groupLayout);
	}
}

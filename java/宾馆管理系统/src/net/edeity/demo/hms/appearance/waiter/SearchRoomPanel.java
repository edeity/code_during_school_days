package net.edeity.demo.hms.appearance.waiter;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import net.edeity.demo.hms.appearance.HU;
import net.edeity.demo.hms.database.Arrange;
import net.edeity.demo.hms.database.Operation;
import net.edeity.demo.hms.database.Room;

/**
 * ��ѯ�Ƿ��п��෿�����
 * @author Javer
 *
 */
@SuppressWarnings("serial")
public class SearchRoomPanel extends JPanel{
	private final ButtonGroup peopleNumGroup = new ButtonGroup();
	private final ButtonGroup roomLevelGroup = new ButtonGroup();
	private final ButtonGroup searchKindGroup = new ButtonGroup();

	private JTextField roomIDField;
	SpareRoomPanel resultPanel;
	TimePanel timePanel ;
	//�����ť��Ϊ�����̿����õ�
	public JButton nextBtn;
	
	private boolean roomIDSearch = false;
	private int num = Room.SINGLE_BED;
	private int level = Room.ECONOMIC_LEVEL;
	
	private boolean next = false;//�Ƿ��Ѿ��������������
	
	public SearchRoomPanel() {
		super();
		
		JRadioButton singleBtn = new JRadioButton("���˼�");
		singleBtn.setSelected(true);
		singleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num = Room.SINGLE_BED;
			}
		});
		peopleNumGroup.add(singleBtn);
		
		JRadioButton doubleBtn = new JRadioButton("˫�˼�");
		doubleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num = Room.DOUBLE_BED;
			}
		});
		peopleNumGroup.add(doubleBtn);
		
		JRadioButton economicalBtn = new JRadioButton("���÷�");
		economicalBtn.setSelected(true);
		economicalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = Room.ECONOMIC_LEVEL;
			}
		});
		roomLevelGroup.add(economicalBtn);
		
		JRadioButton businessBtn = new JRadioButton("����");
		businessBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = Room.BUSSINESS_LEVEL;
			}
		});
		roomLevelGroup.add(businessBtn);
		
		resultPanel = new SpareRoomPanel();

		timePanel = new TimePanel();
		
		JButton searchBtn = new JButton("��ѯ");
		
		//���ҹ��ܰ�ť
		searchBtn.addActionListener(new ActionListener() {
			//����������ҷ���,1.Ѱ�ҷ������͵ķ��� 2.Ѱ�ҿ��еķ���
			public void actionPerformed(ActionEvent e) {
				checkAndSearch();
				SearchRoomPanel.this.repaint();//��ȥ����Ļ���
			}
		});
		
		nextBtn = new JButton();
		nextBtn.setText("��һ��");
		nextBtn.setVisible(false);
		
		roomIDField = new JTextField();
		roomIDField.setColumns(10);
		
		JRadioButton roomIdRB = new JRadioButton("���Ų�ѯ");
		roomIdRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomIDSearch = true;
			}
		});
		searchKindGroup.add(roomIdRB);
		
		JRadioButton conditionRB = new JRadioButton("������ѯ");
		conditionRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomIDSearch = false;
			}
		});
		searchKindGroup.add(conditionRB);
		conditionRB.setSelected(true);
		
		//����
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(conditionRB)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(21)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(economicalBtn)
										.addComponent(singleBtn))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(businessBtn)
										.addComponent(doubleBtn)))
								.addComponent(roomIdRB)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(21)
									.addComponent(roomIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(timePanel, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(resultPanel, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
						.addComponent(searchBtn))
					.addGap(16)
					.addComponent(nextBtn))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(427, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchBtn)
						.addComponent(nextBtn)))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(resultPanel, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(conditionRB)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(singleBtn)
								.addComponent(doubleBtn))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(economicalBtn)
								.addComponent(businessBtn))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(roomIdRB)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(roomIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(timePanel, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(168, Short.MAX_VALUE))
		);
		
		
		setLayout(groupLayout);
	}
	private void checkAndSearch() {
		if(timePanel.checkTimeValid() && timePanel.checkDayDistance()) {
			String roomID = roomIDField.getText();
			Arrange arr = new Arrange(timePanel.getYear(), timePanel.getMonth(), timePanel.getDate(), timePanel.getLastDay());
			if(roomIDSearch) {
				if(HU.isNotNull(roomID, "����")) {
					resultPanel.updatePanel(arr.filterByRoomState(Room.searchByID(roomID)));
				}
			}else {
				Room standard = new Room(null, num, level, 0);
				resultPanel.updatePanel(arr.filterByRoomState(Room.searchByCondition(standard)));
			}
			next = true;
		}else {
			JOptionPane.showMessageDialog(SearchRoomPanel.this, "���ڸ�ʽ����");
		}
	}
	/**
	 * @param operation
	 * @return
	 */
	public boolean updateOperation(Operation operation){
		if(next) {
			//timePanel�ṩ���������ں�����
			operation.setFunction(timePanel.getFunctionCal(), timePanel.getLastDay());
			operation.setOperationKind(timePanel.getOperationKind());
			//resultPanel�ṩ����ѡ��ķ���
			Room room = resultPanel.getChooseRoom();
			if(room != null)
				operation.setRoomInfo(room);
			else  {
				next = false;
				JOptionPane.showMessageDialog(SearchRoomPanel.this, "����������ѡ�񷿼�");
			}
		}
		return next;
	}
}


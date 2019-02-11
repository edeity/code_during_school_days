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
 * 查询是否有空余房间面板
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
	//这个按钮是为了流程控制用的
	public JButton nextBtn;
	
	private boolean roomIDSearch = false;
	private int num = Room.SINGLE_BED;
	private int level = Room.ECONOMIC_LEVEL;
	
	private boolean next = false;//是否已经完成了搜索工作
	
	public SearchRoomPanel() {
		super();
		
		JRadioButton singleBtn = new JRadioButton("单人间");
		singleBtn.setSelected(true);
		singleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num = Room.SINGLE_BED;
			}
		});
		peopleNumGroup.add(singleBtn);
		
		JRadioButton doubleBtn = new JRadioButton("双人间");
		doubleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num = Room.DOUBLE_BED;
			}
		});
		peopleNumGroup.add(doubleBtn);
		
		JRadioButton economicalBtn = new JRadioButton("经济房");
		economicalBtn.setSelected(true);
		economicalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = Room.ECONOMIC_LEVEL;
			}
		});
		roomLevelGroup.add(economicalBtn);
		
		JRadioButton businessBtn = new JRadioButton("商务房");
		businessBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = Room.BUSSINESS_LEVEL;
			}
		});
		roomLevelGroup.add(businessBtn);
		
		resultPanel = new SpareRoomPanel();

		timePanel = new TimePanel();
		
		JButton searchBtn = new JButton("查询");
		
		//查找功能按钮
		searchBtn.addActionListener(new ActionListener() {
			//根据需求查找房间,1.寻找符合类型的房间 2.寻找空闲的房间
			public void actionPerformed(ActionEvent e) {
				checkAndSearch();
				SearchRoomPanel.this.repaint();//除去多余的画面
			}
		});
		
		nextBtn = new JButton();
		nextBtn.setText("下一步");
		nextBtn.setVisible(false);
		
		roomIDField = new JTextField();
		roomIDField.setColumns(10);
		
		JRadioButton roomIdRB = new JRadioButton("房号查询");
		roomIdRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomIDSearch = true;
			}
		});
		searchKindGroup.add(roomIdRB);
		
		JRadioButton conditionRB = new JRadioButton("条件查询");
		conditionRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomIDSearch = false;
			}
		});
		searchKindGroup.add(conditionRB);
		conditionRB.setSelected(true);
		
		//布局
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
				if(HU.isNotNull(roomID, "房号")) {
					resultPanel.updatePanel(arr.filterByRoomState(Room.searchByID(roomID)));
				}
			}else {
				Room standard = new Room(null, num, level, 0);
				resultPanel.updatePanel(arr.filterByRoomState(Room.searchByCondition(standard)));
			}
			next = true;
		}else {
			JOptionPane.showMessageDialog(SearchRoomPanel.this, "日期格式有误");
		}
	}
	/**
	 * @param operation
	 * @return
	 */
	public boolean updateOperation(Operation operation){
		if(next) {
			//timePanel提供操作的日期和类型
			operation.setFunction(timePanel.getFunctionCal(), timePanel.getLastDay());
			operation.setOperationKind(timePanel.getOperationKind());
			//resultPanel提供最终选择的房间
			Room room = resultPanel.getChooseRoom();
			if(room != null)
				operation.setRoomInfo(room);
			else  {
				next = false;
				JOptionPane.showMessageDialog(SearchRoomPanel.this, "请先搜索并选择房间");
			}
		}
		return next;
	}
}


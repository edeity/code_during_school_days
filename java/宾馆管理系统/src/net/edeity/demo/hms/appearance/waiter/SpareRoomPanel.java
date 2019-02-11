package net.edeity.demo.hms.appearance.waiter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import net.edeity.demo.hms.database.Room;



/**
 * 进行查询时
 * 当有空余房间时,提供对空余房间的选择
 * 当选择了空余的房间后,便可以进行个人信息的登记
 * @author Javer
 * 
 */
@SuppressWarnings("serial")
public class SpareRoomPanel extends JPanel{
	Room chooseRoom;
	/**
	 * 根据房号查询到的空闲房间
	 * @param room
	 */
	public void updatePanel(Room room) {
		this.removeAll();
		if(room != null) {
			JTextArea roomDetail = new JTextArea();
			roomDetail.setText(room.getDetailDescribe());
			this.chooseRoom = room;
			this.add(roomDetail);
		}else {
			JLabel tipLab = new JLabel("对不起,暂时没有空闲的房间");
			this.add(tipLab);
		}
		this.validate();
	}
	/**
	 * 条件查询到的空闲房间
	 * @param bill 账单
	 * @param spareRoomsInfo 空闲房间的信息
	 */
	public void updatePanel(ArrayList<Room> spareRoomsInfo) {
		this.removeAll();
		if(spareRoomsInfo.size() != 0) {
			ButtonGroup spareRoomGroup = new ButtonGroup();
			JLabel tipLab = new JLabel("相应的空闲房间 : ");
			this.add(tipLab);
			for(Room tempRoom : spareRoomsInfo) {
				JRadioButton tempBtn = new JRadioButton(tempRoom.getSimpleDescribe()); 
				tempBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						chooseRoom = tempRoom;
					}
				});
				spareRoomGroup.add(tempBtn);
				this.add(tempBtn);
			}
		}else {
			JLabel tipLab = new JLabel("对不起,暂时没有空闲的房间");
			this.add(tipLab);
		}
		this.validate();
	}
	/**
	 * 确认该面板的取值是正确的
	 */
	public Room getChooseRoom() {
			return chooseRoom;
	}
}

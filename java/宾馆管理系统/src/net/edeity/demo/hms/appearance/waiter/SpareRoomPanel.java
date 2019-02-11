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
 * ���в�ѯʱ
 * ���п��෿��ʱ,�ṩ�Կ��෿���ѡ��
 * ��ѡ���˿���ķ����,����Խ��и�����Ϣ�ĵǼ�
 * @author Javer
 * 
 */
@SuppressWarnings("serial")
public class SpareRoomPanel extends JPanel{
	Room chooseRoom;
	/**
	 * ���ݷ��Ų�ѯ���Ŀ��з���
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
			JLabel tipLab = new JLabel("�Բ���,��ʱû�п��еķ���");
			this.add(tipLab);
		}
		this.validate();
	}
	/**
	 * ������ѯ���Ŀ��з���
	 * @param bill �˵�
	 * @param spareRoomsInfo ���з������Ϣ
	 */
	public void updatePanel(ArrayList<Room> spareRoomsInfo) {
		this.removeAll();
		if(spareRoomsInfo.size() != 0) {
			ButtonGroup spareRoomGroup = new ButtonGroup();
			JLabel tipLab = new JLabel("��Ӧ�Ŀ��з��� : ");
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
			JLabel tipLab = new JLabel("�Բ���,��ʱû�п��еķ���");
			this.add(tipLab);
		}
		this.validate();
	}
	/**
	 * ȷ�ϸ�����ȡֵ����ȷ��
	 */
	public Room getChooseRoom() {
			return chooseRoom;
	}
}

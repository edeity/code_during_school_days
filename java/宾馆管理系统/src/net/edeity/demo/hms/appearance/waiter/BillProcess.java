package net.edeity.demo.hms.appearance.waiter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.edeity.demo.hms.appearance.HotelManageInterface;
import net.edeity.demo.hms.database.Operation;
/**
 * ���ƴӲ�ѯ���䵽ѡ�񷿼������
 * ������ɢ�Ĺ�����ϵ����
 * @author Javer
 */
public class BillProcess {

	public BillProcess(HotelManageInterface hm) {
		//�����˵�
		Operation operation = new Operation();
		//��һ�������������Ľ���
		SearchRoomPanel srp = new SearchRoomPanel();
		hm.updatePanel(srp);
		srp.nextBtn.setVisible(true);
		srp.nextBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(srp.updateOperation(operation)) {
					//�ڶ����Ǹ�����Ϣע��Ľ���
					RegistInfoPanel rip = new RegistInfoPanel();
					hm.updatePanel(rip);
					rip.nextBtn.setVisible(true);
					rip.nextBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(rip.updateOperation(operation)) {
								//���������Ǹ���Ľ���
								PayPanel pp = new PayPanel(operation);
								hm.updatePanel(pp);
							}
						}
					});
				}
			}
		});
	}

}

package net.edeity.demo.hms.appearance.waiter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.edeity.demo.hms.appearance.HotelManageInterface;
import net.edeity.demo.hms.database.Operation;
/**
 * 控制从查询房间到选择房间的流程
 * 即将分散的功能联系起来
 * @author Javer
 */
public class BillProcess {

	public BillProcess(HotelManageInterface hm) {
		//创建账单
		Operation operation = new Operation();
		//第一个界面是搜索的界面
		SearchRoomPanel srp = new SearchRoomPanel();
		hm.updatePanel(srp);
		srp.nextBtn.setVisible(true);
		srp.nextBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(srp.updateOperation(operation)) {
					//第二个是个人信息注册的界面
					RegistInfoPanel rip = new RegistInfoPanel();
					hm.updatePanel(rip);
					rip.nextBtn.setVisible(true);
					rip.nextBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(rip.updateOperation(operation)) {
								//第三个便是付款的界面
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

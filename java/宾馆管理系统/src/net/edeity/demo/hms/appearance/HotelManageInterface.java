package net.edeity.demo.hms.appearance;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.edeity.demo.hms.appearance.manager.InquirePanel;
import net.edeity.demo.hms.appearance.manager.RoomRegisterPanel;
import net.edeity.demo.hms.appearance.manager.WaiterPanel;
import net.edeity.demo.hms.appearance.waiter.BillProcess;
import net.edeity.demo.hms.appearance.waiter.RegistInfoPanel;
import net.edeity.demo.hms.appearance.waiter.SearchRoomPanel;
import net.edeity.demo.hms.database.Delay;
import net.edeity.demo.hms.database.Operation;
import net.edeity.demo.hms.database.Room;
import net.edeity.demo.hms.database.Waiter;

/**
 * ����Ա�������Ľ���,WaiterInterface�ڴ����м�дΪwi
 * @author Javer
 *
 */
@SuppressWarnings("serial")
public class HotelManageInterface extends JFrame{
	private final static int WI_WIDTH = 800;
	private final static int WI_HEIGHT = 600;
	private static boolean isLogin = false;
	private static int rank = Waiter.RANK_OF_MANAGER;
	private JPanel currentFunPanel;//��ǰִ�й��ܵ����

	/**
	 * main������
	 * @param args
	 */
	public static void main(String[] args) {
		HotelManageInterface hm = new HotelManageInterface();
		hm.setLayout(new BorderLayout());
		hm.setSize(WI_WIDTH, WI_HEIGHT);
		hm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		hm.addLoginBar();
		
		hm.setVisible(true);
	}
	
	public void addLoginBar() {

		JMenuBar loginBar = new JMenuBar();
		JMenuBar topBar = new JMenuBar();
		if(!isLogin) {
			JLabel tipLab = new JLabel("  [���ȵ�¼]  ");
			JLabel waiterIDLab = new JLabel("�û�ID");
			JTextField waiterIDField = new JTextField();
			JLabel passwordLab = new JLabel("����");
			JPasswordField passwordField = new JPasswordField();
			JButton ensureBtn = new JButton("ȷ��");
			ensureBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String waiterID = waiterIDField.getText();
					char[] password = passwordField.getPassword();
					Waiter waiter = new Waiter(waiterID);
					isLogin = waiter.login(password);
					if(isLogin) {
						loginBar.removeAll();
						JLabel welcomeLab = new JLabel(waiter.getSimpleDescribe());
						loginBar.add(welcomeLab);
						rank = waiter.getRank();
						if(rank == Waiter.RANK_OF_WAITER) {
							topBar.add(getNormalMenu());
						}
						if(rank == Waiter.RANK_OF_MANAGER) {
							topBar.add(getNormalMenu());
							topBar.add(getManageMenu());
						}
						HotelManageInterface.this.validate();
					}
					else {
						JOptionPane.showMessageDialog(HotelManageInterface.this, "�û������������");
					}
				}
			});
			loginBar.add(tipLab);
			loginBar.add(waiterIDLab);
			loginBar.add(waiterIDField);
			loginBar.add(passwordLab);
			loginBar.add(passwordField);
			loginBar.add(ensureBtn);
		}else {
			//test
			topBar.add(getNormalMenu());
			topBar.add(getManageMenu());
		}
		this.add(loginBar, BorderLayout.SOUTH);
		this.add(topBar, BorderLayout.NORTH);
	}
	
	/**
	 * ���������ѡ��
	 * @param hotelFrm
	 * @return 
	 */
	public  JMenu getNormalMenu() {
		JMenu operateMenu = new JMenu("�������");
		JMenuItem billProcessItem = new JMenuItem("Ԥ����ס");
		JMenuItem searchInItem = new JMenuItem("�����ѯ");//ȷ��ѡ������,�Ȳ�ѯ,�����
		JMenuItem registItem = new JMenuItem("��Ϣ�Ǽ�");
		billProcessItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new BillProcess(HotelManageInterface.this);
			}
		});
		searchInItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchRoomPanel rp = new SearchRoomPanel();
				updatePanel(rp);
			}
		});
		registItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistInfoPanel rip = new RegistInfoPanel();
				updatePanel(rip);
			}
		});
	
		operateMenu.add(billProcessItem);
		operateMenu.add(searchInItem);
		operateMenu.add(registItem);
		
		return operateMenu;
	}
	
	/**
	 * ������Ĳ˵�
	 * @param hotelFrm
	 * @return 
	 */
	public  JMenu getManageMenu() {
		JMenu inquireMenu = new JMenu("����");
		JMenuItem incomeItem = new JMenuItem("�鿴�˵�");
		incomeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Operation operation = new Operation();
				InquirePanel ip = new InquirePanel(operation, 30);
				updatePanel(ip);
			}
		});
		JMenuItem delayItem = new JMenuItem("�鿴�ұ�");
		delayItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Delay delay = new Delay();
				InquirePanel ip = new InquirePanel(delay, 30);
				updatePanel(ip);
			}
		});
		JMenuItem roomItem = new JMenuItem("�鿴����");
		roomItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Room room = new Room();
				InquirePanel ip = new InquirePanel(room, 30);
				updatePanel(ip);
			}
		});
		JMenuItem roomRegistItem = new JMenuItem("����ע��");
		roomRegistItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomRegisterPanel rrp = new RoomRegisterPanel();
				updatePanel(rrp);
			}
		});
		JMenuItem waiterRegistItem = new JMenuItem("����Աע��");
		waiterRegistItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WaiterPanel wp = new WaiterPanel();
				updatePanel(wp);
			}
		});
		
		
		inquireMenu.add(incomeItem);
		inquireMenu.add(delayItem);
		inquireMenu.add(roomItem);
		inquireMenu.add(roomRegistItem);
		inquireMenu.add(waiterRegistItem);

		return inquireMenu;
	}
	
	/**
	 * ��������������Ľ���
	 * @param jp Ҫ���µĽ���, jp����Ϊnull,�����м�Ĳ���Ϊ�հײ���
	 */
	public  void updatePanel(JPanel jp) {
		if(currentFunPanel != null) {
			this.remove(currentFunPanel);
		}
		currentFunPanel = jp;
		this.add(currentFunPanel, BorderLayout.CENTER);
		this.validate();
	}
}

package net.edeity.demo.bigdatacalculator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * ���׼�����
 * @author Javer
 */
public class Interface extends JFrame{
//	4729317598465614985732947 + 438971982578914798473987512
//	23478579847589743589745874 - 489123755428915734894231141
//	31427856425876457198454571 * 748912759814375897489571091
//	47198578947189579817502935 / 431728758917405328740
	private static final long serialVersionUID = 1L;
	Calculator nc;//������;
	private final static int calculator_width = 240;
	private final static int calculator_height =300;
	boolean isResult = false;
	String first_number = "0", operators = "", second_number = "0", result = "0";//�����ֵ
	JPanel num_panel;//���㰴ť������
	JTextArea pre_area, show_area;//��ʾ��ֵ���ı�λ��
	
	public static void main(String[] args) {
		Font font = new Font("����", Font.PLAIN, 15);
		UIManager.put("Button.font", font);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("���÷�����");
		}
		Interface calculator = new Interface();
		calculator.init();
	}
	/**
	 * ��ʼ��������
	 */
	public void init() {
		nc = new Calculator();
		this.setTitle("����������");
		this.setSize(calculator_width, calculator_height);
		this.setBackgroundImage();
		this.setLayout(new BorderLayout());
		
		this.addMenuBar();
		this.addShowArea();
		this.addNumberButton();
		addNumberKeyListerner();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	/**
	 * ���ñ���ͼ��
	 */
	void setBackgroundImage() {
		String bg_image_url = "bg_image.jpg";
		 ((JPanel) this.getContentPane()).setOpaque(false);
//		this.getContentPane().setBackground(Color.green);
        URL url = Interface.class.getResource(bg_image_url);
        ImageIcon bg_img = new ImageIcon(url);
        JLabel background = new JLabel(bg_img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, bg_img.getIconWidth(), bg_img.getIconHeight());
	}
	/**
	 * ���ͷ���˵���
	 */
	private void addMenuBar() {
		JPanel menu_panel = new JPanel();
		menu_panel.setOpaque(false);
		
		menu_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
		JMenuBar menu_bar = new JMenuBar();
		JMenu file_menu = new JMenu("�ļ�");
		JMenuItem file_menu_item = new JMenuItem("��������");
		file_menu_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					catchComputing();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		JMenu help_menu = new JMenu("����");
		JMenuItem help_menu_item = new JMenuItem("����");
		file_menu.add(file_menu_item);
		menu_bar.add(file_menu);
		help_menu.add(help_menu_item);
		menu_bar.add(help_menu);
		menu_panel.add(menu_bar);
		
		this.add(menu_panel, BorderLayout.NORTH);
	}
	/**
	 * �����ֵ��ʾ��
	 */
	private void addShowArea() {
		JPanel show_panel = new JPanel();
		show_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		show_panel.setOpaque(false);
		
		pre_area = new JTextArea(1, 33);
		pre_area.setEditable(false);
		
		show_area = new JTextArea(1, 18);
		show_area.setFont(new Font("����", Font.PLAIN, 25));
		show_area.setEditable(false);
		show_area.setLineWrap(true);

		show_panel.add(pre_area);
		show_panel.add(show_area);
		this.add(show_panel);
	}
	/**
	 * �����ֵ��ť
	 */
	private void addNumberButton() {
		num_panel = new JPanel();
		num_panel.setOpaque(false);
		num_panel.setLayout(new GridLayout(5, 4, 4, 4));
		//����ģ����̵İ�ť
		addFunctionBtn("Cl");
		addFunctionBtn("��");
		addFunctionBtn("%");
		addFunctionBtn("+");
		addNumberBtn("7");
		addNumberBtn("8");
		addNumberBtn("9");
		addFunctionBtn("-");
		addNumberBtn("4");
		addNumberBtn("5");
		addNumberBtn("6");
		addFunctionBtn("*");
		addNumberBtn("1");
		addNumberBtn("2");
		addNumberBtn("3");
		addFunctionBtn("/");
		addNumberBtn("0");
		addFunctionBtn("Ke");
		addFunctionBtn("");
		addFunctionBtn("=");
		this.add(num_panel, BorderLayout.SOUTH);
	}
	/**
	 * �����ֵ��ť
	 * @param title
	 */
	private void addNumberBtn(final String title) {
		JButton tempBtn = new JButton(title);
		num_panel .add(tempBtn);
		tempBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearPreResult();
				show_area.setText(show_area.getText() + title);
			}
		});
	}
	/**
	 * ��ӹ��ܰ�ť
	 * @param title
	 */
	private void addFunctionBtn(final String title) {
		final JButton tempBtn = new JButton(title);
		num_panel.add(tempBtn);
		tempBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String order = tempBtn.getText();
				if(order == "+" || order == "-" || order == "*" || order == "/" || order == "%") {//ִ�����������
					Interface.this.functionOfOperators(order);
				}  else if(order == "=") {
					Interface.this.functionOfEqual();
				} else if(order == "Cl") {
					show_area.setText("");
				} else if(order == "��") {
					String temp = show_area.getText();
					if(!temp.equals(""))//��ֹԭ��û�ж���
						show_area.setText(temp.substring(0, temp.length() - 1));
				}
			}
		});
	}
	
	/**
	 * ���̼���,���������û�л�ý���֮ǰ..�ǲ����з�Ӧ��..
	 */
	private void addNumberKeyListerner() {
		show_area.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				clearPreResult();
				switch (e.getKeyCode()) {
					case KeyEvent.VK_0:	//Ч��ͬVK_NUMPAD0,������KeyCode�ǲ�һ����
					case KeyEvent.VK_NUMPAD0:	show_area.append("0"); break;
					case KeyEvent.VK_1:
					case KeyEvent.VK_NUMPAD1:show_area.append("1"); break;
					case KeyEvent.VK_2:	
					case KeyEvent.VK_NUMPAD2:show_area.append("2"); break;
					case KeyEvent.VK_3:	
					case KeyEvent.VK_NUMPAD3:show_area.append("3"); break;
					case KeyEvent.VK_4:	
					case KeyEvent.VK_NUMPAD4:show_area.append("4"); break;
					case KeyEvent.VK_5:
					case KeyEvent.VK_NUMPAD5:show_area.append("5"); break;
					case KeyEvent.VK_6:	
					case KeyEvent.VK_NUMPAD6:show_area.append("6"); break;
					case KeyEvent.VK_7:
					case KeyEvent.VK_NUMPAD7:show_area.append("7"); break;
					case KeyEvent.VK_8:
					case KeyEvent.VK_NUMPAD8:show_area.append("8"); break;
					case KeyEvent.VK_9:	
					case KeyEvent.VK_NUMPAD9:show_area.append("9"); break;
					case KeyEvent.VK_ADD:	
						Interface.this.functionOfOperators("+"); break;
					case KeyEvent.VK_MINUS://����̼���
					case KeyEvent.VK_SUBTRACT://С���̼���
						Interface.this.functionOfOperators("-"); break;
					case KeyEvent.VK_MULTIPLY:	
						Interface.this.functionOfOperators("*"); break;
					case KeyEvent.VK_DIVIDE:		
						Interface.this.functionOfOperators("/"); break;
					case KeyEvent.VK_ENTER:	
						Interface.this.functionOfEqual(); break;
					case KeyEvent.VK_BACK_SPACE:
						String temp = show_area.getText();
						if(!temp.equals(""))//��ֹԭ��û�ж���
							show_area.setText(temp.substring(0, temp.length() - 1));
					}
			}
		});
	}
	/**
	 * ������ŵĹ���
	 * @param operators �������
	 */
	private void functionOfOperators(String operators) {
		first_number = show_area.getText();
		if(!first_number.equals("")) {
			pre_area.setText(first_number + " " + operators);
			show_area.setText("");
			this.operators = operators;
		} else {
			JOptionPane.showMessageDialog(null, "����������");
		}
	}
	/**
	 * �ȺŵĹ���
	 */
	private void functionOfEqual() {
		if(!isResult) {//��ΰ��Ⱥ�û�з�Ӧ
			second_number = show_area.getText();
			if(operators == "+") {
				result = nc.add(first_number, second_number);
			} else if(operators == "-") {
				result = nc.minus(first_number, second_number);
			} else if(operators == "*") {
				result = nc.multiply(first_number, second_number);
			} else if(operators == "/") {
				result = nc.divide(first_number, second_number);
			} else if(operators == "%") {
				result = nc.complemanate(first_number, second_number);
			}
			isResult = true;
			pre_area.append(" " + second_number);
			show_area.setText(result);
		}
	}
	
	/**
	 * �жϲ������һ�εļ�����
	 */
	private void clearPreResult() {
		if(isResult) {//show_area��������һ�μ���Ľ��,Ӧ�����
			first_number = "0"; operators = ""; second_number = "0";
			pre_area.setText("");
			show_area.setText("");
			isResult = false;
		}
	}
	/**
	 * ��������
	 * @throws IOException 
	 */
	private void catchComputing() throws IOException {
		JFileChooser file_chooser = new JFileChooser();
		file_chooser.showOpenDialog(null);//���������,������ʾ
		File file = file_chooser.getSelectedFile();
		if(file!=null) {//֤���Ѿ�ѡ�����ļ�,��������;�˳�
			try {
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(file);
				String result = "";
				while(scan.hasNext()) {
					first_number = scan.next();
					operators = scan.next();
					second_number = scan.next();
					result += first_number +" " +  operators + " " + second_number+" = ";
					if(operators .equals("+"))//equal ������ ==
						result += nc.add(first_number, second_number);
					else if (operators.equals("-"))
						result += nc.minus(first_number, second_number);
					else if(operators.equals("*"))
						result += nc.multiply(first_number, second_number);
					else if(operators.equals("/"))
						result += nc.divide(first_number, second_number);
					result+="\r\n";
				}
				//��������SCAN֮ǰ����.�õ��ľ�Ȼ�ǿ��ı�
				FileWriter fos = new FileWriter(file);// append = false
				fos.write(result);
				fos.flush();
				fos.close();
				JOptionPane.showMessageDialog(null, "ִ�гɹ�");
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "¼���ļ�����:�Ҳ���ָ���ļ�");
			}	catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "�ļ���ʽ����:��ֵ�к��з���ֵѡ��");
			} catch(NoSuchElementException e) {
				JOptionPane.showMessageDialog(null, "�ļ���ʽ����:���з���ѧʽ��");
			}
		}
	}
}

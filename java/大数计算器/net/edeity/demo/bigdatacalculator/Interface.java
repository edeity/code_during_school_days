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
 * 简易计算器
 * @author Javer
 */
public class Interface extends JFrame{
//	4729317598465614985732947 + 438971982578914798473987512
//	23478579847589743589745874 - 489123755428915734894231141
//	31427856425876457198454571 * 748912759814375897489571091
//	47198578947189579817502935 / 431728758917405328740
	private static final long serialVersionUID = 1L;
	Calculator nc;//就算器;
	private final static int calculator_width = 240;
	private final static int calculator_height =300;
	boolean isResult = false;
	String first_number = "0", operators = "", second_number = "0", result = "0";//运算的值
	JPanel num_panel;//运算按钮的容器
	JTextArea pre_area, show_area;//显示数值的文本位置
	
	public static void main(String[] args) {
		Font font = new Font("宋体", Font.PLAIN, 15);
		UIManager.put("Button.font", font);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("设置风格错误");
		}
		Interface calculator = new Interface();
		calculator.init();
	}
	/**
	 * 初始化计算器
	 */
	public void init() {
		nc = new Calculator();
		this.setTitle("大数计算器");
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
	 * 设置背景图案
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
	 * 添加头顶菜单栏
	 */
	private void addMenuBar() {
		JPanel menu_panel = new JPanel();
		menu_panel.setOpaque(false);
		
		menu_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
		JMenuBar menu_bar = new JMenuBar();
		JMenu file_menu = new JMenu("文件");
		JMenuItem file_menu_item = new JMenuItem("批量计算");
		file_menu_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					catchComputing();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		JMenu help_menu = new JMenu("帮助");
		JMenuItem help_menu_item = new JMenuItem("帮助");
		file_menu.add(file_menu_item);
		menu_bar.add(file_menu);
		help_menu.add(help_menu_item);
		menu_bar.add(help_menu);
		menu_panel.add(menu_bar);
		
		this.add(menu_panel, BorderLayout.NORTH);
	}
	/**
	 * 添加数值显示层
	 */
	private void addShowArea() {
		JPanel show_panel = new JPanel();
		show_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		show_panel.setOpaque(false);
		
		pre_area = new JTextArea(1, 33);
		pre_area.setEditable(false);
		
		show_area = new JTextArea(1, 18);
		show_area.setFont(new Font("宋体", Font.PLAIN, 25));
		show_area.setEditable(false);
		show_area.setLineWrap(true);

		show_panel.add(pre_area);
		show_panel.add(show_area);
		this.add(show_panel);
	}
	/**
	 * 添加数值按钮
	 */
	private void addNumberButton() {
		num_panel = new JPanel();
		num_panel.setOpaque(false);
		num_panel.setLayout(new GridLayout(5, 4, 4, 4));
		//画出模拟键盘的按钮
		addFunctionBtn("Cl");
		addFunctionBtn("←");
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
	 * 添加数值按钮
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
	 * 添加功能按钮
	 * @param title
	 */
	private void addFunctionBtn(final String title) {
		final JButton tempBtn = new JButton(title);
		num_panel.add(tempBtn);
		tempBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String order = tempBtn.getText();
				if(order == "+" || order == "-" || order == "*" || order == "/" || order == "%") {//执行所需的运算
					Interface.this.functionOfOperators(order);
				}  else if(order == "=") {
					Interface.this.functionOfEqual();
				} else if(order == "Cl") {
					show_area.setText("");
				} else if(order == "←") {
					String temp = show_area.getText();
					if(!temp.equals(""))//防止原本没有东西
						show_area.setText(temp.substring(0, temp.length() - 1));
				}
			}
		});
	}
	
	/**
	 * 键盘监听,这个东东在没有获得焦点之前..是不会有反应的..
	 */
	private void addNumberKeyListerner() {
		show_area.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				clearPreResult();
				switch (e.getKeyCode()) {
					case KeyEvent.VK_0:	//效果同VK_NUMPAD0,两个的KeyCode是不一样的
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
					case KeyEvent.VK_MINUS://大键盘减号
					case KeyEvent.VK_SUBTRACT://小键盘减号
						Interface.this.functionOfOperators("-"); break;
					case KeyEvent.VK_MULTIPLY:	
						Interface.this.functionOfOperators("*"); break;
					case KeyEvent.VK_DIVIDE:		
						Interface.this.functionOfOperators("/"); break;
					case KeyEvent.VK_ENTER:	
						Interface.this.functionOfEqual(); break;
					case KeyEvent.VK_BACK_SPACE:
						String temp = show_area.getText();
						if(!temp.equals(""))//防止原本没有东西
							show_area.setText(temp.substring(0, temp.length() - 1));
					}
			}
		});
	}
	/**
	 * 运算符号的功能
	 * @param operators 运算符号
	 */
	private void functionOfOperators(String operators) {
		first_number = show_area.getText();
		if(!first_number.equals("")) {
			pre_area.setText(first_number + " " + operators);
			show_area.setText("");
			this.operators = operators;
		} else {
			JOptionPane.showMessageDialog(null, "请输入数字");
		}
	}
	/**
	 * 等号的功能
	 */
	private void functionOfEqual() {
		if(!isResult) {//多次按等号没有反应
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
	 * 判断并清空上一次的计算结果
	 */
	private void clearPreResult() {
		if(isResult) {//show_area还保留上一次计算的结果,应当清除
			first_number = "0"; operators = ""; second_number = "0";
			pre_area.setText("");
			show_area.setText("");
			isResult = false;
		}
	}
	/**
	 * 批量计算
	 * @throws IOException 
	 */
	private void catchComputing() throws IOException {
		JFileChooser file_chooser = new JFileChooser();
		file_chooser.showOpenDialog(null);//必须有这个,否则不显示
		File file = file_chooser.getSelectedFile();
		if(file!=null) {//证明已经选择了文件,而不是中途退出
			try {
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(file);
				String result = "";
				while(scan.hasNext()) {
					first_number = scan.next();
					operators = scan.next();
					second_number = scan.next();
					result += first_number +" " +  operators + " " + second_number+" = ";
					if(operators .equals("+"))//equal 而不是 ==
						result += nc.add(first_number, second_number);
					else if (operators.equals("-"))
						result += nc.minus(first_number, second_number);
					else if(operators.equals("*"))
						result += nc.multiply(first_number, second_number);
					else if(operators.equals("/"))
						result += nc.divide(first_number, second_number);
					result+="\r\n";
				}
				//这句如果在SCAN之前调用.得到的竟然是空文本
				FileWriter fos = new FileWriter(file);// append = false
				fos.write(result);
				fos.flush();
				fos.close();
				JOptionPane.showMessageDialog(null, "执行成功");
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "录入文件错误:找不到指定文件");
			}	catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "文件格式错误:数值中含有非数值选项");
			} catch(NoSuchElementException e) {
				JOptionPane.showMessageDialog(null, "文件格式错误:含有非数学式子");
			}
		}
	}
}

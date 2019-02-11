package core;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
/**
 * 这个东东使用Swing/awt写的
 * 
 * @author Javer
 * 
 */
public class ClassCreator {
	JFrame myFrame;
	String aimPath = "";//现在的总路径
	int totalChapterNum = 0;
	String currentCharpter = "";//目前录入的什么章节的内容
	String	picOldPath = "", musicOldPath = "", docOldPath = "";//以前的路径
	//现在的子文件
	String coursewareName = "";
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("设置风格错误");
		}
		ClassCreator cc = new ClassCreator();
		cc.init();
	}
	public void init() {
		myFrame = new JFrame();
		// 对myFrame进行设定
		myFrame.setLayout(new BorderLayout());
		myFrame.setSize(400, 400);
		myFrame.setTitle("课件生成器");
		// 实例化各种组件并为他们添加属性
		JPanel classPanel = createClassPanel();
		JPanel configPanel = createConfigPanel();
		JPanel ensurePanel =createEnsurePanel();	
		myFrame.add(classPanel, BorderLayout.NORTH);
		myFrame.add(configPanel, BorderLayout.CENTER);
		myFrame.add(ensurePanel, BorderLayout.SOUTH);
		myFrame.pack();
		// 设置背景
		/*
		 * JLayeredPane lPanel = frame.getLayeredPane(); ImageIcon bgImage = new
		 * ImageIcon("E:/1.jpg"); JLabel label = new JLabel(bgImage);
		 * label.setSize(bgImage.getIconWidth(), bgImage.getIconHeight());//
		 * 如果没有这条会不显示,默认为0,0 lPanel.add(label, new Integer(Integer.MIN_VALUE));
		 */
		myFrame.setVisible(true);
		myFrame.pack();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * 输入课件和章节数,创建文件夹
	 * @return 
	 */
	private JPanel createClassPanel() {
		JLabel cNameLab = new JLabel("输入课件名称");
		final JTextField cNameTf = new JTextField(10);
		JLabel cChapterLab = new JLabel("请输入课件章节");
		final JTextField cChapterTf = new JTextField(10);
		JButton classBtn = new JButton("确定");
		JButton clearBtn = new JButton("清空");
		classBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					coursewareName = cNameTf.getText();
					totalChapterNum = Integer.parseInt(cChapterTf.getText());
					if(coursewareName.equals(""))
						JOptionPane.showMessageDialog(myFrame, "课件名不能为空");
					if(totalChapterNum <=0 || totalChapterNum > 9) 
						JOptionPane.showMessageDialog(myFrame, "数目:章节数必须是大于零并且小于十的数字");
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(myFrame, "格式:章节数必须是大于零小于十的数字");
				}
				JPanel chapterPanel = createChapterPanel(totalChapterNum);
				myFrame.add(chapterPanel, BorderLayout.WEST);
				myFrame.pack();
			}
		});
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalChapterNum = 0;
				coursewareName = "";
				cNameTf.setText("");
				cChapterTf.setText("");
			}
			
		});
		JPanel classPanel = new JPanel();
		classPanel.setLayout(new FlowLayout());
		classPanel.add(cNameLab);
		classPanel.add(cNameTf);
		classPanel.add(cChapterLab);
		classPanel.add(cChapterTf);
		classPanel.add(classBtn);
		classPanel.add(clearBtn);
		return classPanel;
	}
	/**
	 * 创建章节选择的按钮
	 */
	private JPanel createChapterPanel(int chapterNum) {
		GridLayout chapterLayout = new GridLayout(10, 1);
		JPanel chapterPanel = new JPanel(chapterLayout);
		currentCharpter = "第一章";
		for(int i=1; i<=chapterNum; i++) {
			final JButton tempBtn = new JButton("第" + i + "章");
			tempBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					currentCharpter = tempBtn.getText();
				}
			});
			chapterPanel.add(tempBtn);
		}
		return chapterPanel;
	}
	/**
	 * 创建获得资源的panel
	 */
	private JPanel createConfigPanel() {	
		JLabel bgImageLab = new JLabel("背景图片");
		JLabel bgMusicLab = new JLabel("背景音乐");
		JLabel cTextLab = new JLabel("课件文本");
		final JTextField bgImageTf = new JTextField(20);
		final JTextField bgMusicTf = new JTextField(20);
		final JTextField docTf = new JTextField(20);
		JButton bgImageBtn = new JButton("图片游览");
		JButton bgMusicBtn = new JButton("音乐游览");
		JButton cTextBtn = new JButton("课件游览");
		JButton clearBtn = new JButton("清空选择内容");
		
		bgImageTf.setEditable(false);
		bgMusicTf.setEditable(false);
		docTf.setEditable(false);
		
		// 获取要设定的背景图片的路径
		bgImageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser imageChooser = new JFileChooser(new File("C:"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"仅支持jpg, gif", "jpg", "gif");
				imageChooser.setFileFilter(filter);
				int returnVal = imageChooser.showOpenDialog(myFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					picOldPath = imageChooser.getSelectedFile().getAbsolutePath();
					bgImageTf.setText(picOldPath);
				}
			}
		});
		// 获取要设定的背景音乐的路径
		bgMusicBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser bgMusicChooser = new JFileChooser(new File("C:"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"仅支持wav", "wav");
				bgMusicChooser.setFileFilter(filter);
				int returnVal = bgMusicChooser.showOpenDialog(myFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					musicOldPath = bgMusicChooser.getSelectedFile().getAbsolutePath();
					bgMusicTf.setText(musicOldPath);
				}
			}
		});
		// 获取要设定的doc文档的路径
		cTextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser classTextChooser = new JFileChooser(new File("C:"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"仅支持doc", "doc");
				classTextChooser.setFileFilter(filter);
				int returnVal = classTextChooser.showOpenDialog(myFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					docOldPath = classTextChooser.getSelectedFile().getAbsolutePath();
					docTf.setText(docOldPath);
				}
			}
		});
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picOldPath = "";
				musicOldPath = "";
				docOldPath = "";
				bgImageTf.setText("");
				bgMusicTf.setText("");
				docTf.setText("");
			}
		});
		//设置布局管理器
		GridLayout conLayout = new GridLayout(4, 3);
		conLayout.setHgap(10);
		conLayout.setVgap(10);
		JPanel configPanel = new JPanel(conLayout);
		//添加组件
		configPanel.add(bgImageLab);			//图片选择
		configPanel.add(bgImageTf);
		configPanel.add(bgImageBtn);
		configPanel.add(bgMusicLab);		// 音乐选择
		configPanel.add(bgMusicTf);
		configPanel.add(bgMusicBtn);
		configPanel.add(cTextLab);		// 文本内容选择
		configPanel.add(docTf);
		configPanel.add(cTextBtn);
		configPanel.add(clearBtn);
		return configPanel;
	}
	/**
	 * 创建确认信息
	 * @return 
	 */
	private JPanel createEnsurePanel() {
		// 重置所有的信息
		JLabel aimLab = new JLabel("生成路径");
		final JTextField aimTf = new JTextField(10);
		JButton aimBtn = new JButton("选择路径");
		JButton clearBtn = new JButton("清空生成路径");
		JButton startBtn = new JButton("生成数据");
		
		aimTf.setEditable(false);
		//默认生成路径
		aimPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
		aimTf.setText(aimPath);
		// 选择课件的路径
		aimBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser PathChooser = new JFileChooser("C:");
				PathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = PathChooser.showOpenDialog(myFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					aimPath = PathChooser.getSelectedFile().getAbsolutePath();
					aimTf.setText(aimPath);
				}
			}
		});
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aimPath = "";
				aimTf.setText("");
			}
		});
		// !!!!!!!!!!!!!!!!!!!!!!!工作的正式内容!!!!!!!!!!!!!!!!!!!!!!!!!!!
		startBtn.addActionListener(new StartWorkListener());
		JPanel ensurePanel = new JPanel();
		// 选择路径
		ensurePanel.add(aimLab);
		ensurePanel.add(aimTf);
		ensurePanel.add(aimBtn);
		// 重置
		ensurePanel.add(clearBtn);
		// 开始工作
		ensurePanel.add(startBtn);
		return ensurePanel;
	}
	/**
	 * 生成器工作的关键类,实现了ActionListener接口
	 * @author Javer
	 *
	 */
	class StartWorkListener implements ActionListener {
		//课件资源将要迁往的文件夹
		String picParentPath = "", musicParentPath = "", textParentPath = "", configParentPath = "";
		String textAimPath = "",pictureAimPath = "",musicAimPath = "";
		boolean isInit;
		//课件的配置文件
		File configFile = null;
		//复制文件的功能
		FileInputStream fis = null;
		FileOutputStream fos = null;
		/**
		 * 创建必备的文件夹
		 */
		private void createNeccessaryDir() {
			String parentFilePath = aimPath + "\\" + coursewareName;
			picParentPath = parentFilePath + "\\图片文件夹\\";
			musicParentPath = parentFilePath + "\\音乐文件夹\\";
			textParentPath = parentFilePath + "\\文本文件夹\\";
			configParentPath = parentFilePath + "\\基本配置文件\\";
			// 必须配置的文件的文件夹
			new File(aimPath).mkdirs();	
			new File(picParentPath).mkdirs();
			new File(musicParentPath).mkdirs();
			new File(textParentPath).mkdirs();
			new File(configParentPath).mkdirs();
			System.out.println("创建文件夹成功");
		}
		/**
		 * 创建必备的配置文件
		 */
		private void createNeccessaryFile() {
			// 必须配置的文件
			String confilePath = configParentPath + "config.txt";
			try {
				configFile = new File(confilePath);
				configFile.createNewFile();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(myFrame, "由于未知原因,创建配置文件失败");
			}
			System.out.println("创建配置文件成功");
		}
		/**
		 * 将所要资源全部复制到指定文件夹
		 */
		private void copyAllFile() {
			if (!picOldPath.equals("")) {
				pictureAimPath = picParentPath + currentCharpter + ".jpg";
				copyFile(picOldPath, pictureAimPath);
			}
			if (!musicOldPath.equals("")) {
				musicAimPath = musicParentPath + currentCharpter + ".mp3";
				copyFile(musicOldPath, musicAimPath);
			}
			if (!docOldPath.equals("")) {
				textAimPath = textParentPath + currentCharpter + ".doc";
				copyFile(docOldPath, textAimPath);
			}
			System.out.println("复制资源成功");
		}
		/**
		 * 在不同文件夹上写资源的绝对路径
		 */
		private void writeAttitude() {
			try {
				@SuppressWarnings("resource")
				FileWriter confw = new FileWriter(configFile, true);
				StringBuffer sb = new StringBuffer();
				if(!isInit) {
					sb.append("all:" + totalChapterNum).append("\r\n");
					isInit = true;
				}
				sb.append("cha:"+ currentCharpter).append("\r\n");
				sb.append("tex:" + textAimPath).append("\r\n");
				sb.append("pic:" + pictureAimPath).append("\r\n");
				sb.append("mus:" + musicAimPath).append("\r\n");
				confw.write(sb.toString());
				confw.flush();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(myFrame, "写入配置文件失败");
			} 
			System.out.println("写入数据成功");
		}
		
		/**
		 * 把一个文件从oldPath复制到newPath
		 * 
		 * @param oldPath
		 * @param newPath
		 */
		public void copyFile(String oldPath, String newPath) {
			try {
					fis = new FileInputStream(oldPath); // 读入原文件
					fos = new FileOutputStream(newPath);
					int byteread = 0;
					File newFile = new File(newPath);
					// 生成文件夹
					if (!newFile.getParentFile().exists()) {
						newFile.getParentFile().mkdirs();
					}
					byte[] buffer = new byte[1444];
					while ((byteread = fis.read(buffer)) != -1) {
						fos.write(buffer, 0, byteread);
						// fs.write(buffer);该文件出错应该为后面的buffer写入了多余的空格
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 监听功能并实现需要实现的方法
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!isInit) {
				createNeccessaryDir();
				createNeccessaryFile();
			}
			copyAllFile();
			writeAttitude();
			JOptionPane.showMessageDialog(myFrame, "生成 第" + currentCharpter + "课件成功");
		}
	}
}

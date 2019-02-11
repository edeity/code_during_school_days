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
 * �������ʹ��Swing/awtд��
 * 
 * @author Javer
 * 
 */
public class ClassCreator {
	JFrame myFrame;
	String aimPath = "";//���ڵ���·��
	int totalChapterNum = 0;
	String currentCharpter = "";//Ŀǰ¼���ʲô�½ڵ�����
	String	picOldPath = "", musicOldPath = "", docOldPath = "";//��ǰ��·��
	//���ڵ����ļ�
	String coursewareName = "";
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("���÷�����");
		}
		ClassCreator cc = new ClassCreator();
		cc.init();
	}
	public void init() {
		myFrame = new JFrame();
		// ��myFrame�����趨
		myFrame.setLayout(new BorderLayout());
		myFrame.setSize(400, 400);
		myFrame.setTitle("�μ�������");
		// ʵ�������������Ϊ�����������
		JPanel classPanel = createClassPanel();
		JPanel configPanel = createConfigPanel();
		JPanel ensurePanel =createEnsurePanel();	
		myFrame.add(classPanel, BorderLayout.NORTH);
		myFrame.add(configPanel, BorderLayout.CENTER);
		myFrame.add(ensurePanel, BorderLayout.SOUTH);
		myFrame.pack();
		// ���ñ���
		/*
		 * JLayeredPane lPanel = frame.getLayeredPane(); ImageIcon bgImage = new
		 * ImageIcon("E:/1.jpg"); JLabel label = new JLabel(bgImage);
		 * label.setSize(bgImage.getIconWidth(), bgImage.getIconHeight());//
		 * ���û�������᲻��ʾ,Ĭ��Ϊ0,0 lPanel.add(label, new Integer(Integer.MIN_VALUE));
		 */
		myFrame.setVisible(true);
		myFrame.pack();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * ����μ����½���,�����ļ���
	 * @return 
	 */
	private JPanel createClassPanel() {
		JLabel cNameLab = new JLabel("����μ�����");
		final JTextField cNameTf = new JTextField(10);
		JLabel cChapterLab = new JLabel("������μ��½�");
		final JTextField cChapterTf = new JTextField(10);
		JButton classBtn = new JButton("ȷ��");
		JButton clearBtn = new JButton("���");
		classBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					coursewareName = cNameTf.getText();
					totalChapterNum = Integer.parseInt(cChapterTf.getText());
					if(coursewareName.equals(""))
						JOptionPane.showMessageDialog(myFrame, "�μ�������Ϊ��");
					if(totalChapterNum <=0 || totalChapterNum > 9) 
						JOptionPane.showMessageDialog(myFrame, "��Ŀ:�½��������Ǵ����㲢��С��ʮ������");
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(myFrame, "��ʽ:�½��������Ǵ�����С��ʮ������");
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
	 * �����½�ѡ��İ�ť
	 */
	private JPanel createChapterPanel(int chapterNum) {
		GridLayout chapterLayout = new GridLayout(10, 1);
		JPanel chapterPanel = new JPanel(chapterLayout);
		currentCharpter = "��һ��";
		for(int i=1; i<=chapterNum; i++) {
			final JButton tempBtn = new JButton("��" + i + "��");
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
	 * ���������Դ��panel
	 */
	private JPanel createConfigPanel() {	
		JLabel bgImageLab = new JLabel("����ͼƬ");
		JLabel bgMusicLab = new JLabel("��������");
		JLabel cTextLab = new JLabel("�μ��ı�");
		final JTextField bgImageTf = new JTextField(20);
		final JTextField bgMusicTf = new JTextField(20);
		final JTextField docTf = new JTextField(20);
		JButton bgImageBtn = new JButton("ͼƬ����");
		JButton bgMusicBtn = new JButton("��������");
		JButton cTextBtn = new JButton("�μ�����");
		JButton clearBtn = new JButton("���ѡ������");
		
		bgImageTf.setEditable(false);
		bgMusicTf.setEditable(false);
		docTf.setEditable(false);
		
		// ��ȡҪ�趨�ı���ͼƬ��·��
		bgImageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser imageChooser = new JFileChooser(new File("C:"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"��֧��jpg, gif", "jpg", "gif");
				imageChooser.setFileFilter(filter);
				int returnVal = imageChooser.showOpenDialog(myFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					picOldPath = imageChooser.getSelectedFile().getAbsolutePath();
					bgImageTf.setText(picOldPath);
				}
			}
		});
		// ��ȡҪ�趨�ı������ֵ�·��
		bgMusicBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser bgMusicChooser = new JFileChooser(new File("C:"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"��֧��wav", "wav");
				bgMusicChooser.setFileFilter(filter);
				int returnVal = bgMusicChooser.showOpenDialog(myFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					musicOldPath = bgMusicChooser.getSelectedFile().getAbsolutePath();
					bgMusicTf.setText(musicOldPath);
				}
			}
		});
		// ��ȡҪ�趨��doc�ĵ���·��
		cTextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser classTextChooser = new JFileChooser(new File("C:"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"��֧��doc", "doc");
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
		//���ò��ֹ�����
		GridLayout conLayout = new GridLayout(4, 3);
		conLayout.setHgap(10);
		conLayout.setVgap(10);
		JPanel configPanel = new JPanel(conLayout);
		//������
		configPanel.add(bgImageLab);			//ͼƬѡ��
		configPanel.add(bgImageTf);
		configPanel.add(bgImageBtn);
		configPanel.add(bgMusicLab);		// ����ѡ��
		configPanel.add(bgMusicTf);
		configPanel.add(bgMusicBtn);
		configPanel.add(cTextLab);		// �ı�����ѡ��
		configPanel.add(docTf);
		configPanel.add(cTextBtn);
		configPanel.add(clearBtn);
		return configPanel;
	}
	/**
	 * ����ȷ����Ϣ
	 * @return 
	 */
	private JPanel createEnsurePanel() {
		// �������е���Ϣ
		JLabel aimLab = new JLabel("����·��");
		final JTextField aimTf = new JTextField(10);
		JButton aimBtn = new JButton("ѡ��·��");
		JButton clearBtn = new JButton("�������·��");
		JButton startBtn = new JButton("��������");
		
		aimTf.setEditable(false);
		//Ĭ������·��
		aimPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
		aimTf.setText(aimPath);
		// ѡ��μ���·��
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
		// !!!!!!!!!!!!!!!!!!!!!!!��������ʽ����!!!!!!!!!!!!!!!!!!!!!!!!!!!
		startBtn.addActionListener(new StartWorkListener());
		JPanel ensurePanel = new JPanel();
		// ѡ��·��
		ensurePanel.add(aimLab);
		ensurePanel.add(aimTf);
		ensurePanel.add(aimBtn);
		// ����
		ensurePanel.add(clearBtn);
		// ��ʼ����
		ensurePanel.add(startBtn);
		return ensurePanel;
	}
	/**
	 * �����������Ĺؼ���,ʵ����ActionListener�ӿ�
	 * @author Javer
	 *
	 */
	class StartWorkListener implements ActionListener {
		//�μ���Դ��ҪǨ�����ļ���
		String picParentPath = "", musicParentPath = "", textParentPath = "", configParentPath = "";
		String textAimPath = "",pictureAimPath = "",musicAimPath = "";
		boolean isInit;
		//�μ��������ļ�
		File configFile = null;
		//�����ļ��Ĺ���
		FileInputStream fis = null;
		FileOutputStream fos = null;
		/**
		 * �����ر����ļ���
		 */
		private void createNeccessaryDir() {
			String parentFilePath = aimPath + "\\" + coursewareName;
			picParentPath = parentFilePath + "\\ͼƬ�ļ���\\";
			musicParentPath = parentFilePath + "\\�����ļ���\\";
			textParentPath = parentFilePath + "\\�ı��ļ���\\";
			configParentPath = parentFilePath + "\\���������ļ�\\";
			// �������õ��ļ����ļ���
			new File(aimPath).mkdirs();	
			new File(picParentPath).mkdirs();
			new File(musicParentPath).mkdirs();
			new File(textParentPath).mkdirs();
			new File(configParentPath).mkdirs();
			System.out.println("�����ļ��гɹ�");
		}
		/**
		 * �����ر��������ļ�
		 */
		private void createNeccessaryFile() {
			// �������õ��ļ�
			String confilePath = configParentPath + "config.txt";
			try {
				configFile = new File(confilePath);
				configFile.createNewFile();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(myFrame, "����δ֪ԭ��,���������ļ�ʧ��");
			}
			System.out.println("���������ļ��ɹ�");
		}
		/**
		 * ����Ҫ��Դȫ�����Ƶ�ָ���ļ���
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
			System.out.println("������Դ�ɹ�");
		}
		/**
		 * �ڲ�ͬ�ļ�����д��Դ�ľ���·��
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
				JOptionPane.showMessageDialog(myFrame, "д�������ļ�ʧ��");
			} 
			System.out.println("д�����ݳɹ�");
		}
		
		/**
		 * ��һ���ļ���oldPath���Ƶ�newPath
		 * 
		 * @param oldPath
		 * @param newPath
		 */
		public void copyFile(String oldPath, String newPath) {
			try {
					fis = new FileInputStream(oldPath); // ����ԭ�ļ�
					fos = new FileOutputStream(newPath);
					int byteread = 0;
					File newFile = new File(newPath);
					// �����ļ���
					if (!newFile.getParentFile().exists()) {
						newFile.getParentFile().mkdirs();
					}
					byte[] buffer = new byte[1444];
					while ((byteread = fis.read(buffer)) != -1) {
						fos.write(buffer, 0, byteread);
						// fs.write(buffer);���ļ�����Ӧ��Ϊ�����bufferд���˶���Ŀո�
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * �������ܲ�ʵ����Ҫʵ�ֵķ���
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!isInit) {
				createNeccessaryDir();
				createNeccessaryFile();
			}
			copyAllFile();
			writeAttitude();
			JOptionPane.showMessageDialog(myFrame, "���� ��" + currentCharpter + "�μ��ɹ�");
		}
	}
}

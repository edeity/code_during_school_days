package core;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.UIManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ClassReader{
	private final static int SOFTWIDTH = 800;
	private final static int SOFTHEIGHT = 600;
	Display display = null;
	Shell wordShell = null;
	AudioClip ac = null;//music player 
	Composite chapterPanel = null;
	Composite settingPanel = null;
	OleFrame oleFrame = null;
	OleClientSite clientSite = null;;
	int chapterNum = 0;
	ArrayList<String> configContent;//���������ļ�������
	String currentChapter = "";
	String bgImagePath = "";
	String bgMusicPath = "";
	String docFilePath = "";
	
	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (Exception e) {
//			System.out.println("���÷�����");
//		}
		new ClassReader().init();
	}

	private void init() {
		display = Display.getDefault();
		//������������
		wordShell = new Shell();
		wordShell.setSize(SOFTWIDTH, SOFTHEIGHT);
		wordShell.setText("�μ�������");
		//���ֹ�����
		RowLayout rowLayout = new RowLayout();
		rowLayout.pack = true;			//���������ԭ�д�С
		rowLayout.wrap = true;		//��������Ի���
		rowLayout.type = SWT.VERTICAL;			//��ֱ����
		rowLayout.marginLeft = 10;		//��߾�Ϊ30����
		rowLayout.marginRight = 10;
		rowLayout.marginTop = 10;		//�ϱ߾�Ϊ30����
		wordShell.setLayout(rowLayout);	
		//shell �൱�� frame, composite �൱�� panel
		
		createChapterBar();//��ʾ�½ڲ˵�
		// ��ʾExcel�Ĳ˵���
		createWin32Application();
		createSettingBar();//��ʾ���ò˵�

		wordShell.open();
		while (!wordShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.close();
		System.exit(0);
	}
	/**
	 * ˢ��shell
	 */
	@SuppressWarnings("deprecation")
	private void refreshShell() {
		if(!bgImagePath.equals("")) {
			Image image = new Image(display, bgImagePath);
			File file = new File(bgMusicPath);
			try {
				ac = Applet.newAudioClip(file.toURL());
				ac.play();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	    	wordShell.setBackgroundImage(image);
		}
		this.refreshWin32Application();
	}
	@SuppressWarnings("resource")
	private boolean readAttitude(String configFilename) {
		Scanner scan;
		try {
			scan = new Scanner(new File(configFilename));
			configContent = new ArrayList<String>();
			//һ��Ҫ�ϸ���˳��
			chapterNum = Integer.parseInt(scan.nextLine().substring(4));
			while(scan.hasNext()) {
				String tempChapter = scan.nextLine().substring(4);
//				System.out.println(currentChapter);
				String tempDocFile = scan.nextLine().substring(4);
//				System.out.println(docFilePath);
				String tempbgImage = scan.nextLine().substring(4);
//				System.out.println(bgImagePath);
				String tempbgMusic = scan.nextLine().substring(4);
//				System.out.println(bgMusicPath);
				configContent.add(tempChapter);
				configContent.add(tempDocFile);
				configContent.add(tempbgImage);
				configContent.add(tempbgMusic);
			}
			return true;
		} catch (NumberFormatException e1) {
			MessageBox messageBox = new MessageBox(wordShell);
		    messageBox.setMessage("����δ֪ԭ�� , �����ļ��Ѿ���");
		    messageBox.open();
			return false;
		} catch (IOException e) {
			MessageBox messageBox = new MessageBox(wordShell);
		    messageBox.setMessage("IO������");
		    messageBox.open();
			return false;
		}
	}
	/**
	 * ˢ���½���Ŀ
	 */
	private void refreshChapterBar() {
		for(int i=1; i<=chapterNum; i++) {
			//�½ڵİ�ť
			final Button tempChapBtn = new Button(chapterPanel, SWT.PUSH);
			tempChapBtn.setText("�� "+i+"��");
			tempChapBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					String tempIndexString = tempChapBtn.getText().substring(2, 3);
					int tempIndex = Integer.parseInt(tempIndexString);
					try {
						currentChapter = configContent.get((tempIndex - 1) * 4);
						 docFilePath= configContent.get(1 + (tempIndex - 1) * 4);
						 bgImagePath = configContent.get(2 + (tempIndex - 1) * 4);
						 bgMusicPath = configContent.get(3 + (tempIndex - 1) * 4);
						System.out.println(currentChapter);
						System.out.println(bgImagePath);
						System.out.println(bgMusicPath);
						System.out.println(docFilePath);
						refreshShell();
					} catch(IndexOutOfBoundsException e1) {
						//do not thing
					}
				}
			});
		}
		chapterPanel.pack();
		chapterPanel.layout(true);//refresh
	}
	/**
	 * 	�����½ڵ�panel������
	 */
	private void createChapterBar() {
		chapterPanel = new Composite(wordShell, SWT.NONE);
		chapterPanel.setBackground(new Color(null, 80, 80, 80));
		chapterPanel.setLayout(new FillLayout());
		chapterPanel.pack(true);
	}
	/**
	 * ˢ��win32���
	 */
	private  void refreshWin32Application() {
		if(!docFilePath.equals("")) {
			File file = new File(docFilePath);
//			System.out.println(docFilePath);
			clientSite.dispose();
			clientSite = new OleClientSite(oleFrame, SWT.NONE, file);
			clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
		}
	}
	/**
	 * ʹExcelǶ�뵽shell��
	 */
	private void createWin32Application() {
//		OleFrameʵ������һ��Composite�����ڷ���OLE�ؼ� 
		oleFrame = new OleFrame(wordShell, SWT.NO_TRIM);
		oleFrame.setSize(SOFTWIDTH - 40, SOFTHEIGHT - 120);
		clientSite = new OleClientSite(oleFrame, SWT.NONE, "Word.Document");
		clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
//		OleClientSite�ṩһ���������ڰ�OLE����Ƕ�뵽�����У������Excel.Sheet����ʾ��OLE������Excel
//		����progId�����HKEY_LOCAL_MACHINE/SOFTWARE/Classes
//		OleClientSite clientSite = new OleControlSite(oleFrame, SWT.NO_TRIM, "Shell.Explorer");//Excel.Sheet
//		File file = new File(docFilePath);
	}
	/**
	 * �������õ�panel������
	 */
	private void createSettingBar() {
		//���õ�panel
		settingPanel = new Composite(wordShell, SWT.NONE);
		settingPanel.setLayout(new FillLayout());
		//��ȡ�����ļ��İ�ť
		Button readSettingBtn = new Button(settingPanel, SWT.PUSH);
		readSettingBtn.setText("��ѡ��μ��������ļ�(config.txt)");
		readSettingBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				 FileDialog fd = new FileDialog(wordShell);
			     fd.open();
			     String configFilename = fd.getFileName();
			     if(!configFilename.equals("config.txt")) {
		    		MessageBox messageBox = new MessageBox(wordShell);
				    messageBox.setMessage("��ѡ�������ļ�(config.txt)");
				    messageBox.open();
			     } else {
			    	 //�����ļ�
				     String bgConPath = fd.getFilterPath() + "\\" + "config.txt";
				    if( readAttitude(bgConPath)) {
				    	currentChapter = configContent.get(0);
						docFilePath = configContent.get(1);
						bgImagePath = configContent.get(2);
						bgMusicPath = configContent.get(3);
				    	refreshShell();
				    	refreshChapterBar();
				    	refreshWin32Application();
				    }
			     }
			}
		});
		//���õ��ĸ�����
		Button setBgPicBtn = new Button(settingPanel, SWT.PUSH);
		setBgPicBtn.setText("���ñ���ͼƬ");
		setBgPicBtn.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
			     FileDialog fd = new FileDialog(wordShell);
			     String[] extensions = new String[] {"*.jpg"};
			     fd.setFilterExtensions(extensions);
			     fd.open();
			     String bgPicPath = fd.getFilterPath() + "\\" + fd.getFileName();
			     Image image = new Image(display, bgPicPath);
			     wordShell.setBackgroundImage(image);
			}
		});
		Button setBgMusBtn = new Button(settingPanel, SWT.PUSH);
		setBgMusBtn.setText("���ñ�������");
		setBgMusBtn.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			public void mouseDown(MouseEvent e) {
				FileDialog fd = new FileDialog(wordShell);
				String[] extendsions = new String[] {"*.wav"};
				fd.setFilterExtensions(extendsions);
				fd.open();
			   String bgMusPath = fd.getFilterPath() + "\\" + fd.getFileName();
			   File file = new File(bgMusPath);
			   try {
				   ac.stop();
				   ac = Applet.newAudioClip(file.toURL());
				   ac.play();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			}
		});
		Button exitBtn = new Button(settingPanel, SWT.PUSH);
		exitBtn.setText("�˳�����");
		exitBtn.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
			    display.close();
			    System.exit(0);
			}
		});
	}
}
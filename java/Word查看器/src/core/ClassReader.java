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
	ArrayList<String> configContent;//储存配置文件的内容
	String currentChapter = "";
	String bgImagePath = "";
	String bgMusicPath = "";
	String docFilePath = "";
	
	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (Exception e) {
//			System.out.println("设置风格错误");
//		}
		new ClassReader().init();
	}

	private void init() {
		display = Display.getDefault();
		//部署整个窗口
		wordShell = new Shell();
		wordShell.setSize(SOFTWIDTH, SOFTHEIGHT);
		wordShell.setText("课件游览器");
		//布局管理器
		RowLayout rowLayout = new RowLayout();
		rowLayout.pack = true;			//子组件保持原有大小
		rowLayout.wrap = true;		//子组件可以换行
		rowLayout.type = SWT.VERTICAL;			//垂直布置
		rowLayout.marginLeft = 10;		//左边距为30像素
		rowLayout.marginRight = 10;
		rowLayout.marginTop = 10;		//上边距为30像素
		wordShell.setLayout(rowLayout);	
		//shell 相当于 frame, composite 相当于 panel
		
		createChapterBar();//显示章节菜单
		// 显示Excel的菜单栏
		createWin32Application();
		createSettingBar();//显示设置菜单

		wordShell.open();
		while (!wordShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.close();
		System.exit(0);
	}
	/**
	 * 刷新shell
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
			//一定要严格按照顺序
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
		    messageBox.setMessage("由于未知原因 , 配置文件已经损坏");
		    messageBox.open();
			return false;
		} catch (IOException e) {
			MessageBox messageBox = new MessageBox(wordShell);
		    messageBox.setMessage("IO流错误");
		    messageBox.open();
			return false;
		}
	}
	/**
	 * 刷新章节条目
	 */
	private void refreshChapterBar() {
		for(int i=1; i<=chapterNum; i++) {
			//章节的按钮
			final Button tempChapBtn = new Button(chapterPanel, SWT.PUSH);
			tempChapBtn.setText("第 "+i+"章");
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
	 * 	创建章节的panel和内容
	 */
	private void createChapterBar() {
		chapterPanel = new Composite(wordShell, SWT.NONE);
		chapterPanel.setBackground(new Color(null, 80, 80, 80));
		chapterPanel.setLayout(new FillLayout());
		chapterPanel.pack(true);
	}
	/**
	 * 刷新win32组件
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
	 * 使Excel嵌入到shell中
	 */
	private void createWin32Application() {
//		OleFrame实际上是一个Composite，用于放置OLE控件 
		oleFrame = new OleFrame(wordShell, SWT.NO_TRIM);
		oleFrame.setSize(SOFTWIDTH - 40, SOFTHEIGHT - 120);
		clientSite = new OleClientSite(oleFrame, SWT.NONE, "Word.Document");
		clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
//		OleClientSite提供一个场所用于把OLE对象嵌入到容器中，在这里“Excel.Sheet”表示的OLE对象是Excel
//		具体progId请参照HKEY_LOCAL_MACHINE/SOFTWARE/Classes
//		OleClientSite clientSite = new OleControlSite(oleFrame, SWT.NO_TRIM, "Shell.Explorer");//Excel.Sheet
//		File file = new File(docFilePath);
	}
	/**
	 * 创建设置的panel和内容
	 */
	private void createSettingBar() {
		//设置的panel
		settingPanel = new Composite(wordShell, SWT.NONE);
		settingPanel.setLayout(new FillLayout());
		//读取配置文件的按钮
		Button readSettingBtn = new Button(settingPanel, SWT.PUSH);
		readSettingBtn.setText("请选择课件的配置文件(config.txt)");
		readSettingBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				 FileDialog fd = new FileDialog(wordShell);
			     fd.open();
			     String configFilename = fd.getFileName();
			     if(!configFilename.equals("config.txt")) {
		    		MessageBox messageBox = new MessageBox(wordShell);
				    messageBox.setMessage("请选择配置文件(config.txt)");
				    messageBox.open();
			     } else {
			    	 //加载文件
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
		//设置的四个按键
		Button setBgPicBtn = new Button(settingPanel, SWT.PUSH);
		setBgPicBtn.setText("设置背景图片");
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
		setBgMusBtn.setText("设置背景音乐");
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
		exitBtn.setText("退出程序");
		exitBtn.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
			    display.close();
			    System.exit(0);
			}
		});
	}
}
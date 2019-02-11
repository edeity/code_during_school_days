package net.edeity.demo.codecounter;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Method;

/**
 * 可视化的用户窗口（GUI），虽然还是比较简陋
 * 
 * @author javer
 */
public class CodeInterface extends JFrame implements ActionListener {

	CodeReader codeAnalyze;// 利用String 提供的基本方法进行代码行数计算
	String dirOrFile;
	JTextArea textArea;

	public static void main(String[] args) {
		JFrame myFrame = new CodeInterface();
		myFrame.setTitle("代码计算器");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
	}
	
	/**
	 * 界面布局
	 */
	public CodeInterface() {
		setLayout(new BorderLayout());
		JPanel myPanel = new JPanel();

		JButton btnOpen = new JButton("open directory or file");
		JButton btnRefresh = new JButton("refresh");
		btnOpen.setActionCommand("analyze");
		btnRefresh.setActionCommand("refresh");
		btnOpen.addActionListener(this);
		btnRefresh.addActionListener(this);
		myPanel.add(btnOpen);
		myPanel.add(btnRefresh);

		textArea = new JTextArea(12, 25);

		add(myPanel, BorderLayout.NORTH);
		add(textArea, BorderLayout.CENTER);

		this.pack();
	}

	/**
	 * 获得选定的文件或文件夹
	 * 
	 * @Override
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		JFileChooser chooser = new JFileChooser();
		/*
		 * 下面这行代码可以利用为只选择。java问价，但为了能选择文件夹，故将这行代码注释掉 FileNameExtensionFilter
		 * filter = new FileNameExtensionFilter("only for .java", "java");
		 * chooser.setFileFilter(filter); int returnVal =
		 * chooser.showDialog(this, cmd); if(returnVal ==
		 * JFileChooser.APPROVE_OPTION); {
		 */

		if (cmd.equals("analyze")) {
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int r = chooser.showOpenDialog(null);
			if (chooser.getSelectedFile() != null) {
				// dirOrFile = chooser.getSelectedFile().getAbsolutePath();
				File analyzeFile = chooser.getSelectedFile();

				codeAnalyze = new CodeReader();
				codeAnalyze.codeReadAndAnalyze(analyzeFile);

				textArea.setText(codeAnalyze.toString());
			} else {
				System.out.println("the directory or file is not found!");
			}
		} else if (cmd.equals("refresh")) {
			codeAnalyze.initializeAll();
			System.out
					.println("++++++++++++++ initialize ++++++++++++++++++++++++++++");
		}
	}
}

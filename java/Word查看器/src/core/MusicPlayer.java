package core;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.File;
import java.net.MalformedURLException;

public class MusicPlayer extends JFrame implements ActionListener {
    boolean looping = false; 
//    File file1 = new File();
    AudioClip sound1;
    AudioClip chosenClip;

    JButton playButton = new JButton("播放"); 
    JButton loopButton = new JButton("循环播放");    
    JButton stopButton = new JButton("停止"); 
    JLabel status = new JLabel("选择播放文件"); 
    JPanel controlPanel = new JPanel(); 
    Container container = getContentPane(); 

    public static void main(String[] args) {
    	File file = new File("E:\\数据\\[专区]代码控\\JAVA\\workspace\\VM\\软件开发环境课程设计\\src\\core\\30暗涌.wav");
    	try {
			AudioClip ac = Applet.newAudioClip(file.toURL());
			ac.play();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	
    }
    public MusicPlayer(String filename) { 
    	File file1 = new File(filename);
        try {
            sound1 = Applet.newAudioClip(file1.toURL());
            chosenClip = sound1;
        } catch(OutOfMemoryError e){
            System.out.println("内存溢出");
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        playButton.addActionListener(this);
        loopButton.addActionListener(this);
        stopButton.addActionListener(this);
        stopButton.setEnabled(false); 

        controlPanel.add(playButton);
        controlPanel.add(loopButton);
        controlPanel.add(stopButton);

        container.add(controlPanel, BorderLayout.CENTER);
        container.add(status, BorderLayout.SOUTH);

        setSize(300, 130); 
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
    }

public void actionPerformed(ActionEvent event) {
    if (chosenClip == null) {
        status.setText("声音未载入");
        return; 
    }
    Object source = event.getSource(); //获取用户洗涤激活的按钮

    if (source == playButton) {
        stopButton.setEnabled(true); 
        loopButton.setEnabled(true); 
        chosenClip.play(); 
        status.setText("正在播放");
    }

    if (source == loopButton) {
        looping = true;
        chosenClip.loop(); 
        loopButton.setEnabled(false); 
        stopButton.setEnabled(true); 
        status.setText("正在循环播放"); 
    }
    if (source == stopButton) {
        if (looping) {
            looping = false;
            chosenClip.stop(); 
            loopButton.setEnabled(true);
        } else {
            chosenClip.stop();
        }
        stopButton.setEnabled(false); 
        status.setText("停止播放");
      }
    }
}
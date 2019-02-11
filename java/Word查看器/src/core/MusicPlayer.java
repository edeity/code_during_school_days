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

    JButton playButton = new JButton("����"); 
    JButton loopButton = new JButton("ѭ������");    
    JButton stopButton = new JButton("ֹͣ"); 
    JLabel status = new JLabel("ѡ�񲥷��ļ�"); 
    JPanel controlPanel = new JPanel(); 
    Container container = getContentPane(); 

    public static void main(String[] args) {
    	File file = new File("E:\\����\\[ר��]�����\\JAVA\\workspace\\VM\\������������γ����\\src\\core\\30��ӿ.wav");
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
            System.out.println("�ڴ����");
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //�رմ���ʱ�˳�����
    }

public void actionPerformed(ActionEvent event) {
    if (chosenClip == null) {
        status.setText("����δ����");
        return; 
    }
    Object source = event.getSource(); //��ȡ�û�ϴ�Ӽ���İ�ť

    if (source == playButton) {
        stopButton.setEnabled(true); 
        loopButton.setEnabled(true); 
        chosenClip.play(); 
        status.setText("���ڲ���");
    }

    if (source == loopButton) {
        looping = true;
        chosenClip.loop(); 
        loopButton.setEnabled(false); 
        stopButton.setEnabled(true); 
        status.setText("����ѭ������"); 
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
        status.setText("ֹͣ����");
      }
    }
}
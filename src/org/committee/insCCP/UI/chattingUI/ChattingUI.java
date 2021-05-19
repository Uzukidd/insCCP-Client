package org.committee.insCCP.UI.chattingUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;

import org.committee.insCCP.MainManager;
import org.committee.insCCP.UI.LoginNewUI;
import org.committee.insCCP.UI.UITheme;
import org.committee.insCCP.UI.friendUI.Friend;
import org.committee.insCCP.client.utils;
import org.committee.insCCP.fileManager.ChattingRecord;
import org.committee.insCCP.fileManager.ChattingRecordManager;

public class ChattingUI{
	
	public JFrame frame;
	
	public JLabel titleLabel;
	
	public JButton sendButton;
	
	public JButton toolImageButton, toolFileButton;
	
	public JTextArea msgArea;
	
	public JTextArea editArea;
	
	public JScrollPane jScrollPane;
	
	public MainManager callback;
	
	public ChattingPanel chattingPanel;
	
	public Friend friend;
	
	public ArrayList<ChattingRecord> chattingRecords;
	
	public void update() {
		this.frame.setTitle(friend.toString());
		
		this.titleLabel.setText(friend.toString());
		
		this.chattingPanel.friend = friend;
		
		if(friend.state == 1) {
			this.sendButton.setEnabled(true);
			
			this.sendButton.setText("Send");
			
			this.toolImageButton.setEnabled(true);
			
			this.toolFileButton.setEnabled(true);
			
			this.editArea.setEnabled(true);
		} else {
			this.sendButton.setEnabled(false);
			
			this.sendButton.setText("Friend is offline.");
			
			this.toolImageButton.setEnabled(false);
			
			this.toolFileButton.setEnabled(false);
			
			this.editArea.setEnabled(false);
		}
	}

	public ChattingUI(MainManager callback, Friend friend) {
		
		this.friend = friend;
		
		this.callback = callback;
		
		try {
			this.chattingRecords = ChattingRecordManager.readChattingRecord(callback.id, friend.id);
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame = new JFrame();
	
		frame.setBackground(UITheme.ThemeColor1);
		
		Toolkit t = Toolkit.getDefaultToolkit();
		
		Dimension d = t.getScreenSize();
		
		frame.setBounds( (d.width-d.width/3)/2, (d.height-d.height/3)/2, 600, 600);
		
		frame.setIconImage(new ImageIcon(ChattingUI.class.getResource("/assets/ui/icon.png")).getImage());
		
		frame.setResizable(true);
		
		frame.setLayout(new BorderLayout());
		
		frame.add(create_north(), BorderLayout.NORTH);
		
		frame.add(create_south(), BorderLayout.SOUTH);
		
		frame.add(create_center(), BorderLayout.CENTER);
		
		frame.setVisible(true);
		
		this.callback = callback;
		
		this.frame.addWindowListener(new CloseWindow());
		
		this.LoadChattingRecord();
		
		this.update();

	}
	
	public void LoadChattingRecord() {
		
		if(this.chattingRecords == null) return;
		
		for(ChattingRecord chattingRecord : this.chattingRecords) {
			if(chattingRecord.state == 0) {
				this.chattingPanel.addSent(chattingRecord.msg, 1);
			} else if(chattingRecord.state == 1) {
				this.chattingPanel.addReceived(chattingRecord.msg, 1);
			} else if(chattingRecord.state == 2) {
				this.chattingPanel.addSent(chattingRecord.msg, 2);
			} else if(chattingRecord.state == 3) {
				this.chattingPanel.addReceived(chattingRecord.msg, 2);
			} else if(chattingRecord.state == 4) {
				this.chattingPanel.addSent(chattingRecord.msg, 3);
			} else if(chattingRecord.state == 5) {
				this.chattingPanel.addReceived(chattingRecord.msg, 3);
			}
		}
	}
	
	public JPanel create_north() {
		JPanel panel = new JPanel();
		
		panel.setBackground(new Color(255, 255, 255));
		
		panel.setPreferredSize(new Dimension(0, 30));
		
		titleLabel = new JLabel();
		
		titleLabel.setFont(new Font("menlo", Font.BOLD,17));
		
		titleLabel.setForeground(new Color(0.0f, 0.0f, 0.0f));
		
		titleLabel.setText(friend.toString());
		
		panel.add(titleLabel);
		
		return panel;
	}

	public JScrollPane create_center() {
		
		msgArea = new JTextArea();
		
		msgArea.setFont(new Font("menlo", Font.BOLD,17));
		
		chattingPanel = new ChattingPanel(new Friend(callback.id, ""), this.friend);
		
		chattingPanel.setBackground(new Color(255, 255, 255));
		
		jScrollPane = new JScrollPane(chattingPanel);
		
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		jScrollPane.setBackground(new Color(255, 255, 255));
		
		return jScrollPane;
	}

	public JPanel create_south() {
			
		JPanel jp = new JPanel();
		
		jp.setBackground(new Color(255, 255, 255));
		
		jp.setLayout(new BorderLayout());
		
		jp.setPreferredSize(new Dimension(0,200));
		
		editArea = new JTextArea();
		
		editArea.setFont(new Font("menlo", Font.BOLD,17));
		
		sendButton= new JButton("Send");
		
		sendButton.setBounds(0,0,260,30);
		
		sendButton.setFont(new Font("menlo", Font.BOLD,17));
		
		sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		sendButton.setContentAreaFilled(false);
		
		sendButton.setFocusPainted(false);
		
        Action sendAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                
            	if(!editArea.getText().equals("")) sendMsg(editArea.getText(), 1);
                
        		editArea.setText("");
            }
        };
        
        sendButton.addActionListener(sendAction);

        editArea.getInputMap().put(KeyStroke.getKeyStroke('\n', InputEvent.CTRL_DOWN_MASK) , "send");

        editArea.getActionMap().put("send", sendAction);

        jp.add(create_tool(), BorderLayout.NORTH);
        
		jp.add(new JScrollPane(editArea), BorderLayout.CENTER);
		
		jp.add(sendButton, BorderLayout.SOUTH);
		
		return jp;
	}
	

	public JPanel create_tool() {
			
		JPanel jp = new JPanel();
		
		jp.setBackground(new Color(255, 255, 255));
		
		jp.setLayout(new GridLayout(1, 0));
		
		jp.setPreferredSize(new Dimension(0,30));
		
		toolImageButton= new JButton("Image");
		
		toolImageButton.setFont(new Font("menlo", Font.BOLD,17));
		
		toolImageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		toolImageButton.setContentAreaFilled(false);
		
		toolImageButton.setFocusPainted(false);
		
        Action imageAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
        		JFileChooser fd = new JFileChooser();
        		
        		fd.setAcceptAllFileFilterUsed(false);
        		
            	fd.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
            	
                fd.setFileFilter(new FileFilter() {
                    public boolean accept(File f) {
                      if(f.getName().endsWith(".bmp")||f.getName().endsWith(".gif")||f.getName().endsWith(".jpg")||f.getName().endsWith(".png")||f.isDirectory()) return true;
                      
                      return false;
                    }
                    public String getDescription() {
                      return "Picture(*.png/jpg/gif/bmp)";
                    }
                });
                
            	if(fd.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            	
	            	File file = fd.getSelectedFile();
	            	
	            	try {
						if(file != null) sendMsg(utils.Base64Utils.readBase64(file), 2);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	            	
            	}
            	
            }
        };
        
        toolImageButton.addActionListener(imageAction);
		
		jp.add(toolImageButton);
		
		toolFileButton= new JButton("File");
		
		toolFileButton.setFont(new Font("menlo", Font.BOLD,17));
		
		toolFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		toolFileButton.setContentAreaFilled(false);
		
		toolFileButton.setFocusPainted(false);
		
        Action fileAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
        		JFileChooser fd = new JFileChooser();
            	fd.setFileSelectionMode(JFileChooser.OPEN_DIALOG);

            	if (fd.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            		File file =fd.getSelectedFile();
            		if(file.length() > 1024 * 1024 * 10) {
            			callback.errorOccupy("文件大于10M！");
            		} else {
            			
            			sendMsg(file.getName(), 3);
            			
            			new Thread(new Runnable() {
							
							@Override
							public void run() {
								byte[] data;
								try {
									data = utils.FileUtils.readFile(file);
									
									callback.sendFile(friend.id, file.getName(), data);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								
							}
						}).start();
            		}
            	}
            }
        };
        
        toolFileButton.addActionListener(fileAction);
		
		jp.add(toolFileButton);
		
		return jp;
	}
	
	public void sendMsg(String msg, int type) {
		
		try {
			if(type != 3) callback.sendMsg(this.friend.id, msg, type);
			this.chattingPanel.addSent(msg, type);
			if(type == 1) chattingRecords.add(new ChattingRecord(callback.id, "", msg, (byte) 0));
			else if(type == 2) chattingRecords.add(new ChattingRecord(callback.id, "", msg, (byte) 2));
			else if(type == 3) chattingRecords.add(new ChattingRecord(callback.id, "", msg, (byte) 4));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		jScrollPane.getViewport().setViewPosition(new Point(0, jScrollPane.getVerticalScrollBar().getMaximum()));
		
	}
	
	public void receiveMsg(String msg, int type) {
		this.chattingPanel.addReceived(msg, type);
		
		if(type == 1) chattingRecords.add(new ChattingRecord(callback.id, "", msg, (byte) 1));
		else if(type == 2) chattingRecords.add(new ChattingRecord(callback.id, "", msg, (byte) 3));
		else if(type == 3) chattingRecords.add(new ChattingRecord(callback.id, "", msg, (byte) 5));
		
		jScrollPane.getViewport().setViewPosition(new Point(0, jScrollPane.getVerticalScrollBar().getMaximum()));
		
	}
	
	class CloseWindow extends WindowAdapter{

		@Override
        public void windowClosing(WindowEvent e){
			try {
				ChattingRecordManager.saveChattingRecord(callback.id, friend.id, chattingRecords);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			callback.closeChattingPanel(friend);
			dispose();
        }
    }
	
	public void dispose() {
		this.frame.dispose();
	}

}
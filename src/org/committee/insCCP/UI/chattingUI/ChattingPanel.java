package org.committee.insCCP.UI.chattingUI;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import org.committee.insCCP.UI.friendUI.Friend;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChattingPanel extends JPanel {

	private JLabel messageLbl1, userImageLbl1, messageLbl, userImageLbl;

	private JPanel msgPanel1, msgPanel;

	public ParallelGroup seGroup1;

	public SequentialGroup seGroup2;

	public GroupLayout layout;
	
	public Friend friend, self;
	
	public static void main(String[] args) {
		new ChattingPanel(null, null).show();
	}

	public ChattingPanel(Friend friend, Friend self) {
		
		layout = new GroupLayout(this);

		this.setLayout(layout);
		
		this.friend = friend;
		
		this.self = self;

		seGroup1 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

		seGroup2 = layout.createSequentialGroup();

		seGroup2.addContainerGap();
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(seGroup1).addContainerGap()));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(seGroup2));

	}
	
	public void addReceivedImage(String path) {
		
		JPanel msgPanel = LeftArrowBubble.createImage(path);

		seGroup1.addComponent(msgPanel);

		seGroup2.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(msgPanel,
				GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18);
	
		this.updateUI();
	}

	public void addSentImage(String path) {
		JPanel msgPanel = RightArrowBubble.createImage(path);

		seGroup1.addComponent(msgPanel);

		seGroup2.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(msgPanel,
				GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18);
		
		this.updateUI();
	}

	public void addReceived(String text, int type) {
		JPanel msgPanel = null;
		
		if(type == 1) msgPanel = LeftArrowBubble.create(text);
		else if(type == 2) msgPanel = LeftArrowBubble.createImage(text);
		else if(type == 3) msgPanel = LeftArrowBubble.createFile(text, this.self.id, this.friend.id);

		seGroup1.addComponent(msgPanel);

		seGroup2.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(msgPanel,
				GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18);
		
		this.updateUI();
	}

	public void addSent(String text, int type) {
		JPanel msgPanel = null;
		
		if(type == 1) msgPanel = RightArrowBubble.create(text);
		else if(type == 2) msgPanel = RightArrowBubble.createImage(text);
		else if(type == 3) msgPanel = RightArrowBubble.createFile(text);

		seGroup1.addComponent(msgPanel);

		seGroup2.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(msgPanel,
				GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18);
		
		this.updateUI();
	}

}

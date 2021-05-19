package org.committee.insCCP.UI.friendUI;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import org.committee.insCCP.MainManager;
import org.committee.insCCP.UI.LoginNewUI;
import org.committee.insCCP.UI.UIUtil;
import org.committee.insCCP.UI.chattingUI.ChattingUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class FriendUI {
	
	public JFrame frame;
	
	public ArrayList<Friend> FriendsList;
	
	public ArrayList<Friend> ApplyFriendsList;
	
	public DefaultMutableTreeNode rootNode, onLine, offLine;
	
	public MainManager callback;
	
	public JButton addFriendsButton, 
	deleteFriendButton, showApplicationButton;
	
	public boolean isDeleting = false;
	
	public JTree tree;
	
	public FriendUI(MainManager callback) {
		this(new ArrayList<Friend>(), new ArrayList<Friend>(), callback);
	}
	
	
	
	public FriendUI(ArrayList<Friend> FriendsList, ArrayList<Friend> ApplyFriendsList, MainManager callback) {
		
		this.FriendsList = FriendsList;
		
		this.ApplyFriendsList = ApplyFriendsList;
		
		this.callback = callback;
		
		init();
		
		updateFriend();
	}
	
	public void updateFriend() {
		this.onLine.removeAllChildren();
		
		this.offLine.removeAllChildren();
		
		for(Friend friend : this.FriendsList) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(friend);
			if(friend.state == 1) this.onLine.add(node);
			else if(friend.state == 0) this.offLine.add(node);
		}
		
		this.tree.updateUI();
	}
	
	public void init() {
		this.frame = new JFrame("Friends");
		
        this.frame.setSize(300, 300);
        
		this.frame.setIconImage(new ImageIcon(LoginNewUI.class.getResource("/assets/ui/icon.png")).getImage());
        
        this.frame.setLocationRelativeTo(null);
        
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        
        panel.add(create_tool(), BorderLayout.NORTH);

        rootNode = new DefaultMutableTreeNode("Friends");
        
        onLine = new DefaultMutableTreeNode("Online");
        
        rootNode.add(onLine);
        
        offLine = new DefaultMutableTreeNode("Offline");
        
        rootNode.add(offLine);

        this.tree = new JTree(rootNode);

        this.tree.setShowsRootHandles(true);

        this.tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
            	
            	try {
            		Friend friend = (Friend) ((DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent()).getUserObject();
            		
            		if(isDeleting) {
            			
            			ArrayList<Friend> tempArrayList = new ArrayList<Friend>();
            			 
            			tempArrayList.add(new Friend(friend.id, friend.nickName));
            			
            			if(JOptionPane.showConfirmDialog(frame, 
            					"Are you certainly delete this friend?\nWARNING: deleting is IRREVERSIBLE", 
            					"WARNING", 
            					JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) callback.deleteFriends(tempArrayList);
            			
            		} else {
            		
            			callback.openChattingPanel(friend);
                	
            		}
            	} catch(Exception e1) {

            	}
                
            }
        });

        panel.add(new JScrollPane(tree), BorderLayout.CENTER);

        this.frame.setContentPane(panel);
        
	}
	
	public JPanel create_tool() {
		
		JPanel jp = new JPanel();
		
		jp.setBackground(new Color(255, 255, 255));
		
		jp.setLayout(new GridLayout(1, 0));
		
		jp.setPreferredSize(new Dimension(0,30));
		
		addFriendsButton = UIUtil.createButton("Add");
		
		addFriendsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addFriendInput();
			}
			
		});
        
		jp.add(addFriendsButton);
		
		deleteFriendButton = UIUtil.createButton("Delete");
		
		deleteFriendButton.addActionListener(new ActionListener() {
			
			public Color switchColor = new Color(0.5f, 0.0f, 0.0f);
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				isDeleting = !isDeleting;
				
				Color tempColor = deleteFriendButton.getForeground();
				
				deleteFriendButton.setForeground(switchColor);
				
				switchColor = tempColor;
				
			}
		});

		jp.add(deleteFriendButton);
		
		showApplicationButton = UIUtil.createButton("Applications");
		
		showApplicationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				applicationList();
			}
		});
		
		jp.add(showApplicationButton);
		
		return jp;
	}
	
	public void addFriendInput() {
		
		JDialog dialog = new JDialog(this.frame,"Add a new friend via id",true);

		dialog.setBounds(400,200,350,150);
		
		dialog.setLayout(new BorderLayout());
		
		JButton confirm  = UIUtil.createButton("Confirm");
		
		JTextField idFieldArea = new JTextField();
		
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(idFieldArea.getText().equals("")) return;
				
				ArrayList<Friend> tempArrayList = new ArrayList<>();
				
				tempArrayList.add(new Friend(idFieldArea.getText(), ""));
				
				callback.addFriends(tempArrayList);
				
				UIUtil.showMsgMsg("An application has been sent!", "OK", frame);
				
				dialog.setVisible(false);
			}
			
		});
		
		idFieldArea.addKeyListener(new KeyAdapter(){ 
			
		      public void keyPressed(KeyEvent e)    
		      {
		        if(e.getKeyChar()==KeyEvent.VK_ENTER )
		        {
		        	confirm.doClick();
		        } 
		      } 
		});
		
		idFieldArea.setFont(new Font("menlo", Font.BOLD,17));
		
		dialog.add(idFieldArea, BorderLayout.NORTH);
		
		dialog.add(confirm, BorderLayout.SOUTH);
		
		dialog.setLocationRelativeTo(this.frame);
		
		dialog.setVisible(true);

	}
	
	public void applicationList() {
		
		JDialog dialog = new JDialog(this.frame,"Applications",true);
		
		dialog.setBounds(400,200,350,500);
		
		dialog.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(0, 1));
		
		for(Friend friend : this.ApplyFriendsList) {
			
			JButton tempButton = UIUtil.createButton(new Friend(friend.id, friend.nickName).toString());
			
			JPanel tempPanel = new JPanel();
			
			tempPanel.setLayout(new BorderLayout());
			
			tempButton.setBounds(0, 0, 0, 40);
			
			tempButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ArrayList<Friend> tempArrayList = new ArrayList<>();
					
					String[] tempStrings = tempButton.getText().split("@");
					
					panel.remove(tempPanel);
					
					panel.updateUI();
					
					ApplyFriendsList.remove(tempButton.getText());
					
					tempArrayList.add(new Friend(tempStrings[1],tempStrings[0]));
					
					callback.addFriends(tempArrayList);
				}
			});
			
			tempPanel.add(tempButton, BorderLayout.NORTH);
			
			panel.add(tempPanel);

		}
		
		dialog.add(new JScrollPane(panel), BorderLayout.NORTH);
		
		dialog.setLocationRelativeTo(this.frame);
		
		dialog.setVisible(true);

	}
	
	public static void main(String[] args) {
		new FriendUI(null).frame.setVisible(true);;
	}

}
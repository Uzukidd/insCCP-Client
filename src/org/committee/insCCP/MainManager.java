package org.committee.insCCP;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.committee.insCCP.UI.friendUI.Friend;
import org.committee.insCCP.UI.friendUI.FriendUI;
import org.committee.insCCP.UI.LoginNewUI;
import org.committee.insCCP.UI.UIUtil;
import org.committee.insCCP.UI.chattingUI.ChattingUI;
import org.committee.insCCP.client.MsgClient;
import org.committee.insCCP.fileManager.FileWindows;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class MainManager {
	
	public MsgClient client;
	
	public LoginNewUI loggingLoginNewUI;
	
	public FriendUI friendUI;
	
	public ArrayList<Friend> friendList;
	
	public ArrayList<Friend> applyFriendList;
	
	public HashMap<String, ChattingUI> chattingPanel;
	
	public String id;
	
	public boolean login(String id, String password) throws Exception {
		client.login(id, password);
		
		this.id = id;

		this.friendUI.frame.setVisible(true);
		return true;
	}
	
	public boolean sendFile(String id, String name, byte[] file) {
		try {
			this.client.sendFile(id, name, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean openChattingPanel(Friend friend) {
		boolean res = true;
		
		if(chattingPanel.get(friend.id) != null) res = false;
		else {
			chattingPanel.put(friend.id, new ChattingUI(this, friend));
		}
		
		return res;
	}
	
	public void updateFriendList(ArrayList<Friend> friendList) {
		this.friendUI.FriendsList = friendList;
		
		for(Friend friend : friendList) {
			if(this.chattingPanel.get(friend.id) != null) {
				this.chattingPanel.get(friend.id).friend = friend;
				
				this.chattingPanel.get(friend.id).update();
			}
		}
		
		this.friendUI.updateFriend();
	}
	
	public void updateApplyFriendList(ArrayList<Friend> ApplyFriendsList) {
		this.friendUI.ApplyFriendsList = ApplyFriendsList;
	}
	
	public boolean closeChattingPanel(Friend friend) {
		boolean res = true;
		
		if(chattingPanel.get(friend.id) == null) res = false;
		else {
			chattingPanel.remove(friend.id);
		}
		
		return res;
	}
	
    public static void Notice() {

    	try {

            File file = new File(MainManager.class.getResource("/assets/music/notice.mp3").getFile());

    		FileInputStream fis = new FileInputStream(file);
    		
    		BufferedInputStream stream = new BufferedInputStream(fis);
    		
    		Player player = new Player(stream);
    		
    		player.play();

    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}

    }
	
	public void receivedMsg(String id, String msg, int type) throws Exception {

		if(chattingPanel.get(id) == null) {
			Friend sender = new Friend(id, "Anonymous");
			for(Friend fri : this.friendUI.FriendsList) {
				if(fri.id.equals(id)) {
					sender.nickName = fri.nickName;
					sender.state = 1;
					break;
				}
			}
			chattingPanel.put(id, new ChattingUI(this, sender));
		}
		
		chattingPanel.get(id).receiveMsg(msg, type);
		
		Notice();
		
	}
	
	public boolean sendMsg(String id, String msg, int type) throws Exception {
		
		client.sendMsg(id, msg, type);
		
		return true;
	}
	
	public boolean addFriends(ArrayList<Friend> friends) {
		
		try {
			this.client.addFriends(friends);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean deleteFriends(ArrayList<Friend> friends) {
		
		try {
			this.client.deleteFriends(friends);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void exit() {
		try {
			this.client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

        System.exit(0);
	}
	
	public MainManager() throws IOException {
		init();
		
		this.loggingLoginNewUI = new LoginNewUI(this);

	}
	
	public void init() throws IOException {
		
		this.friendList = new ArrayList<Friend>();
		
		this.applyFriendList = new ArrayList<Friend>();
		
		this.client = new MsgClient(this, "127.0.0.1", 19170);
		
		this.friendUI = new FriendUI(this.friendList, this.applyFriendList, this);
		
		this.chattingPanel = new HashMap<String, ChattingUI>();
	}

	public static void main(String[] args) throws IOException {
		
		MainManager mainManager = new MainManager();
    	
	}

	public void errorOccupy(final String msg) {
		
		new Thread() {
			@Override
			public void run() {
				UIUtil.showErrorMsg(msg, "Error", friendUI.frame);
			}
		}.start();
		
	}

}

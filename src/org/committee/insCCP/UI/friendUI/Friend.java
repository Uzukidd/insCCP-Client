package org.committee.insCCP.UI.friendUI;

public class Friend {
	
	public String id;
	
	public String nickName;
	
	public int state;
	
	public Friend(String id, String nickName) {
		this(id, nickName, -1);
	}
	
	public Friend(String id, String nickName, int state) {
		this.id = id;
		
		this.nickName = nickName;
		
		this.state = state;
	}
	
	@Override
	public String toString() {
		if(this.state == -1)
		return this.nickName + "@" + this.id;
		else if(this.state == 1)
		return this.nickName + "@" + this.id + " (online)";
		else if(this.state == 0)
		return this.nickName + "@" + this.id + " (offline)";

		return this.nickName + "@" + this.id + " (" + this.state + ")";
	}
}

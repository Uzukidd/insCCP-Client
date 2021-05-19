package org.committee.insCCP.fileManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.nio.charset.Charset;

import org.committee.insCCP.client.utils;

public class ChattingRecord {
	public String id;
	
	public String nickName;
	
	public String msg;
	
	public byte state;
	//0 for self
	//1 for opposite
	
	public ChattingRecord(String id, String nickName, String msg, byte state) {
		this.id = id;
		
		this.nickName = nickName;
		
		this.msg = msg;
		
		this.state = state;
	}
	
	public ChattingRecord() {
		this("", "", "", (byte) 0);
	}
	
	public void toFile(DataOutputStream stream) throws IOException {
		
		stream.writeByte(this.state);
		
		stream.writeInt(this.msg.getBytes(Charset.forName("UTF-8")).length);
		
		stream.write(this.msg.getBytes(Charset.forName("UTF-8")));
		
	}
	
	public static ChattingRecord toData(DataInputStream stream) throws IOException {
		
		ChattingRecord resChattingRecord = new ChattingRecord();
		
		resChattingRecord.state = stream.readByte();
		
		byte[] msg = new byte[stream.readInt()];
		
		stream.read(msg);
		
		resChattingRecord.msg = new String(msg,Charset.forName("UTF-8"));
		
		return resChattingRecord;
	}

}

package org.committee.insCCP.UI;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UIUtil {
	
	public static void showErrorMsg(String msg, String title, JFrame frame) {
		JOptionPane.showMessageDialog(frame, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showMsgMsg(String msg, String title, JFrame frame) {
		JOptionPane.showMessageDialog(frame, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static JButton createButton(String text) {
		JButton button = new JButton(text);
		
		button.setFont(new Font("menlo", Font.BOLD,17));
		
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		button.setContentAreaFilled(false);
		
		button.setFocusPainted(false);
		
		return button;
	}
	

}

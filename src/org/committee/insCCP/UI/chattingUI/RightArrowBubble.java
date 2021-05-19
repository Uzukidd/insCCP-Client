package org.committee.insCCP.UI.chattingUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.committee.insCCP.MainManager;
import org.committee.insCCP.UI.UITheme;
import org.committee.insCCP.UI.UIUtil;
import org.committee.insCCP.client.utils;

public class RightArrowBubble extends JPanel {

   private int strokeThickness = 3;
   
   private int radius = 5;
   
   private int arrowSize = 12;
   
   private int padding = strokeThickness / 2;
   
   @Override
   protected void paintComponent(final Graphics g) {
	   
      final Graphics2D g2d = (Graphics2D) g;
      
      g2d.setColor(new Color(0.5f, 0.0f, 0.0f));
      
      int bottomLineY = getHeight() - strokeThickness;
      
      int width = getWidth() - arrowSize - (strokeThickness * 2);
      
      g2d.fillRect(padding, padding, width, bottomLineY);
      
      RoundRectangle2D.Double rect = new RoundRectangle2D.Double(padding, padding, width, bottomLineY,  radius, radius);
      
      Polygon arrow = new Polygon();
      
      arrow.addPoint(width, 8);
      
      arrow.addPoint(width + arrowSize, 10);
      
      arrow.addPoint(width, 12);
      
      Area area = new Area(rect);
      
      area.add(new Area(arrow));
      
      g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
      
      g2d.setStroke(new BasicStroke(strokeThickness));
      
      g2d.draw(area);
   }
   
	public static RightArrowBubble create(String msg) {
	    
	    RightArrowBubble msgPanel = new RightArrowBubble();
	    
	    JTextArea text = new JTextArea();
	    
	    text.setEditable(false);
	    
	    text.setBackground(UITheme.ThemeColor1);
	    
	    text.setFont(new Font("menlo", Font.BOLD,15));
	    
	    text.setForeground(new Color(1.0f, 1.0f, 1.0f));
	    
	    text.setText(msg);
	    
	    JPanel rightPanel = new JPanel();
	    
	    rightPanel.setBackground(new Color(0, 0,  0, 0));
	    
	    msgPanel.setLayout(new BorderLayout());
	    
	    rightPanel.add(text);
	    
	    JPanel tempPanel = new JPanel();
	    
	    tempPanel.setBackground(new Color(0, 0, 0, 0));
	    
	    rightPanel.add(tempPanel);
	    
	    msgPanel.add(rightPanel, BorderLayout.EAST);
	    
	    return msgPanel;
	}
	
	public static RightArrowBubble createFile(String name) {
		RightArrowBubble msgPanel = new RightArrowBubble();
	    
	    ImageIcon image;
	    
		image = new ImageIcon(MainManager.class.getResource("/assets/ui/fileWhite.png"));
		
		if(image == null) return null;
	    
       if(image.getIconHeight() > image.getIconWidth()) {
    	   
    	   if(image.getIconHeight() > 100) {
    		   image.setImage(image.getImage().getScaledInstance(image.getIconWidth() * 100 / image.getIconHeight(), 100, Image.SCALE_DEFAULT));
    	   }
    	   
       } else {
    	   if(image.getIconWidth() > 100) {
    		   image.setImage(image.getImage().getScaledInstance(100, image.getIconHeight() * 100 / image.getIconWidth(), Image.SCALE_DEFAULT));
    	   }
       }
		
		JLabel text = new JLabel(image);
		
		text.setText(name);
	
		text.setFont(new Font("menlo", Font.BOLD,15));
		
		text.setForeground(new Color(1.0f, 1.0f, 1.0f));
		
	    GroupLayout jPanel1Layout = new GroupLayout(msgPanel);
	    
	    msgPanel.setLayout(jPanel1Layout);
	    
	    jPanel1Layout.setHorizontalGroup(
	        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	            .addContainerGap(171, Short.MAX_VALUE)
	            .addComponent(text)
	            .addGap(22, 22, 22))
	    );
	    
	    jPanel1Layout.setVerticalGroup(
	        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	        .addGroup(jPanel1Layout.createSequentialGroup()
	            .addComponent(text)
	            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	    );
	    
	    return msgPanel;
	}
	
	public static RightArrowBubble createImage(String base64) {
		
	    RightArrowBubble msgPanel = new RightArrowBubble();
	    
	    ImageIcon image;
	    
		image = utils.Base64Utils.base642Image(base64);
		
		if(image == null) return null;
	    
       if(image.getIconHeight() > image.getIconWidth()) {
    	   
    	   if(image.getIconHeight() > 500) {
    		   image.setImage(image.getImage().getScaledInstance(image.getIconWidth() * 500 / image.getIconHeight(), 500, Image.SCALE_DEFAULT));
    	   }
    	   
       } else {
    	   if(image.getIconWidth() > 500) {
    		   image.setImage(image.getImage().getScaledInstance(500, image.getIconHeight() * 500 / image.getIconWidth(), Image.SCALE_DEFAULT));
    	   }
       }
		
		JLabel text = new JLabel(image);
	
	    GroupLayout jPanel1Layout = new GroupLayout(msgPanel);
	    
	    msgPanel.setLayout(jPanel1Layout);
	    
	    jPanel1Layout.setHorizontalGroup(
	        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	            .addContainerGap(171, Short.MAX_VALUE)
	            .addComponent(text)
	            .addGap(22, 22, 22))
	    );
	    
	    jPanel1Layout.setVerticalGroup(
	        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	        .addGroup(jPanel1Layout.createSequentialGroup()
	            .addComponent(text)
	            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	    );
	    
	    return msgPanel;
	      
	   }
}

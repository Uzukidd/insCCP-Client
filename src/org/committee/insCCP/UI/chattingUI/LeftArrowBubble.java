package org.committee.insCCP.UI.chattingUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
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
import org.committee.insCCP.UI.friendUI.Friend;
import org.committee.insCCP.client.utils;
import org.committee.insCCP.fileManager.FileWindows;

public class LeftArrowBubble extends JPanel {

   private int radius = 5;
   
   private int arrowSize = 12;
   
   private int strokeThickness = 3;
   
   private int padding = strokeThickness / 2;
   
   @Override
   protected void paintComponent(final Graphics g) {
      final Graphics2D g2d = (Graphics2D) g;
      
      g2d.setColor(new Color(0.8f, 0.8f, 0.8f));
      
      int x = padding + strokeThickness + arrowSize;
      
      int width = getWidth() - arrowSize - (strokeThickness * 2);
      
      int bottomLineY = getHeight() - strokeThickness;
      
      g2d.fillRect(x, padding, width, bottomLineY);
      
      g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON));
      
      g2d.setStroke(new BasicStroke(strokeThickness));
      
      RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, padding, width, bottomLineY, radius, radius);
      
      Polygon arrow = new Polygon();
      
      arrow.addPoint(20, 8);
      
      arrow.addPoint(0, 10);
      
      arrow.addPoint(20, 12);
      
      Area area = new Area(rect);
      
      area.add(new Area(arrow));
      
      g2d.draw(area);
   }
   
   public static LeftArrowBubble create(String msg) {
       
	   LeftArrowBubble msgPanel = new LeftArrowBubble();
	   
	   JTextArea text = new JTextArea();
	   
	   text.setEditable(false);
	    
	   text.setBackground(new Color(0.8f, 0.8f, 0.8f));
		
	   text.setFont(new Font("menlo", Font.BOLD,15));
		
	   text.setForeground(new Color(0.0f, 0.0f, 0.0f));
		
	   text.setText(msg);
		
	   JPanel rightPanel = new JPanel();
		
	   rightPanel.setBackground(new Color(0, 0,  0, 0));
		
	   msgPanel.setLayout(new BorderLayout());
	   
	   JPanel tempPanel = new JPanel();
		
	   tempPanel.setBackground(new Color(0, 0, 0, 0));
		
	   rightPanel.add(tempPanel);
		
	   rightPanel.add(text);
		
	   msgPanel.add(rightPanel, BorderLayout.WEST);
   
       return msgPanel;
   }
   
   public static LeftArrowBubble createImage(String base64) {
       
       LeftArrowBubble msgPanel = new LeftArrowBubble();
       
       ImageIcon image;
	    
       image = utils.Base64Utils.base642Image(base64);
	
       if(image == null) return null;
       
       JLabel text = new JLabel(image);
       
       if(image.getIconHeight() > image.getIconWidth()) {
    	   
    	   if(image.getIconHeight() > 500) {
    		   image.setImage(image.getImage().getScaledInstance(image.getIconWidth() * 500 / image.getIconHeight(), 500, Image.SCALE_DEFAULT));
    	   }
    	   
       } else {
    	   if(image.getIconWidth() > 500) {
    		   image.setImage(image.getImage().getScaledInstance(500, image.getIconHeight() * 500 / image.getIconWidth(), Image.SCALE_DEFAULT));
    	   }
       }

       GroupLayout msgPanelLayout = new GroupLayout(msgPanel);
       
       msgPanel.setLayout(msgPanelLayout);
       
       msgPanelLayout.setHorizontalGroup(
           msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
           .addGroup(msgPanelLayout.createSequentialGroup()
               .addGap(21, 21, 21)
               .addComponent(text)
               .addContainerGap(162, Short.MAX_VALUE))
       );
       
       msgPanelLayout.setVerticalGroup(
       		
           msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
           .addGroup(msgPanelLayout.createSequentialGroup()
               .addComponent(text)
               .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
       );
       
       return msgPanel;
   }
   
   public static LeftArrowBubble createFile(String name, String srcId, String dstId) {
       
       LeftArrowBubble msgPanel = new LeftArrowBubble();
       
       ImageIcon image;
	    
       image = new ImageIcon(MainManager.class.getResource("/assets/ui/fileBlack.png"));
		
       if(image == null) return null;
       
       JButton text = UIUtil.createButton(name);
       
       text.setBorderPainted(false);
       
       text.setBorder(null);
       
       text.setIcon(image);
       
       text.setText(name);
       
       text.setFont(new Font("menlo", Font.BOLD,15));
       
       text.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		
	   text.setForeground(new Color(0.0f, 0.0f, 0.0f));
	   
	   text.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Desktop desk=Desktop.getDesktop();  
			try {  
			    desk.open(new File(FileWindows.getDefaultDirectory() + "\\" + srcId + "\\Downloads\\" + name));
			} catch(Exception e1) {  
				e1.printStackTrace();
			}  
			
		}
	});
       
       if(image.getIconHeight() > image.getIconWidth()) {
    	   
    	   if(image.getIconHeight() > 100) {
    		   image.setImage(image.getImage().getScaledInstance(image.getIconWidth() * 100 / image.getIconHeight(), 100, Image.SCALE_DEFAULT));
    	   }
    	   
       } else {
    	   if(image.getIconWidth() > 100) {
    		   image.setImage(image.getImage().getScaledInstance(100, image.getIconHeight() * 100 / image.getIconWidth(), Image.SCALE_DEFAULT));
    	   }
       }

       GroupLayout msgPanelLayout = new GroupLayout(msgPanel);
       
       msgPanel.setLayout(msgPanelLayout);
       
       msgPanelLayout.setHorizontalGroup(
           msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
           .addGroup(msgPanelLayout.createSequentialGroup()
               .addGap(21, 21, 21)
               .addComponent(text)
               .addContainerGap(162, Short.MAX_VALUE))
       );
       
       msgPanelLayout.setVerticalGroup(
       		
           msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
           .addGroup(msgPanelLayout.createSequentialGroup()
               .addComponent(text)
               .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
       );
       
       return msgPanel;
   }
}

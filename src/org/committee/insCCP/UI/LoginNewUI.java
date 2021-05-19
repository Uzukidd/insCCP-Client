package org.committee.insCCP.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.committee.insCCP.MainManager;

public class LoginNewUI implements KeyListener {
	
	private JFrame frame;
	
	private JButton loginButton;
	
	private JPanel northPanel, 
				westPanel, 
				centerPanel, 
				eastPanel, 
				southPanel;
	
	private JLabel forgetLabel, registerLabel;
	
	private JTextField idField;
	
	private JPasswordField passwordField;
	
	private MainManager callback;
	
	private boolean loggedin = false;

	public LoginNewUI(MainManager callback) {

		frame=new JFrame("insCCP Sign In");
	
		frame.setBackground(UITheme.ThemeColor1);
		
		Toolkit t = Toolkit.getDefaultToolkit();
		
		Dimension d = t.getScreenSize();
		
		frame.setBounds((d.width-d.width/3)/2,(d.height-d.height/3)/2,510,380);
		
		frame.setIconImage(new ImageIcon(LoginNewUI.class.getResource("/assets/ui/icon.png")).getImage());
		
		frame.setResizable(false);
		
		northPanel=creat_north();
		
		westPanel=creat_west();
		
		centerPanel=creat_center();
		
		southPanel=creat_south();
		
		eastPanel=creat_east();
		
		frame.add(northPanel,BorderLayout.NORTH);
		
		frame.add(westPanel,BorderLayout.WEST);
		
		frame.add(southPanel,BorderLayout.SOUTH);
		
		frame.add(centerPanel,BorderLayout.CENTER);
		
		frame.add(eastPanel,BorderLayout.EAST);
		
		frame.setVisible(true);
		
		this.callback = callback;
		
		this.frame.addWindowListener(new CloseWindow());

	}

	public JPanel creat_north() {
		
		JPanel jp = new JPanel();
		
		jp.setBackground(new Color(255, 255, 255));
		
		jp.setLayout(null);
		
		jp.setPreferredSize(new Dimension(0,190));
		
		ImageIcon in = new ImageIcon(LoginNewUI.class.getResource("/assets/ui/logo.png"));
		
		JLabel cc=new JLabel(in);
		
		cc.setBounds(0,0,500,190);
		
		cc.setOpaque(false);
		
		jp.add(cc);
		
		return jp;
	}

	public JPanel creat_west() {
		
		JPanel jp = new JPanel();
		
		jp.setBackground(new Color(255, 255, 255));
		
		jp.setLayout(null);
		
		jp.setPreferredSize(new Dimension(160,0));
		
		ImageIcon ss = new ImageIcon(LoginNewUI.class.getResource("/assets/ui/headDefault.png"));
		
		ss.setImage(ss.getImage().getScaledInstance(80, 90, Image.SCALE_DEFAULT));
		
		JLabel cs = new JLabel(ss);
		
		cs.setBounds(35,0,80,100);
		
		jp.add(cs);
		
		return jp;
	}

	public JPanel creat_center() {
		
		JPanel jp=new JPanel();
		
		jp.setBackground(new Color(255, 255, 255));
		
		jp.setLayout(null);
		
		jp.setPreferredSize(new Dimension(0,220));
		
		idField = new JTextField(10);
		
		idField.setBounds(0,10,200,30);
		
		idField.setFont(new Font("menlo",Font.BOLD,17));
		
		idField.addFocusListener(new JTextFieldHdandler(idField,"Id"));
		
		idField.setOpaque(false);
		
		idField.addKeyListener(this);
		
		jp.add(idField);
		
		passwordField = new JPasswordField(18);
		
		passwordField.setBounds(0,42, 200, 30);
		
		passwordField.setFont(new Font("menlo",Font.BOLD,17));
		
		passwordField.addFocusListener(new JPasswordFielddHdandler(passwordField,"Password"));
		
		passwordField.setOpaque(false);
		
		passwordField.addKeyListener(this);
		
		jp.add(passwordField);

		return jp;
	}

	public JPanel creat_south() {
		
		JPanel jp = new JPanel();
		
		jp.setBackground(new Color(255, 255, 255));
		
		jp.setLayout(null);
		
		jp.setPreferredSize(new Dimension(0,40));
		
		loginButton= UIUtil.createButton("Login");
		
		loginButton.setBounds(120,0,260,30);

		loginButton.addActionListener(new  LoginHandler(this));
		
		jp.add(loginButton);
		
		return jp;
	}

	public JPanel creat_east() {
		
		JPanel jp = new JPanel();
		
		jp.setBackground(new Color(255, 255, 255));
		
		jp.setLayout(null);
		
		jp.setPreferredSize(new Dimension(130,0));
		
		registerLabel=new JLabel("Register");
		
		registerLabel.setBounds(0, 10, 100, 30);
		
		registerLabel.setFont(new Font("menlo",Font.BOLD,15));
		
		registerLabel.setForeground(new Color(100,149,238));
		
		registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		forgetLabel=new JLabel("Forget password");
		
		forgetLabel.setBounds(0, 42, 200, 30);
		
		forgetLabel.setFont(new Font("menlo", Font.BOLD,15));
		
		forgetLabel.setForeground(new Color(100,149,238));
		
		forgetLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		jp.add(registerLabel);
		
		jp.add(forgetLabel);
		
		return jp;
	}
	
	public boolean checkInput() {
		return !idField.getText().equals("") && !String.valueOf(passwordField.getPassword()).equals("");
	}
		
	class LoginHandler implements ActionListener {
		
		LoginNewUI uiFrame;
		
		public LoginHandler(LoginNewUI uiFrame) {
			super();
			this.uiFrame = uiFrame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(uiFrame.checkInput())
				try {
					if(uiFrame.callback.login(idField.getText(), String.valueOf(passwordField.getPassword()))){
						uiFrame.loggedin = true;
						uiFrame.dispose();
					}
				} catch (Exception e1) {
					UIUtil.showErrorMsg(e1.getMessage(), "Error", frame);
					e1.printStackTrace();
				}
		}
		
	}

	class JTextFieldHdandler implements FocusListener{
		
		private String str;
		
		private JTextField text1;
		
		public JTextFieldHdandler(JTextField text1,String str) {
			this.text1=text1;
			this.str=str;
			
		}
		public void focusGained(FocusEvent e) {
			if(text1.getText().equals(str))
			{
				text1.setText("");
				text1.setForeground(Color.BLACK);
			}
		}
		public void focusLost(FocusEvent e) {
				if(text1.getText().equals("")) {
					text1.setForeground(Color.gray);
					text1.setText(str);
				}
			}
	}

	class JPasswordFielddHdandler implements FocusListener{
		
		private String str;
		
		private JPasswordField text1;
		
		public JPasswordFielddHdandler(JPasswordField text1,String str) {
			this.text1=text1;
			this.str=str;

		}
		
		public void focusGained(FocusEvent e) {
				if(String.valueOf(text1.getPassword()).equals(str))
				{
					text1.setText("");
					text1.setEchoChar('*');
					text1.setForeground(Color.BLACK);
				}
			}
		
		public void focusLost(FocusEvent e) {
			if(String.valueOf(text1.getPassword()).equals("")) {
				text1.setEchoChar((char)(0));
				text1.setForeground(Color.gray);
				text1.setText(str);
			}
		}
	}
	
	class CloseWindow extends WindowAdapter{

		@Override
        public void windowClosing(WindowEvent e){
			dispose();
        }
    }
	
	public void dispose() {
		if(!loggedin) this.callback.exit();
		this.frame.dispose();
	}

	int preKey = 0;
	
	public void keyPressed(KeyEvent event) {
		
		preKey = event.getKeyCode();
		
	}
 
	public void keyReleased(KeyEvent event) {
		
		if( KeyEvent.getKeyText(event.getKeyCode()).equals("ENTER") && KeyEvent.getKeyText(preKey).equals("ENTER")|| 
				event.getKeyCode() == 10 && preKey == 10 ){
			
			if(this.checkInput())
				try {
					if(this.callback.login(idField.getText(), String.valueOf(passwordField.getPassword()))) {
						loggedin = true;
						dispose();
					}
				} catch (Exception e) {
					UIUtil.showErrorMsg(e.getMessage(), "Error", frame);
					e.printStackTrace();
				}
		}
		
	}
	
	
	@Override
	public void keyTyped(KeyEvent event) {
		
	}

}
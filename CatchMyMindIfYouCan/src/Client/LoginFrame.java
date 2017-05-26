package Client;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import VO.ProtocolInfo;
import VO.UserInfo;

public class LoginFrame extends JPanel{
	ClientMind mainFrame;
	JPanel inputArea, buttonArea;
	JTextField idField, pwField;
	JButton loginButton, cancelButton, registerButton;
	JLabel loginLabel, passwordLabel;
	RegisterDialog regDiag;
	
	public LoginFrame(ClientMind client) {
		mainFrame = client;
		setLoginFrame();
		setButtonListener();
	}
	public void setLoginFrame(){
		this.setLayout(new GridLayout(0, 1));
		//this.setSize(330, 200);
		inputArea = new JPanel(new FlowLayout());
		buttonArea = new JPanel(new GridLayout(0, 3));
		
		idField = new JTextField(10);
		pwField = new JTextField(10);
		
		loginLabel = new JLabel("�̸�");
		passwordLabel = new JLabel("PW");
		
		loginButton = new JButton("�α���");
		cancelButton = new JButton("���");
		registerButton = new JButton("ȸ������");
		
		inputArea.add(loginLabel);
		inputArea.add(idField);
		inputArea.add(passwordLabel);
		inputArea.add(pwField);
		
		buttonArea.add(loginButton);
		buttonArea.add(cancelButton);
		buttonArea.add(registerButton);
		
		this.add(inputArea);
		this.add(buttonArea);
	}
	public void setButtonListener(){
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//OK�� ������ ��������� �ؾ� �Ѵ�.
				System.out.println("�α����� �����մϴ�.");
				ProtocolInfo temp = new ProtocolInfo();
				temp.setUserInfo(new UserInfo(idField.getText(), pwField.getText(), "", ""));
				temp.setProtocolMode(ProtocolInfo.ACCESS_MODE);
//				System.out.println(temp.getUserInfo());
				mainFrame.currInfo = temp;
				mainFrame.sendProtocol(temp);
				idField.setText("");
				pwField.setText("");
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
			}
		});
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JDialog
				regDiag = new RegisterDialog(LoginFrame.this);
			}
		});
	}
	public void dialogTurnOff(){
		//���⼭����
		regDiag.dispose();
	}
/*	public void turnOn(){
		setVisible(true);
	}
	public void turnOff(){
		setVisible(false);
	}*/
}

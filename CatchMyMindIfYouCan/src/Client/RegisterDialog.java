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

public class RegisterDialog extends JDialog{
	LoginFrame lf;
	JLabel nameLabel, pwLabel, nickLabel, genLabel;
	JTextField nameField, pwField, nickField, genField;
	JButton registerBtn, cancelBtn;
	JPanel inputPanel, buttonPanel;

	public RegisterDialog(LoginFrame lf) {
		this.lf = lf;
		this.setLocation(lf.mainFrame.getLocation().x, lf.mainFrame.getLocation().y);
		this.setSize(320, 180);
		setLayout(new GridLayout(0, 1));
		setComponent();
		setButtonListener();
	}
	public void setComponent(){ 
		inputPanel = new JPanel(new FlowLayout());
		buttonPanel = new JPanel(new GridLayout(0, 2));
		
		nameLabel = new JLabel("�̸�");
		pwLabel = new JLabel("PW");
		nickLabel = new JLabel("����");
		genLabel = new JLabel("����");
		
		nameField = new JTextField(10);
		pwField = new JTextField(10);
		nickField = new JTextField(10);
		genField = new JTextField(10);
		
		registerBtn = new JButton("����");
		cancelBtn = new JButton("���");
		
		inputPanel.add(nameLabel);
		inputPanel.add(nameField);
		inputPanel.add(pwLabel);
		inputPanel.add(pwField);
		inputPanel.add(nickLabel);
		inputPanel.add(nickField);
		inputPanel.add(genLabel);
		inputPanel.add(genField);
		
		buttonPanel.add(registerBtn);
		buttonPanel.add(cancelBtn);
		
		this.add(inputPanel);
		this.add(buttonPanel);
		setVisible(true);
	}
	public void setButtonListener(){
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ProtocolInfo temp = new ProtocolInfo();
				System.out.println("���������� ��������ϴ�.");
				//���� ���������� ���� ���� ����
				temp.setProtocolMode(ProtocolInfo.REGISTER_MODE);
				temp.setUserInfo(new UserInfo(nameField.getText(), pwField.getText(), 
						nickField.getText(), genField.getText()));
				lf.mainFrame.sendProtocol(temp);
				
				//�α��� ������������ ���Ѱ��� ó���ؾ� �Ѵ�.
				lf.dialogTurnOff();
			}
		});
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//�̰��� login�����ӿ��� ó��
				lf.dialogTurnOff();
			}
		});
	}
}

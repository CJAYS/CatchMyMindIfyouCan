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
		
		nameLabel = new JLabel("이름");
		pwLabel = new JLabel("PW");
		nickLabel = new JLabel("별명");
		genLabel = new JLabel("성별");
		
		nameField = new JTextField(10);
		pwField = new JTextField(10);
		nickField = new JTextField(10);
		genField = new JTextField(10);
		
		registerBtn = new JButton("가입");
		cancelBtn = new JButton("취소");
		
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
				System.out.println("프로토콜을 만들었습니다.");
				//메인 프레임으로 가입 정보 전송
				temp.setProtocolMode(ProtocolInfo.REGISTER_MODE);
				temp.setUserInfo(new UserInfo(nameField.getText(), pwField.getText(), 
						nickField.getText(), genField.getText()));
				lf.mainFrame.sendProtocol(temp);
				
				//로그인 실패했을때에 대한것을 처리해야 한다.
				lf.dialogTurnOff();
			}
		});
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//이것은 login프레임에서 처리
				lf.dialogTurnOff();
			}
		});
	}
}

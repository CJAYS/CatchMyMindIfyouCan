package Client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import VO.ProtocolInfo;

public class WaitFrame extends JPanel {

	JPanel north_p, center_p, south_p; // WaitingRopomPanel 을 borderLayout으로
										// 설정하여 3부분으로 나눈다
	JPanel input_p; // 사용자가 채팅 내용을 입력할 패널 : waitingRoomChatField, sendChatBtn 이
					// 들어감
	JButton joinGameRoomBtn, findGameRoomBtn, createGameRoomBtn, exitWaitingRoomBtn; // 방참여,

	ClientMind mainframe;
	JPanel inputArea, buttonArea;
	JScrollPane gameRoomListScrollPane, waitingRoomUserListScrollPane, waitingRoomChatScrollPane; // 방리스트,
	JList<String> gameRoomList, waitingRoomUserList; // 게임방 이름 리스트, 대기실 사용자 이름
														// 리스트
	JTextArea waitingRoomChatArea; // 대기실에서 채팅내용을 표시하기 위한 컴포넌트
	JTextField waitingRoomChatField; // 대기실에서 채팅하기 위해 사용자가 글을 입력하는 부분
	JButton enterBtn, createBtn, findBtn, sendChatBtn; // 입력한 내용을 전송한다.
	JDialog jdialog;// 방만들기 화면을 보여줄 다이얼로그
	Graphics g;

	public WaitFrame(ClientMind client) {

		mainframe = client;
		setWaitFrame();
		setButtonListener();
	}

	public void setWaitFrame() {
		this.setLayout(new BorderLayout());

		// final ImageIcon icon = new ImageIcon("C:/project/gear.png");
		north_p = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 방참여, 방만들기,
																// 나가기 버튼을 담을 북쪽
																// 패널
		center_p = new JPanel(new BorderLayout()); // 방 목록과 이용자 목록리스트를 담을 센터 패널
		south_p = new JPanel(new BorderLayout());

		JPanel ns_p = new JPanel(new BorderLayout());

		// 버튼패널
		north_p.add(joinGameRoomBtn = new JButton("참여하기"));
		north_p.add(createGameRoomBtn = new JButton("방만들기"));
		north_p.add(findGameRoomBtn = new JButton("방찾기"));
		north_p.add(exitWaitingRoomBtn = new JButton("나가기"));

		// 센터패널
		gameRoomListScrollPane = new JScrollPane(gameRoomList = new JList<>());
		gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameRoomListScrollPane.setBorder(new TitledBorder("방 목록"));
		waitingRoomUserListScrollPane = new JScrollPane(waitingRoomUserList = new JList<>());
		waitingRoomUserListScrollPane.setBorder(new TitledBorder("대기실 이용자"));
		waitingRoomUserList.setFixedCellWidth(150);
		center_p.add(gameRoomListScrollPane);
		center_p.add(waitingRoomUserListScrollPane, BorderLayout.EAST);

		// 대화패널
		waitingRoomChatScrollPane = new JScrollPane(waitingRoomChatArea = new JTextArea(7, 0));
		waitingRoomChatScrollPane.setBorder(new TitledBorder("대화창"));
		waitingRoomChatArea.setEditable(false);
		waitingRoomChatScrollPane.getVerticalScrollBar().setValue(waitingRoomChatScrollPane.getVerticalScrollBar().getMaximum());

		input_p = new JPanel(new BorderLayout());

		input_p.add(waitingRoomChatScrollPane);
		input_p.add(waitingRoomChatField = new JTextField(), new BorderLayout().SOUTH);

		this.add(center_p, new BorderLayout().CENTER);
		this.add(ns_p, new BorderLayout().SOUTH);
		ns_p.add(north_p, new BorderLayout().NORTH);
		ns_p.add(input_p, new BorderLayout().CENTER);

		// this.add(center_p,new BorderLayout().NORTH);
		// this.add(input_p,new BorderLayout().SOUTH);
		//

		this.setLocation(300, 300);
		//this.setSize(360, 450);
		this.setVisible(true);
	}

	// 버튼리스너
	private void setButtonListener() {
		joinGameRoomBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("게임에 참가합니다");
				ProtocolInfo joinMsg = new ProtocolInfo();

				joinMsg.setGf(new GameFrame(mainframe));

				joinMsg.setProtocolMode(ProtocolInfo.JOIN_MODE);
				mainframe.sendProtocol(joinMsg);
			}
		});
		createGameRoomBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("방을 만듭니다");
				ProtocolInfo createMsg = new ProtocolInfo();

				createMsg.setProtocolMode(ProtocolInfo.CREATE_MODE);
				mainframe.sendProtocol(createMsg);

			}
		});
		findGameRoomBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("방을 찾습니다");
				ProtocolInfo findMsg = new ProtocolInfo();

				// findMsg.setProtocolMode(ProtocolInfo.FIND_MODE);
				mainframe.sendProtocol(findMsg);
			}
		});

		exitWaitingRoomBtn.addActionListener(new ActionListener() {

			private JFrame frm;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("대기실을 나갑니다");
				ProtocolInfo exitMsg = new ProtocolInfo();
				    Object[] inputs = {"나가기", "취소"};
			        int selectedNum = JOptionPane.showOptionDialog(frm, "옵션 다이얼로그 예제", "옵션 다이얼로그", 
		                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, inputs, inputs[0]);
			        	if(selectedNum==0){
			        		exitMsg.setProtocolMode(ProtocolInfo.EXIT_MODE_WAIT);
			        		mainframe.sendProtocol(exitMsg);
			        		System.exit(0);
			        	}
			}
		});
		waitingRoomChatField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("채팅창의 액션리스너가 실행되었습니다.");
				String msg = waitingRoomChatField.getText();
				waitingRoomChatField.setText("");
				/*waitingRoomChatArea.append(msg + "\n");*/
				ProtocolInfo chat = mainframe.currInfo;
				chat.setWaitRoomMsg(msg);
				System.out.println(msg);
				chat.setProtocolMode(ProtocolInfo.WAITCHAT_MODE);
				System.out.println("프로토콜에담긴 메시지"+chat.getWaitRoomMsg());
				mainframe.sendProtocol(chat);
				/*mainframe.currInfo.setWaitRoomMsg("");*/
			}
		});
	}
	public void setWaitRoomList(String[] list){
		/*JList<String>*/ waitingRoomUserList.setListData(list);
	}

	// 장유빈
	/*
	 * ClientMind mainFrame; JPanel listArea, buttonArea, chatterArea; JTextArea
	 * chatterView; JTextField chatterLine; JButton createRoomBtn, joinRoomBtn,
	 * findRoomBtn, exitRoomBtn;
	 */

	/*
	 * public WaitFrame(ClientMind client) { mainFrame = client; setWaitFrame();
	 * setListener(); }
	 * 
	 * private void setWaitFrame() { this.setLayout(new FlowLayout());
	 * createRoomBtn = new JButton("방만들기"); joinRoomBtn = new JButton("방들어가기");
	 * findRoomBtn = new JButton("방찾아가기"); exitRoomBtn = new JButton("로그아웃");
	 * 
	 * this.add(createRoomBtn); this.add(joinRoomBtn); this.add(findRoomBtn);
	 * this.add(exitRoomBtn); } private void setListener(){
	 * exitRoomBtn.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent arg0) { ProtocolInfo
	 * info = new ProtocolInfo();
	 * info.setProtocolMode(ProtocolInfo.EXIT_MODE_WAIT);
	 * mainFrame.sendProtocol(info); } }); }
	 */
	/*
	 * public void turnOn(){ this.setVisible(true); }
	 * 
	 * public void turnOff(){ this.setVisible(false); }
	 */
}

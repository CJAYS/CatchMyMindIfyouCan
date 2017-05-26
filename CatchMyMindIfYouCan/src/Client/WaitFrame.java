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

	JPanel north_p, center_p, south_p; // WaitingRopomPanel �� borderLayout����
										// �����Ͽ� 3�κ����� ������
	JPanel input_p; // ����ڰ� ä�� ������ �Է��� �г� : waitingRoomChatField, sendChatBtn ��
					// ��
	JButton joinGameRoomBtn, findGameRoomBtn, createGameRoomBtn, exitWaitingRoomBtn; // ������,

	ClientMind mainframe;
	JPanel inputArea, buttonArea;
	JScrollPane gameRoomListScrollPane, waitingRoomUserListScrollPane, waitingRoomChatScrollPane; // �渮��Ʈ,
	JList<String> gameRoomList, waitingRoomUserList; // ���ӹ� �̸� ����Ʈ, ���� ����� �̸�
														// ����Ʈ
	JTextArea waitingRoomChatArea; // ���ǿ��� ä�ó����� ǥ���ϱ� ���� ������Ʈ
	JTextField waitingRoomChatField; // ���ǿ��� ä���ϱ� ���� ����ڰ� ���� �Է��ϴ� �κ�
	JButton enterBtn, createBtn, findBtn, sendChatBtn; // �Է��� ������ �����Ѵ�.
	JDialog jdialog;// �游��� ȭ���� ������ ���̾�α�
	Graphics g;

	public WaitFrame(ClientMind client) {

		mainframe = client;
		setWaitFrame();
		setButtonListener();
	}

	public void setWaitFrame() {
		this.setLayout(new BorderLayout());

		// final ImageIcon icon = new ImageIcon("C:/project/gear.png");
		north_p = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // ������, �游���,
																// ������ ��ư�� ���� ����
																// �г�
		center_p = new JPanel(new BorderLayout()); // �� ��ϰ� �̿��� ��ϸ���Ʈ�� ���� ���� �г�
		south_p = new JPanel(new BorderLayout());

		JPanel ns_p = new JPanel(new BorderLayout());

		// ��ư�г�
		north_p.add(joinGameRoomBtn = new JButton("�����ϱ�"));
		north_p.add(createGameRoomBtn = new JButton("�游���"));
		north_p.add(findGameRoomBtn = new JButton("��ã��"));
		north_p.add(exitWaitingRoomBtn = new JButton("������"));

		// �����г�
		gameRoomListScrollPane = new JScrollPane(gameRoomList = new JList<>());
		gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameRoomListScrollPane.setBorder(new TitledBorder("�� ���"));
		waitingRoomUserListScrollPane = new JScrollPane(waitingRoomUserList = new JList<>());
		waitingRoomUserListScrollPane.setBorder(new TitledBorder("���� �̿���"));
		waitingRoomUserList.setFixedCellWidth(150);
		center_p.add(gameRoomListScrollPane);
		center_p.add(waitingRoomUserListScrollPane, BorderLayout.EAST);

		// ��ȭ�г�
		waitingRoomChatScrollPane = new JScrollPane(waitingRoomChatArea = new JTextArea(7, 0));
		waitingRoomChatScrollPane.setBorder(new TitledBorder("��ȭâ"));
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

	// ��ư������
	private void setButtonListener() {
		joinGameRoomBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("���ӿ� �����մϴ�");
				ProtocolInfo joinMsg = new ProtocolInfo();

				joinMsg.setGf(new GameFrame(mainframe));

				joinMsg.setProtocolMode(ProtocolInfo.JOIN_MODE);
				mainframe.sendProtocol(joinMsg);
			}
		});
		createGameRoomBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("���� ����ϴ�");
				ProtocolInfo createMsg = new ProtocolInfo();

				createMsg.setProtocolMode(ProtocolInfo.CREATE_MODE);
				mainframe.sendProtocol(createMsg);

			}
		});
		findGameRoomBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("���� ã���ϴ�");
				ProtocolInfo findMsg = new ProtocolInfo();

				// findMsg.setProtocolMode(ProtocolInfo.FIND_MODE);
				mainframe.sendProtocol(findMsg);
			}
		});

		exitWaitingRoomBtn.addActionListener(new ActionListener() {

			private JFrame frm;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("������ �����ϴ�");
				ProtocolInfo exitMsg = new ProtocolInfo();
				    Object[] inputs = {"������", "���"};
			        int selectedNum = JOptionPane.showOptionDialog(frm, "�ɼ� ���̾�α� ����", "�ɼ� ���̾�α�", 
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
				System.out.println("ä��â�� �׼Ǹ����ʰ� ����Ǿ����ϴ�.");
				String msg = waitingRoomChatField.getText();
				waitingRoomChatField.setText("");
				/*waitingRoomChatArea.append(msg + "\n");*/
				ProtocolInfo chat = mainframe.currInfo;
				chat.setWaitRoomMsg(msg);
				System.out.println(msg);
				chat.setProtocolMode(ProtocolInfo.WAITCHAT_MODE);
				System.out.println("�������ݿ���� �޽���"+chat.getWaitRoomMsg());
				mainframe.sendProtocol(chat);
				/*mainframe.currInfo.setWaitRoomMsg("");*/
			}
		});
	}
	public void setWaitRoomList(String[] list){
		/*JList<String>*/ waitingRoomUserList.setListData(list);
	}

	// ������
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
	 * createRoomBtn = new JButton("�游���"); joinRoomBtn = new JButton("�����");
	 * findRoomBtn = new JButton("��ã�ư���"); exitRoomBtn = new JButton("�α׾ƿ�");
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

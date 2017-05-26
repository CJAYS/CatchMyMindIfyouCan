package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import VO.ProtocolInfo;
import VO.UserInfo;

public class ClientMind extends JFrame implements Runnable{
	//�������
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	GameFrame gf;
	LoginFrame lf;
	WaitFrame wf;
	Thread thread;
	ProtocolInfo currInfo;
	
	public ClientMind(){
		setSocketConfigure();
		setFrameConfigure();
		setVisible(true);
	}
	/*public void receivedMessage(){
		try {
			String temp = (String) ois.readObject();
			System.out.println(temp);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/
	private void setSocketConfigure(){
		try {
			socket = new Socket("10.10.12.43", 7777);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			thread = new Thread(this);
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setFrameConfigure() {
		this.setSize(330, 110);
		this.setLocation(700, 400);
		
		
		lf = new LoginFrame(this);
		gf = new GameFrame(this);
		wf = new WaitFrame(this);
		
		this.getContentPane().add(lf);
		/*this.add(gf);
		this.add(wf);*/
	}
	
/*	private void setStartFrame(){
		lf.turnOn();
		lf.mainFrame.setVisible(true);
		pack();
	}*/
	
	public static void main(String[] args) {
		ClientMind a = new ClientMind();
	}
	@Override
	//�̰��� Ŭ���̾�Ʈ
	public void run() {
		try {
			bk:while(true){
				ProtocolInfo info =(ProtocolInfo) ois.readObject();
				switch(info.getProtocolMode()){
				case ProtocolInfo.ACCESS_MODE:
					currInfo = info;
					if(currInfo.getStatus()) {
						System.out.println("�α��ο� �����߽��ϴ�.");
						JOptionPane.showMessageDialog(null, "�α����� �Ǿ����ϴ�.");
						this.changePanel("wait");
					}
					else if(!currInfo.getStatus() && !currInfo.getDuplicated()){
						System.out.println("�α��ο� �����߽��ϴ�.");
						JOptionPane.showMessageDialog(null, "�α��ο� �����߽��ϴ�.");
						//�ߺ� �������� ���� �α����� �������� �� 
						//currInfo�� ���ο� ProtocolInfo�� ����� �Ǵ� �� �Ѵ�
//						currInfo = new ProtocolInfo();
					} else {
						System.out.println("���� �������Դϴ�.");
						JOptionPane.showMessageDialog(null, "���� �������Դϴ�.");
//						currInfo = new ProtocolInfo();
					}
					break;
				case ProtocolInfo.CREATE_MODE:
					break;
				case ProtocolInfo.EXIT_MODE_WAIT:
					if(info.getStatus()){
						
						System.out.println("�α׾ƿ��� �����߽��ϴ�.");
						this.changePanel("login");
					} else System.out.println("�α׾ƿ��� �����߽��ϴ�.");
					break;
				case ProtocolInfo.GAMECHAT_MODE:
					break;
				case ProtocolInfo.JOIN_MODE:
					break;
				case ProtocolInfo.PAINT_MODE:
					break;
				case ProtocolInfo.REGISTER_MODE: 
					if(info.getStatus()) System.out.println("ȸ�����Կ� �����߽��ϴ�.");
					else System.out.println("ȸ�����Կ� �����߽��ϴ�.");
					break;
				case ProtocolInfo.WAIT_MODE:
					break;
				case ProtocolInfo.WAITCHAT_MODE:
					String msg = info.getWaitRoomMsg();
					System.out.println("�������� ���� �޽���:"+msg);
					UserInfo fromUserInfo = info.getUserInfo();
		            wf.waitingRoomChatArea.append("["+fromUserInfo.getUserName()+"] : "+msg+"\n");
		            //����� �ڵ����� ��ũ���� ���������� �ϴ� ��
		            wf.waitingRoomChatScrollPane.getVerticalScrollBar().setValue(wf.waitingRoomChatScrollPane.getVerticalScrollBar().getMaximum());
					break;
				case ProtocolInfo.EXIT_MODE_GAME:
					if(info.getStatus()) {
						System.out.println("�α׾ƿ��� �����߽��ϴ�.");
						changePanel("login");
					}
					else System.out.println("�α׾ƿ���  �����߽��ϴ�.");
					break;
				case ProtocolInfo.WAIT_USER_LIST:
					String[] list = info.getUserWaitList();
					System.out.print(list.length+"�� ������");
					for(String temp : list) System.out.print(" " + temp);
					System.out.println();
					wf.setWaitRoomList(list);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*	public void startThread(ProtocolInfo tempInfo){      //NickNamePanel ���� ��ȭ�� �Է� �� Ȯ�� ��ư ������ �� �θ�
		try {
			socket = new Socket("localhost", 7777);   
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			ProtocolInfo info = tempInfo;
			info.setProtocolMode(ProtocolInfo.ACCESS_MODE);
			oos.writeObject(info);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
	}*/
	public void sendProtocol(ProtocolInfo info){
		try {
			oos.writeObject(info);
			oos.flush();
			oos.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void changePanel(String panelName){
		if(panelName.equals("wait")){
			this.getContentPane().removeAll();
			this.setSize(360, 450);
			this.setLocation(450, 200);
			this.getContentPane().add(wf);
			this.revalidate();
			this.repaint();
		} else if(panelName.equals("game")){
			this.getContentPane().removeAll();
			this.getContentPane().add(gf);
			this.revalidate();
			this.repaint();
		} else if(panelName.equals("login")){
			this.getContentPane().removeAll();
			this.setSize(330, 110);
			this.setLocation(700, 400);
			this.getContentPane().add(lf);
			this.revalidate();
			this.repaint();
		}
	}
}


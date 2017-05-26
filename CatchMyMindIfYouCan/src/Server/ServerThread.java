package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Manager.ConnectionManager;
import VO.ProtocolInfo;
import VO.UserInfo;

public class ServerThread extends Thread {
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket threadSocket;
	boolean status;
	ProtocolInfo temp;
	ServerMind uniqueServer;

	public ServerThread(Socket socket, ServerMind server) {
		uniqueServer = server;
		threadSocket = socket;
		try {
			oos = new ObjectOutputStream(threadSocket.getOutputStream());
			ois = new ObjectInputStream(threadSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("������Ʈ��Ʈ�� ���� �Ұ�");
		}
	}
	@Override
	public void run() {
		try {
			while(true){
				oos.reset();
				temp = (ProtocolInfo) ois.readObject();
				switch(temp.getProtocolMode()){
				case ProtocolInfo.ACCESS_MODE:
					ProtocolInfo replyLogin = new ProtocolInfo();
					if(!checkDuplicated(temp)){
						replyLogin = loginQuery(temp);
						if(replyLogin.getStatus()) uniqueServer.threadList.add(this);
						sendWaitListToAll(uniqueServer.threadList);
					} else {
						System.out.println("�������� ���̳�");
						replyLogin.setDuplicated(true);
					}
					//�ߺ� �� �α��� ȸ�������� �ִ����� Ȯ���Ѵ�.
					System.out.println(uniqueServer.threadList.size() + "��");
					//������ �ο��� �����ϴ� ���� �������� ������ ������ �Ѵ�.
					oos.writeObject(replyLogin);
					oos.flush();
					break;
				case ProtocolInfo.CREATE_MODE:
					
					break;
				case ProtocolInfo.EXIT_MODE_WAIT:
					//���� ���뿡 �� �ִ� ����Ʈ���� �ο����� ���� ���̴�.
					//�׸��� ����Ʈ�� 
					ProtocolInfo replyWaitExit = new ProtocolInfo();
					replyWaitExit.setProtocolMode(ProtocolInfo.EXIT_MODE_WAIT);
					replyWaitExit.setStatus(true);
					
					//�α׾ƿ����� ���ؼ� ���濡�� ����������.
					uniqueServer.threadList.remove(this);
					sendWaitListToAll(uniqueServer.threadList);
					//replyWaitExit.setRoomList(/*���⿡�� �ٲ� ����Ʈ�� ������*/);
					oos.writeObject(replyWaitExit);
					break;
				case ProtocolInfo.GAMECHAT_MODE:
					break;
				case ProtocolInfo.JOIN_MODE:
					break;
				case ProtocolInfo.PAINT_MODE:
					break;
				case ProtocolInfo.REGISTER_MODE:
					ProtocolInfo replyRegister = registerQuery(temp);
					oos.writeObject(replyRegister);
					break;
				case ProtocolInfo.WAIT_MODE:
					ProtocolInfo waitChatMessage = temp;
					sendToAllClient(uniqueServer.threadList, waitChatMessage);
					break;
				case ProtocolInfo.WAITCHAT_MODE:
					ProtocolInfo replyMessage = temp;
					System.out.println("�������� �޽����� �޾ҽ��ϴ�.");
					System.out.println(replyMessage.getWaitRoomMsg());
					sendToAllClient(uniqueServer.threadList, replyMessage);
					break;		
				case ProtocolInfo.EXIT_MODE_GAME:
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ProtocolInfo registerQuery(ProtocolInfo info){
		ProtocolInfo tempInfo = info;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement("insert into user_info (USER_ID, USER_PW, USER_NICK, USER_GEN) values(?, ?, ?, ?)");
			System.out.println(pstmt);
			System.out.println(tempInfo);
			pstmt.setString(1, tempInfo.getUserInfo().getUserName());//ù��° �Ű��������� ����ǥ�� ������ ����. ù��° ����ǥ�� 1016�̶�� �� �ְڴٶ�� ��
			pstmt.setString(2, tempInfo.getUserInfo().getUserPw());
			pstmt.setString(3, tempInfo.getUserInfo().getUserName());
			pstmt.setString(4, tempInfo.getUserInfo().getUserGen());
			
			int result = pstmt.executeUpdate();
			if(result != 0) tempInfo.setStatus(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tempInfo;
	}
	public ProtocolInfo loginQuery(ProtocolInfo info){
		ProtocolInfo tempInfo = info;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement("select * from user_info where USER_ID = ?");
			pstmt.setString(1, tempInfo.getUserInfo().getUserName());

			//�Ű������� ���޵� sql���� ������ �ȴ�. select�� ������ �������⶧���� �̰��� �޾ƾ� �Ѵ�.
			ResultSet rs = pstmt.executeQuery();  
			while(rs.next()){
				if(rs.getString(1).equals(tempInfo.getUserInfo().getUserName()) && 
						rs.getString(2).equals(tempInfo.getUserInfo().getUserPw())){
					tempInfo.getUserInfo().setUserNick(rs.getString(3));
					tempInfo.getUserInfo().setUserGen(rs.getString(4));
					tempInfo.setStatus(true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tempInfo;
	}
	
	boolean checkDuplicated(ProtocolInfo info){
		boolean isDuplicated = false;
		
		String checkId = info.getUserInfo().getUserName();
		String[] idList = makeListToString(uniqueServer.threadList);
		
		for(String temp : idList){
			if(temp.equals(checkId)) {
				isDuplicated = true;
				System.out.println("ã�����ϴ°�"+temp);
				System.out.println("�����ִ°�"+checkId);
			}
			break;
		}
		return isDuplicated;
	}
	
	public String[] makeListToString(ArrayList<ServerThread> list){
		String[] temp = new String[list.size()];
		//����� ��� ����Ʈ�� 
		for(int i=0; i<list.size(); ++i){
			temp[i] = list.get(i).temp.getUserInfo().getUserName();
		}
		return temp;
	}
	
	public void sendWaitListToAll(ArrayList<ServerThread> list){
		ArrayList<ServerThread> threadList = list;
		for(ServerThread temp : threadList){
			//����Ʈ�� ��� ��� ArrayList�� ServerThread�� �޼ҵ带 Ȱ���Ͽ� ��� Ŭ���̾�Ʈ���� �ѷ��ش�.
			ProtocolInfo msgInfo = new ProtocolInfo();
			msgInfo.setProtocolMode(ProtocolInfo.WAIT_USER_LIST);
			msgInfo.setUserWaitList(makeListToString(threadList));
			try {
				temp.oos.writeObject(msgInfo);
				temp.oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendToAllClient(ArrayList<ServerThread> list, ProtocolInfo info){
		ArrayList<ServerThread> threadList = list;
		ProtocolInfo tempInfo = info;
		for(ServerThread tempThread : threadList){
			//����Ʈ�� ��� ��� ArrayList�� ServerThread���� ProtocolInfo�� ��ü������ �ѷ��ش�.
			try {
				tempThread.oos.writeObject(tempInfo);
				tempThread.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

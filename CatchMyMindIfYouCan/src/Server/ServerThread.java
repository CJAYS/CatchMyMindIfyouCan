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
			System.err.println("오브젝트스트림 생성 불가");
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
						System.out.println("접속중인 것이네");
						replyLogin.setDuplicated(true);
					}
					//중복 및 로그인 회원정보가 있는지를 확인한다.
					System.out.println(uniqueServer.threadList.size() + "명");
					//서버에 인원이 접속하는 것은 서버에서 강제로 보내야 한다.
					oos.writeObject(replyLogin);
					oos.flush();
					break;
				case ProtocolInfo.CREATE_MODE:
					
					break;
				case ProtocolInfo.EXIT_MODE_WAIT:
					//현재 대기룸에 들어가 있는 리스트에서 인원수가 빠질 것이다.
					//그리고 리스트는 
					ProtocolInfo replyWaitExit = new ProtocolInfo();
					replyWaitExit.setProtocolMode(ProtocolInfo.EXIT_MODE_WAIT);
					replyWaitExit.setStatus(true);
					
					//로그아웃으로 인해서 대기방에서 빠져나간다.
					uniqueServer.threadList.remove(this);
					sendWaitListToAll(uniqueServer.threadList);
					//replyWaitExit.setRoomList(/*여기에는 바뀐 리스트가 들어가겠지*/);
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
					System.out.println("서버에서 메시지를 받았습니다.");
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
			pstmt.setString(1, tempInfo.getUserInfo().getUserName());//첫번째 매개변수에는 물음표의 순서가 들어간다. 첫번째 물음표에 1016이라는 걸 넣겠다라는 뜻
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

			//매개변수로 전달된 sql문이 실행이 된다. select는 뭔가를 가져오기때문에 이것을 받아야 한다.
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
				System.out.println("찾고자하는것"+temp);
				System.out.println("원래있는것"+checkId);
			}
			break;
		}
		return isDuplicated;
	}
	
	public String[] makeListToString(ArrayList<ServerThread> list){
		String[] temp = new String[list.size()];
		//여기는 어레이 리스트는 
		for(int i=0; i<list.size(); ++i){
			temp[i] = list.get(i).temp.getUserInfo().getUserName();
		}
		return temp;
	}
	
	public void sendWaitListToAll(ArrayList<ServerThread> list){
		ArrayList<ServerThread> threadList = list;
		for(ServerThread temp : threadList){
			//리스트에 담긴 모든 ArrayList의 ServerThread의 메소드를 활용하여 모든 클라이언트에게 뿌려준다.
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
			//리스트에 담긴 모든 ArrayList의 ServerThread에서 ProtocolInfo를 전체적으로 뿌려준다.
			try {
				tempThread.oos.writeObject(tempInfo);
				tempThread.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

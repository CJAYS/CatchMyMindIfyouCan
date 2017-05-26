package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMind {
	ServerSocket serverSocket;
	Socket socket;

	//클라이언트를 담는 ArrayList이다.
	ArrayList<ServerThread> threadList;

	public ServerMind() {
		try {
			threadList = new ArrayList<>();
			serverSocket = new ServerSocket(7777);
			System.out.println("서버에서 대기중...");
			while(true){
				socket = serverSocket.accept();
				System.out.println("서버에서 로그인 대기중...");
				ServerThread serverThread = new ServerThread(socket, this);
				/*threadList.add(serverThread);*/
				serverThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("클라이언트와의 연결에 실패하였습니다.");
		}
	}
	public static void main(String[] args) {
		new ServerMind();
	}
}

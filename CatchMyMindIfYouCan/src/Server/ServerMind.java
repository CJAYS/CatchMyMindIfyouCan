package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMind {
	ServerSocket serverSocket;
	Socket socket;

	//Ŭ���̾�Ʈ�� ��� ArrayList�̴�.
	ArrayList<ServerThread> threadList;

	public ServerMind() {
		try {
			threadList = new ArrayList<>();
			serverSocket = new ServerSocket(7777);
			System.out.println("�������� �����...");
			while(true){
				socket = serverSocket.accept();
				System.out.println("�������� �α��� �����...");
				ServerThread serverThread = new ServerThread(socket, this);
				/*threadList.add(serverThread);*/
				serverThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Ŭ���̾�Ʈ���� ���ῡ �����Ͽ����ϴ�.");
		}
	}
	public static void main(String[] args) {
		new ServerMind();
	}
}

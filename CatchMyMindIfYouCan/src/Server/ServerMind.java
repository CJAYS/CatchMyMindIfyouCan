package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMind {
	ServerSocket serverSocket;
	Socket socket;
	
	/*System.out.println("���Ӵ����..");
				socket = server.accept();
				//Ŭ���̾�Ʈ�� ����� ������ ���Ϳ� ���
				vec.add(socket);
				//������ ����
				new EchoThread(socket, vec).start();
	*/
	public ServerMind() {
		try {
			serverSocket = new ServerSocket(7777);
			while(true){
				System.out.println("�������� ���� �����...");
				socket = serverSocket.accept();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
}

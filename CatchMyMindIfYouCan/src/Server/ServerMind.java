package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMind {
	ServerSocket serverSocket;
	Socket socket;
	
	/*System.out.println("접속대기중..");
				socket = server.accept();
				//클라이언트와 연결된 소켓을 벡터에 담기
				vec.add(socket);
				//스레드 구동
				new EchoThread(socket, vec).start();
	*/
	public ServerMind() {
		try {
			serverSocket = new ServerSocket(7777);
			while(true){
				System.out.println("서버에서 접속 대기중...");
				socket = serverSocket.accept();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
}

package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import VO.ProtocolInfo;

public class ServerThread extends Thread{
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket threadSocket;

	public ServerThread(Socket socket) {
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
				ProtocolInfo temp = (ProtocolInfo) ois.readObject();
				int selectMode = temp.getProtocolMode();
				switch(selectMode){
//				case temp. :
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import VO.ProtocolInfo;

public class ClientMind implements Runnable{
	
	Socket sk;
	InputStream is;
	OutputStream os;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	final int PORT= 7777;
	
	public ClientMind() {
		try {
			sk = new Socket();
			System.out.println("연결요청");
			sk.connect(new InetSocketAddress("10.10.12.43", PORT));
			System.out.println("연결성공");
			
			os=sk.getOutputStream();
			oos=new ObjectOutputStream(os);
			ProtocolInfo info = new ProtocolInfo();
			info.setProtocolMode(ProtocolInfo.ACCESS_MODE);
			oos.writeObject(info);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			is = sk.getInputStream();
			ois=new ObjectInputStream(is);
			String msg = (String)ois.readObject();
			System.out.println(msg);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static void main(String[] args) {
		Runnable r = new ClientMind();
		Thread t = new Thread(r);
		t.start();
	}	
}
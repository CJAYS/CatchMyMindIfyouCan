package VO;

import Client.GameFrame;
import Client.LoginFrame;
import Client.WaitFrame;

public class ProtocolInfo {
	final int ACCESS_MODE = 1;
	final int EXIT_MODE = 2;
	final int CREATE_MODE = 3;
	final int JOIN_MODE = 4;
	final int WAIT_MODE = 5;
	final int GAMECHAT_MODE = 6;
	final int WAITCHAT_MODE = 7;
	final int PAINT_MODE = 8;
	
	String[] userList, roomList;

	GameFrame gf;
	LoginFrame lf;
	WaitFrame wf;
	
	PaintInfo paintInfo;
	RoomInfo roomInfo;
	UserInfo userInfo;
	int protocolMode;
	
	public ProtocolInfo(RoomInfo roomInfo, UserInfo userInfo, int protocolMode) {
		this.roomInfo = roomInfo;
		this.userInfo = userInfo;
		this.protocolMode = protocolMode; 
	}
	public String[] getUserList() {
		return userList;
	}
	public void setUserList(String[] userList) {
		this.userList = userList;
	}
	public String[] getRoomList() {
		return roomList;
	}
	public void setRoomList(String[] roomList) {
		this.roomList = roomList;
	}
	public GameFrame getGf() {
		return gf;
	}
	public void setGf(GameFrame gf) {
		this.gf = gf;
	}
	public LoginFrame getLf() {
		return lf;
	}
	public void setLf(LoginFrame lf) {
		this.lf = lf;
	}
	public WaitFrame getWf() {
		return wf;
	}
	public void setWf(WaitFrame wf) {
		this.wf = wf;
	}
	public PaintInfo getPaintInfo() {
		return paintInfo;
	}
	public void setPaintInfo(PaintInfo paintInfo) {
		this.paintInfo = paintInfo;
	}
	public RoomInfo getRoomInfo() {
		return roomInfo;
	}
	public void setRoomInfo(RoomInfo roomInfo) {
		this.roomInfo = roomInfo;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public int getProtocolMode() {
		return protocolMode;
	}
	public void setProtocolMode(int protocolMod) {
		this.protocolMode = protocolMod;
	}
}

package VO;

import java.io.Serializable;

import Client.GameFrame;
import Client.LoginFrame;
import Client.WaitFrame;

public class ProtocolInfo implements Serializable{
	public static final int ACCESS_MODE = 1;
	public static final int EXIT_MODE_WAIT = 2;
	public static final int CREATE_MODE = 3;
	public static final int JOIN_MODE = 4;
	public static final int WAIT_MODE = 5;
	public static final int GAMECHAT_MODE = 6;
	public static final int WAITCHAT_MODE = 7;
	public static final int PAINT_MODE = 8;
	public static final int REGISTER_MODE = 9;
	public static final int EXIT_MODE_GAME = 10;
	public static final int WAIT_USER_LIST = 11;
	
	public String[] userList, roomList;
	public String[] userWaitList;

	public GameFrame gf;
	public LoginFrame lf;
	public WaitFrame wf;
	
	public PaintInfo paintInfo;
	public RoomInfo roomInfo;
	public UserInfo userInfo;
	public String waitRoomMsg;
	int protocolMode;
	boolean status = false;
	boolean isDuplicated = false;
	
	public ProtocolInfo() { 
	}
	public ProtocolInfo(RoomInfo roomInfo, UserInfo userInfo, int protocolMode) {
		this.roomInfo = roomInfo;
		this.userInfo = userInfo;
		this.protocolMode = protocolMode; 
	}
	public String[] getUserWaitList() {
		return userWaitList;
	}
	public void setUserWaitList(String[] userWaitList) {
		this.userWaitList = userWaitList;
	}
	public boolean getDuplicated() {
		return isDuplicated;
	}
	public void setDuplicated(boolean isDuplicated) {
		this.isDuplicated = isDuplicated;
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
	public void isSucceed(){
		status = true;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getWaitRoomMsg() {
		return waitRoomMsg;
	}
	public void setWaitRoomMsg(String waitRoomMsg) {
		this.waitRoomMsg = waitRoomMsg;
	}
}

package VO;

public class UserInfo {
	private String userName;
	private String userPw;
	private String userNick;
	private String userGen;
	
	public UserInfo(String userName, String userPw, String userNick, String userGen) {
		this.userName = userName;
		this.userPw = userPw;
		this.userNick = userNick;
		this.userGen = userGen;
		this.userGen = userGen;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getUserGen() {
		return userGen;
	}
	public void setUserGen(String userGen) {
		this.userGen = userGen;
	}
}

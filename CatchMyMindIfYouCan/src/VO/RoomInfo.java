package VO;

public class RoomInfo {
	String roomTitle;
	int roomJoinableNumber;
	
	public RoomInfo(String roomTitle, int roomJoinableNumber) {
		roomTitle = this.roomTitle;
		roomJoinableNumber = this.roomJoinableNumber;
	}
	public String getRoomTitle() {
		return roomTitle;
	}
	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
	public int getRoomJoinableNumber() {
		return roomJoinableNumber;
	}
	public void setRoomJoinableNumber(int roomJoinableNumber) {
		this.roomJoinableNumber = roomJoinableNumber;
	}
}

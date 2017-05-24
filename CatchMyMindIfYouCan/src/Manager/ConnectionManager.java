package Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private final static String DRIVER
		= "oracle.jdbc.driver.OracleDriver";
	private static final String URL
		= "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERID = "test1";
	private static final String USERPWD = "1234";
	//바뀔일이 드물기 때문에 고정을 시킨다.
	
	private ConnectionManager(){}; //private로 해서 굳이 객체 생성을 하지 않도록 막아놓은 것이다.
	//default로 하면 public이기 떄문에 이것은 원한다면 객체 생성을 할 수도 있기 떄문이다.
	//이거 왜 이렇게 함? -> 굳이 객체를 생성하지 않아도 되기때문에
	
	public static Connection getConnection(){
		Connection conn = null;
		
		try {
			Class.forName(DRIVER);
			//이거 버전업하면서 필요없어졌다. 드라이버 로딩 하는 부분은 필요가 없어졌다.
			conn = DriverManager.getConnection(URL, USERID, USERPWD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}	
}


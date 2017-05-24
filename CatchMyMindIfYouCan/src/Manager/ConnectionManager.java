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
	//�ٲ����� �幰�� ������ ������ ��Ų��.
	
	private ConnectionManager(){}; //private�� �ؼ� ���� ��ü ������ ���� �ʵ��� ���Ƴ��� ���̴�.
	//default�� �ϸ� public�̱� ������ �̰��� ���Ѵٸ� ��ü ������ �� ���� �ֱ� �����̴�.
	//�̰� �� �̷��� ��? -> ���� ��ü�� �������� �ʾƵ� �Ǳ⶧����
	
	public static Connection getConnection(){
		Connection conn = null;
		
		try {
			Class.forName(DRIVER);
			//�̰� �������ϸ鼭 �ʿ��������. ����̹� �ε� �ϴ� �κ��� �ʿ䰡 ��������.
			conn = DriverManager.getConnection(URL, USERID, USERPWD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}	
}


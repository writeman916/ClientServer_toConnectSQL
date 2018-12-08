
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ServerSQL {
	
	private static Statement stmt;

	public static void main(String[] args) throws IOException,ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ServerSocket server = new ServerSocket(7000);
		System.out.println("Server started");
		
		Socket socket = server.accept();
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-D9J383E\\\\SQLEXPRESS:1433;DatabaseName=KhachHang","sa","sa");
			stmt = conn.createStatement();
			
		}catch(Exception e){
			System.out.println("errors");
			System.out.println(e.getMessage());
		}
		
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		
		while(true) {
			String st = dis.readUTF();
			dos.writeUTF("Ket qua: \n"+executeData(st));
			dos.flush();
		}
	}
	
	private static String executeData(String query) throws SQLException {		
		
			ResultSet rs = stmt.executeQuery(query);
			String result = "";
			while(rs.next()) {
				String id = String.valueOf(rs.getInt("id"));
				String luong = String.valueOf(rs.getDouble("Luong"));
				String s = rs.getString("TenKH");
				String d = rs.getString("DiaChi");				
				result += "\nid: "+id +"\nTenKH: "+s +"\nDiaChi: "+d +"\nLuong: "+luong +"\n--------------" ;
			}
			return result;
	}
}

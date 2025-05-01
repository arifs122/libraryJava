package database;

import java.sql.*;
/*addMember listMembers showMemberInfo metodları burada
 * */
public class MemberDatabase {
	private static final String URL = "jdbc:sqlite:members.db";
    private static Connection conn;
    
    public static void connect() {
    	try{
    		conn = DriverManager.getConnection(URL);
    		System.out.println("Connected to the member database.");
    	}catch(SQLException e) {
    		System.err.println("Error while trying to connect to the member database: "+e.getMessage());
    	}
    }
    
    public static void initializeDB() {
    	String sql = "CREATE TABLE IF NOT EXISTS members ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "membername TEXT NOT NULL,"
                + "memberage INTEGER NOT NULL,"
                + "canborrow INTEGER NOT NULL DEFAULT 1 CHECK(canborrow IN (0, 1)))";
     
     try (Statement stmt = conn.createStatement()) {
         stmt.execute(sql);
         System.out.println("The member database has been initialized, and is ready to use.");
         
     } catch (SQLException e) {
         System.err.println("Error while trying to initialize the member database: " + e.getMessage());
         System.exit(1);
     }
    }
    
    public static void insertMember() {}
    public static void listMembers() {}
    public static void updateCanBorrow() {}
}

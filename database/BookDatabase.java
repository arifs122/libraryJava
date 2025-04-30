package database;

import java.sql.*;
/*addBook listBooks showBookInfo gibi metodlar burada olacak
 * */
public class BookDatabase {
	private static final String URL = "jdbc:sqlite:books.db";
    private static Connection conn;
    
    public static void connect() {
    	try{
    		conn = DriverManager.getConnection(URL);
    		System.out.println("Connected to the book database.");
    	}catch(SQLException e) {
    		System.err.println("Error while trying to connect to the book database: "+e.getMessage());
    	}
    }
    
    public static void initializeDB() {
    	String sql = "CREATE TABLE IF NOT EXISTS books ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "bookname TEXT NOT NULL,"
                + "bookauthor TEXT NOT NULL,"
                + "bookyear INTEGER NOT NULL,"
                + "isborrowed INTEGER NOT NULL DEFAULT 1 CHECK(isborrowed IN (0, 1)),"
                + "borrowerid INTEGER DEFAULT NULL)";
     
     try (Statement stmt = conn.createStatement()) {
         stmt.execute(sql);
         System.out.println("The book database has been initialized, and is ready to use.");
         
     } catch (SQLException e) {
         System.err.println("Error while trying to initialize the book database: " + e.getMessage());
         System.exit(1);
     }
    }
    public static void insertBook() {}
    public static void listAllBooks() {}
    public static void listLentOutBooks() {}
    public static void listAvailableBooks() {}
    public static void updateBookAvailability() {}
    
}

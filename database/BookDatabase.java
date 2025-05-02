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
                + "bookType TEXT NOT NULL,"
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
    public static void insertBook() {
        /*public static void insertBook(String name, String author, int year) {
    String sql = "INSERT INTO books (bookname, bookauthor, bookyear, isborrowed) VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name);         // 1. ? yerine kitap adı gelir
        pstmt.setString(2, author);       // 2. ? yerine yazar adı gelir
        pstmt.setInt(3, year);            // 3. ? yerine yıl gelir
        pstmt.setInt(4, 1);               // 4. ? yerine varsayılan olarak "ödünç alınabilir" (1)

        pstmt.executeUpdate();            // Komutu çalıştırır
        System.out.println("Kitap başarıyla eklendi.");
    } catch (SQLException e) {
        System.err.println("Kitap eklenirken hata oluştu: " + e.getMessage());
    }
}

         */



    }
    public static void listAllBooks() {}
    public static void listLentOutBooks() {}
    public static void listAvailableBooks() {

         /* 
            String sql = "SELECT * FROM books WHERE isborrowed = 1";
        
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
        
                boolean found = false;
        
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("bookname");
                    String author = rs.getString("bookauthor");
                    int year = rs.getInt("bookyear");
        
                    System.out.println("ID: " + id +
                                       ", Adı: " + name +
                                       ", Yazar: " + author +
                                       ", Yıl: " + year);
                    found = true;
                }
        
                if (!found) {
                    System.out.println("Müsait kitap bulunamadı.");
                }
        
            } catch (SQLException e) {
                System.err.println("Sorgulama hatası: " + e.getMessage());
            }
        */
        
    }
    public static void updateBookAvailability() {


         /*public static void updateBookAvailability(int bookId, boolean isAvailable, Integer borrowerId) {
            String sql = "UPDATE books SET isborrowed = ?, borrowerid = ? WHERE id = ?";
        
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, isAvailable ? 1 : 0); // 1: müsait, 0: ödünçte
                if (borrowerId != null) {
                    pstmt.setInt(2, borrowerId);      // borrowerid verildi ise güncelle
                } else {
                    pstmt.setNull(2, Types.INTEGER);  // borrowerid null yapılır
                }
                pstmt.setInt(3, bookId);              // Hangi kitap güncelleniyor?
        
                int affected = pstmt.executeUpdate(); // Güncellenen satır sayısı
                if (affected > 0) {
                    System.out.println("Kitap başarıyla güncellendi.");
                } else {
                    System.out.println("Belirtilen ID'ye sahip kitap bulunamadı.");
                }
        
            } catch (SQLException e) {
                System.err.println("Kitap güncellenirken hata oluştu: " + e.getMessage());
            }
        }*/




    }
    
}

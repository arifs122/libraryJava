package database;

import model.Book;

import javax.swing.table.DefaultTableModel;
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
                + "booktype TEXT NOT NULL,"
                + "bookname TEXT NOT NULL,"
                + "bookauthor TEXT NOT NULL,"
                + "bookyear INTEGER NOT NULL,"
                + "availability INTEGER NOT NULL DEFAULT 1 CHECK(availibility IN (0, 1)),"
                + "borrowerid INTEGER DEFAULT NULL)";
     
     try (Statement stmt = conn.createStatement()) {
         stmt.execute(sql);
         System.out.println("The book database has been initialized, and is ready to use.");
         
     } catch (SQLException e) {
         System.err.println("Error while trying to initialize the book database: " + e.getMessage());
         System.exit(1);
     }
    }
    
        public static void insertBook(Book book) {
    String sql = "INSERT INTO books (booktype, bookname, bookauthor, bookyear) VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, book.getBookType());
        pstmt.setString(2, book.getBookName());
        pstmt.setString(3, book.getBookAuthor());
        pstmt.setInt(4, book.getBookYear());

        // borrower id ve availability book oluştururken default olarak ekleniyor
        pstmt.executeUpdate();            // Komutu çalıştırır
        System.out.println("Kitap başarıyla eklendi.");
    } catch (SQLException e) {
        System.err.println("Kitap eklenirken hata oluştu: " + e.getMessage());
    }

}

         



    
    public static void listAllBooks() {}
    public static void listLentOutBooks() {}
    public static DefaultTableModel listAvailableBooks() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Book Type");
        model.addColumn("Book Name");
        model.addColumn("Book Author");
        model.addColumn("Book Year");
        model.addColumn("Availability");
        model.addColumn("Borrower ID");

        String sql = "SELECT * FROM books WHERE isborrowed=0";
        try(Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while(rs.next()){
                int id = rs.getInt("id");
                String bookType = rs.getString("booktype");
                String bookName = rs.getString("bookname");
                String bookAuthor = rs.getString("bookauthor");
                int bookYear = rs.getInt("bookyear");
                String availability = rs.getString("availability");
                String borrowerId = rs.getString("borrowerid");

                Object[] row = { id, bookType, bookName, bookAuthor, bookYear, availability, borrowerId };
                model.addRow(row);
            }
        }catch(SQLException e){
            System.err.println("Error while trying to list available books: " + e.getMessage());
        }
        return model;
    }

    public static void updateBookAvailability(int bookId, boolean isAvailable, Integer borrowerId) {
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
        }
}

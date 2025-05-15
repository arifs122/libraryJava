package database;

import factories.BookFactory;
import model.Book;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

/*Kitaplarla ilgili database işlemlerinin olduğu class.
Bağlanma,tablo hazırlama,kitap ekleme, listeleme, güncelleme metodları bulunuyor.
* */

public class BookDatabase {
	private static final String URL = "jdbc:sqlite:books.db";
    private static Connection conn;
    //database'e bağlanma
    public static void connect() {
    	try{
    		conn = DriverManager.getConnection(URL);
    		System.out.println("Connected to the book database.");
    	}catch(SQLException e) {
    		System.err.println("Error while trying to connect to the book database: "+e.getMessage());
    	}
    }
    //database tablosu yoksa oluşturuluyor ve hazırlanıyor
    public static void initializeDB() {
    	String sql = "CREATE TABLE IF NOT EXISTS books ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "booktype TEXT NOT NULL,"
                + "bookname TEXT NOT NULL,"
                + "bookauthor TEXT NOT NULL,"
                + "bookyear INTEGER NOT NULL,"
                + "availability INTEGER NOT NULL DEFAULT 1 CHECK(availability IN (0, 1))," //kitap eklendiğinde durumu varsayılan olarak 1
                + "borrowerid INTEGER DEFAULT NULL)"; // kitap eklendiğinde varsayılan olarak ödünç alan kişi yok
     
     try (Statement stmt = conn.createStatement()) {
         stmt.execute(sql);
         System.out.println("The book database has been initialized, and is ready to use.");
         
     } catch (SQLException e) {
         System.err.println("Error while trying to initialize the book database: " + e.getMessage());
         System.exit(1);
     }
    }
    //kitapları database'e ekleme metodu
    public static void insertBook(Book book) {
        String sql = "INSERT INTO books (booktype, bookname, bookauthor, bookyear) VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, book.getBookType());
        pstmt.setString(2, book.getBookName());
        pstmt.setString(3, book.getBookAuthor());
        pstmt.setInt(4, book.getBookYear());

        // borrower id ve availability book oluştururken default olarak ekleniyor
        pstmt.executeUpdate();
        System.out.println("Book added succesfully.");
    } catch (SQLException e) {
        System.err.println("Error while trying to add book: " + e.getMessage());
    }

}
    //tüm kitapları listeleme metodu
    public static DefaultTableModel listAllBooks() {
        //table gösterilirken hücreler artık değiştirilemeyecek
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Book Type");
        model.addColumn("Book Name");
        model.addColumn("Book Author");
        model.addColumn("Book Year");
        model.addColumn("Availability");
        model.addColumn("Borrower ID");

        String sql = "SELECT * FROM books";
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
                String borrowerIdStr = rs.getString("borrowerid");
                Integer borrowerId = borrowerIdStr == null ? null : Integer.parseInt(borrowerIdStr);
                Object[] row = { id, bookType, bookName, bookAuthor, bookYear, availability, borrowerId };
                model.addRow(row);
            }
        }catch(SQLException e){
            System.err.println("Error while trying to list all books: " + e.getMessage());
        }
        return model;
    }
    //mevcut kitapları listeleme metodu
    public static DefaultTableModel listAvailableBooks() {
        //table gösterilirken hücreler artık değiştirilemeyecek
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Book Type");
        model.addColumn("Book Name");
        model.addColumn("Book Author");
        model.addColumn("Book Year");
        model.addColumn("Availability");
        model.addColumn("Borrower ID");

        String sql = "SELECT * FROM books WHERE availability=1";
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
                String borrowerIdStr = rs.getString("borrowerid");
                Integer borrowerId = borrowerIdStr == null ? null : Integer.parseInt(borrowerIdStr);
                Object[] row = { id, bookType, bookName, bookAuthor, bookYear, availability, borrowerId };
                model.addRow(row);
            }
        }catch(SQLException e){
            System.err.println("Error while trying to list available books: " + e.getMessage());
        }
        return model;
    }
    //ödünçteki kitapları listeleme metodu
    public static DefaultTableModel listBorrowedBooks() {
        //table gösterilirken hücreler artık değiştirilemeyecek
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Book Type");
        model.addColumn("Book Name");
        model.addColumn("Book Author");
        model.addColumn("Book Year");
        model.addColumn("Availability");
        model.addColumn("Borrower ID");

        String sql = "SELECT * FROM books WHERE availability=0";
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
                String borrowerIdStr = rs.getString("borrowerid");
                Integer borrowerId = borrowerIdStr == null ? null : Integer.parseInt(borrowerIdStr);
                Object[] row = { id, bookType, bookName, bookAuthor, bookYear, availability, borrowerId };
                model.addRow(row);
            }
        }catch(SQLException e){
            System.err.println("Error while trying to list borrowed books: " + e.getMessage());
        }
        return model;
    }
    //kitap durumunu(mevcut/ödünçte) güncelleyen metod
    public static void updateBookAvailability(int bookId, boolean isAvailable, Integer borrowerId) {
            String sql = "UPDATE books SET availability = ?, borrowerid = ? WHERE id = ?";
        
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, isAvailable ? 1 : 0); // 1: müsait, 0: ödünçte
                if (borrowerId != null) {
                    pstmt.setInt(2, borrowerId);
                } else {
                    pstmt.setNull(2, Types.INTEGER);
                }
                pstmt.setInt(3, bookId);
        
                int affected = pstmt.executeUpdate();
                if (affected > 0) {
                    System.out.println("Book updated successfully.");
                } else {
                    System.out.println("The book with the given ID doesn't exist in the database.");
                }
        
            } catch (SQLException e) {
                System.err.println("Error while trying to update the book: " + e.getMessage());
            }
        }

    //kitap ödünç alma ve geri verme işlemlerinde databaseden o kitabı seçmek için metod
    public static Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("booktype");
                String name = rs.getString("bookname");
                String author = rs.getString("bookauthor");
                int year = rs.getInt("bookyear");
                String borrowerIdStr = rs.getString("borrowerid");
                Integer borrowerId = borrowerIdStr == null ? null : Integer.parseInt(borrowerIdStr);

                return BookFactory.create(type,name,author,year, borrowerId);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Book couldn't be retrieved: " + e.getMessage());
            return null;
        }
    }
}

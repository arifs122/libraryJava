package database;

import factories.MemberFactory;
import model.Member;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

/*Üyelerle ilgili database işlemlerinin yapıldığı class.
Üye ekleme,listeleme ve güncelleme metodları bulunuyor.
* */


public class MemberDatabase {
    private static final String URL = "jdbc:sqlite:members.db";
    private static Connection conn;
    //database'e bağlanma
    public static void connect() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connected to the member database.");
        } catch (SQLException e) {
            System.err.println("Error while trying to connect to the member database: " + e.getMessage());
        }
    }
    //database tablosu yoksa oluşturuluyor ve hazırlanıyor
    public static void initializeDB() {
        String sql = "CREATE TABLE IF NOT EXISTS members ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "membername TEXT NOT NULL,"
                + "memberage INTEGER NOT NULL,"
                + "membergender TEXT NOT NULL,"
                + "canborrow INTEGER NOT NULL DEFAULT 1 CHECK(canborrow IN (0, 1)))";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("The member database has been initialized, and is ready to use.");

        } catch (SQLException e) {
            System.err.println("Error while trying to initialize the member database: " + e.getMessage());
            System.exit(1);
        }
    }
    //database'e member ekleme metodu
    public static void insertMember(Member member) {
        String sql = "INSERT INTO members (membername, memberage, membergender) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setInt(2, member.getAge());
            pstmt.setString(3, member.getGender());


            // canborrow default olarak 1
            pstmt.executeUpdate();            // Komutu çalıştırır
            System.out.println("Üye başarıyla eklendi.");
        } catch (SQLException e) {
            System.err.println("Üye eklenirken hata oluştu: " + e.getMessage());
        }

    }
    //tüm memberları listeleyen metod
    public static DefaultTableModel listMembers() {
        //table gösterilirken hücreler artık değiştirilemeyecek
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Member Name");
        model.addColumn("Member Age");
        model.addColumn("Member Gender");
        model.addColumn("Borrow Status");

        String sql = "SELECT * FROM members";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String memberName = rs.getString("membername");
                int memberAge = rs.getInt("memberage");
                String memberGender = rs.getString("membergender");
                int canBorrow = rs.getInt("canborrow");

                Object[] row = {id, memberName, memberAge, memberGender, canBorrow};
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.err.println("Error while trying to list existing members: " + e.getMessage());
        }
        return model;
    }
    //member'in kitap alabilme durumunu güncelleyen metod
    public static void updateMemberCanBorrow(int memberId, boolean canBorrow) {
        String sql = "UPDATE members SET canborrow = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, canBorrow ? 1 : 0);  // true -> 1 (ödünç alabilir), false -> 0 (alamaz)
            pstmt.setInt(2, memberId);           // Hangi üye güncelleniyor?

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                System.out.println("The borrow status of the member updated successfully.");
            } else {
                System.out.println("The member with the given ID doesn't exist in the database.");
            }

        } catch (SQLException e) {
            System.err.println("Üye bilgisi güncellenirken hata oluştu: " + e.getMessage());
        }
    }
    //ödünç alma ve geri verme işlemlerinde işlemi yapan üyeyi database'den bulmak için
    public static Member getMemberById(int memberId) {
        String sql = "SELECT * FROM members WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("membername");
                int age = rs.getInt("memberage");
                String gender = rs.getString("membergender");
                int canBorrow = rs.getInt("canborrow");

                // BookFactory'den uygun kitap nesnesini al
                return MemberFactory.create(name, age, gender, canBorrow==1);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Member couldn't be retrieved: " + e.getMessage());
            return null;
        }
    }
}
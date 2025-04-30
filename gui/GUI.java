package gui;
import javax.swing.*;

/*bir tane ana frame imiz olacak ve her işlem yaptıgımızda
 * o framedeki her şeyi kaldırıp istedigimiz yeni framedekileri
 * ekleyecegiz. bunun icin ya manuel olarak frame.removeAll()
 * ya da cardLayout(swingin kendisinden) kullanılabilir.
 * butonların ve framelerin olabildigince metodlarin
 * icinde olması kodun temizligini arttirir.
 * ornegin public void btnKitapListele(){} metodu yapilip
 * guiStart içinde gereken yerde calistirilirsa hem kodu 
 * birlestirmek kolay olacak daha sonrasında da kodun oku
 * nabilirligi artmis olacak
 * */ 
/*borrow book ve return book sadece gui da butonlar olarak var olacak
 * işlemleri updateBook ve updateMember üstünden yönetcez 
 * ansiklopedi alınmaya çalışıldığında ya da mevcut olan bir kitap ödünç
 * alınmaya çalışırsa hata mesajı verilip işlem yapılmayacak o yüzden if 
 * blokları kullanılmalı */

/*ana menu framei
 * uye ekle üyeleri listele
 * kitap ekle kitapları listele oduncteki kitaplar mevcut kitaplar
 * kitap odunc al kitap geri al
 *
 *uye ekle framei
 *isim dogum yili kaydet geri
 *kitap ekle framei
 *isim yazar yazım yılı kaydet geri
 *kitap odunc al framei
 *kitap id üye id geri
 *kitap geri ver
 *kitap id (whohasbookdan kim odunc aldiysa bulunup canborrow tam tersine cevrilcek) geri
 *listelemeler db icinde gui kodları da o metodda olacak(defaulttablemodel) geri
 *
 * */
public class GUI {

	private JFrame mainframe;
	private JButton btnAddBooks;
	private JButton btnListBooks;
	private JButton btnBorrowedBooks;
	private JButton btnAvailableBooks;
	private JLabel lblWelcome;

	public void guiStart(){

		mainframe = new JFrame("Library Management System");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setSize(1200, 900);
		mainframe.setLocationRelativeTo(null);
		mainframe.setVisible(true);
		mainframe.setLayout(null);
		System.out.println("The GUI has been initialized, and is ready to use.");

		WelcomeLabel();

	}//mainde cagirabilmek icin gui islemleri start icine yazılcak

	private void WelcomeLabel() {
		lblWelcome = new JLabel("Welcome to the Library Management System! Select an option to continue.", SwingConstants.CENTER);
		lblWelcome.setBounds(350, 20, 500, 30); // x, y, width, height
		mainframe.add(lblWelcome);
	}
	
}

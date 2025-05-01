package gui;
import factories.BookFactory;
import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	private JFrame mainFrame;
	private JButton btnAddBooks;
	private JButton btnListBooks;
	private JButton btnListBorrowedBooks;
	private JButton btnListAvailableBooks;
	private JButton btnReturnBook;
	private JButton btnBorrowBook;
	private JButton btnAddMember;
	private JButton btnListMembers;


    private JPanel mainPanel;
	private CardLayout  cardLayout;
	private JPanel homePanel;
	private JPanel addBookPanel;
	private JPanel listBookPanel;
	private JPanel listBorrowedBooksPanel;
	private JPanel listAvailableBooksPanel;
	private JPanel addMemberPanel;
	private JPanel listMembersPanel;
	private JPanel returnBookPanel;
	private JPanel borrowBookPanel;

	//mainde cagirabilmek icin gui islemleri start icine yazılcak
	public void guiStart(){

		mainFrame = new JFrame("Library Management System");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(600, 700);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		System.out.println("The GUI has been initialized, and is ready to use.");

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		//metodların içinde paneller(kartlar) oluşturuluyor
		createHomePanel();
		createAddBookPanel();
		//createAddMemberPanel();
		//createListMembersPanel();
		//createListBooksPanel();
		//createBorrowBookPanel();
		//createReturnBookPanel();
		//createListAvailableBooks();
		//createListBorrowedBooks();

		//oluşturulan kartları mainpanele ekliyoruz geçiş yaparken burdan yapılcak
		mainPanel.add(homePanel, "HomePanel");
		mainPanel.add(addBookPanel, "AddBookPanel");



		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
	}

	private void createHomePanel(){//ana ekran kartı
		homePanel = new JPanel();
		homePanel.setLayout(null);
		WelcomeLabel();
		addHomeButtons();
	}
	//ana sayfadaki butonlar buraya
	private void addHomeButtons(){
		btnAddBooks = new JButton("Add Books");
		btnAddBooks.setBounds(50, 50, 100, 50);
		btnAddBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "AddBookPanel");
			}
		});
		homePanel.add(btnAddBooks);
	}
	//add book butonuna tıklanınca geçilecek olan panel
	private void createAddBookPanel(){
		addBookPanel = new JPanel(null);
		JLabel bookNameLabel = new JLabel("Add Book");
		bookNameLabel.setBounds(50, 50, 100, 30);
		JTextField bookNameTxt = new JTextField();
		bookNameTxt.setBounds(120, 50, 100, 30);
		addBookPanel.add(bookNameTxt);
		addBookPanel.add(bookNameLabel);
		JLabel bookAuthorLabel = new JLabel("Book Author");
		bookAuthorLabel.setBounds(50, 100, 100, 30);
		JTextField bookAuthorTxt = new JTextField();
		bookAuthorTxt.setBounds(120, 100, 100, 30);
		addBookPanel.add(bookAuthorTxt);
		addBookPanel.add(bookAuthorLabel);
		JLabel bookYearLabel = new JLabel("Book Year");
		bookYearLabel.setBounds(50, 150, 100, 30);
		JTextField bookYearTxt = new JTextField();
		bookYearTxt.setBounds(120, 150, 100, 30);
		addBookPanel.add(bookYearTxt);
		addBookPanel.add(bookYearLabel);
		JLabel bookTypeLabel = new JLabel("Book Type");
		bookTypeLabel.setBounds(50, 200, 100, 30);
		JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"novel", "encyclopedia", "poetry"});
		typeComboBox.setBounds(120, 200, 100, 30);
		addBookPanel.add(bookTypeLabel);
		addBookPanel.add(typeComboBox);


		JButton addBookButton = new JButton("Add Book");
		addBookButton.setBounds(50, 300, 100, 30);
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = bookNameTxt.getText();
				String author = bookAuthorTxt.getText();
				int year = Integer.parseInt(bookYearTxt.getText());
				String type = typeComboBox.getSelectedItem().toString();
				//book nesnesini kitap türüne göre factory design pattern ile oluşturuyoruz
				Book book = BookFactory.create(type, name, author, year);
			}
		});
		addBookPanel.add(addBookButton);


	}


	private void WelcomeLabel() {
        JLabel lblWelcome = new JLabel("Welcome to the Library Management System! Select an option to continue.", SwingConstants.CENTER);
		lblWelcome.setBounds(50, 20, 500, 30); // x, y, width, height
		mainFrame.add(lblWelcome);
	}
	
}

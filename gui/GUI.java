package gui;

import database.BookDatabase;
import database.MemberDatabase;
import factories.BookFactory;
import factories.MemberFactory;
import interfaces.NotBorrowable;
import model.Book;
import model.Member;
import util.IconHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/*Tüm kullanıcı arayüzünün hazırlandığı class.
JavaSwing kullanıldı ve tüm işlemler CardLayout kullanılarak 1 frame üstünde yapılıyor
Gerekli yerlerde karakter/sayı girişleri engellendi
Hatalı işlemlerde hata mesajları ile kullanıcı uyarılıyor
Kod temizliğini sağlamak amacıyla tekrar eden kod parçaları metodlar ile yönetildi
Tablolu panellerde form ve tablo olmak üzere 2 ayrı panel kullanıldı
* */

public class GUI {

	private JFrame mainFrame;

    private JPanel mainPanel;
	private CardLayout cardLayout;
	private JPanel homePanel;
	private JPanel addBookPanel;
	private JPanel addMemberPanel;
	private JPanel returnBookPanel;
	private JPanel borrowBookPanel;
	private JTable allBooksTable;
	private JTable availableBookTable;
	private JTable borrowedBookTable;
	private JTable memberTable;
	private JTable menuMembersTable;
	private JTable menuBooksTable;


	// mainde arayüzü başlatmak için çağırılacak metod
	public void guiStart() {

		mainFrame = new JFrame("Library Management System");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		IconHelper.setAppIcon(mainFrame);
		mainFrame.setSize(1000, 500);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		System.out.println("The GUI has been initialized, and is ready to use.");

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		//metodların içinde paneller(kartlar) oluşturuluyor
		createHomePanel();
		createAddBookPanel();
		createAddMemberPanel();
		createBorrowBookPanel();
		createReturnBookPanel();

		//oluşturulan kartları mainpanele ekliyoruz geçiş yaparken burdan yapılacak
		mainPanel.add(homePanel, "HomePanel");
		mainPanel.add(addBookPanel, "AddBookPanel");
		mainPanel.add(borrowBookPanel, "BorrowBookPanel");
		mainPanel.add(returnBookPanel, "ReturnBookPanel");
		mainPanel.add(addMemberPanel, "AddMemberPanel");


		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
	}
	//ana ekran kartı oluşturuluyor
	private void createHomePanel() {
		homePanel = new JPanel(null);
		homePanel.setBackground(new Color(222, 222, 225));

		WelcomeLabel();
		addHomeButtons();

		// Kitaplar tablosu başlığı
		JLabel booksTableTitle = new JLabel("All Books", SwingConstants.CENTER);
		booksTableTitle.setBounds(580, 90, 180, 30);
		booksTableTitle.setFont(new Font("Californian FB", Font.BOLD, 15));
		homePanel.add(booksTableTitle);

		// Kitaplar tablosu
		DefaultTableModel bookModel = BookDatabase.listAllBooks();
		menuBooksTable = new JTable(bookModel); // artık global değişken
		menuBooksTable.setDefaultEditor(Object.class, null);

		int[] bookColumnWidths = {30, 80, 150, 120, 70, 70, 80};
		TableColumnModel bookColModel = menuBooksTable.getColumnModel();
		for (int i = 0; i < bookColumnWidths.length; i++) {
			bookColModel.getColumn(i).setPreferredWidth(bookColumnWidths[i]);
			bookColModel.getColumn(i).setResizable(false);
		}
		JScrollPane booksScroll = new JScrollPane(menuBooksTable);
		booksScroll.setBounds(380, 110, 600, 140);
		homePanel.add(booksScroll);

		// Üyeler tablosu başlığı
		JLabel membersTableTitle = new JLabel("All Members", SwingConstants.CENTER);
		membersTableTitle.setBounds(580, 250, 180, 30);
		membersTableTitle.setFont(new Font("Californian FB", Font.BOLD, 15));
		homePanel.add(membersTableTitle);

		// Üyeler tablosu
		DefaultTableModel memberModel = MemberDatabase.listMembers();
		menuMembersTable = new JTable(memberModel); // artık global değişken
		menuMembersTable.setDefaultEditor(Object.class, null);

		int[] memberColumnWidths = {30, 200, 120, 150, 100};
		TableColumnModel memberColModel = menuMembersTable.getColumnModel();
		for (int i = 0; i < memberColumnWidths.length; i++) {
			memberColModel.getColumn(i).setPreferredWidth(memberColumnWidths[i]);
			memberColModel.getColumn(i).setResizable(false);
		}

		JScrollPane membersScroll = new JScrollPane(menuMembersTable);
		membersScroll.setBounds(380, 270, 600, 140);
		homePanel.add(membersScroll);
	}


	//ana sayfadaki butonlar oluşturuluyor ve action'ları atanıyor
	private void addHomeButtons() {
        JButton btnAddBooks = new JButton("Add Book");
		setButtonLook(btnAddBooks);
		btnAddBooks.setBounds(70, 110, 125, 30);
		btnAddBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "AddBookPanel");
			}
		});
        JButton btnAddMember = new JButton("Add Member");
		setButtonLook(btnAddMember);
		btnAddMember.setBounds(70, 190, 125, 30);
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "AddMemberPanel");
			}
		});
        JButton btnBorrowBook = new JButton("Borrow Book");
		setButtonLook(btnBorrowBook);
		btnBorrowBook.setBounds(70, 270, 125, 30);
		btnBorrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "BorrowBookPanel");
			}
		});
        JButton btnReturnBook = new JButton("Return Book");
		setButtonLook(btnReturnBook);
		btnReturnBook.setBounds(70, 350, 125, 30);
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { cardLayout.show(mainPanel, "ReturnBookPanel");}
		});
		JButton aboutButton = new JButton("About");
		setButtonLook(aboutButton);
		aboutButton.setBounds(880, 420, 80, 30);
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAboutMessage();
			}
		});
		homePanel.add(aboutButton);
		homePanel.add(btnAddMember);
		homePanel.add(btnAddBooks);
		homePanel.add(btnBorrowBook);
		homePanel.add(btnReturnBook);

	}

	//add book butonuyla açılan kitap ekleme paneli
	private void createAddBookPanel() {
		addBookPanel = new JPanel(null);
		addBookPanel.setBackground(new Color(222, 222, 225));
		JLabel addBookMessage = new JLabel("Please enter the book information.", SwingConstants.CENTER);
		addBookMessage.setFont(new Font("Californian FB", Font.BOLD, 20));
		addBookMessage.setBounds(250, 30, 500, 30);
		addBookPanel.add(addBookMessage);
		JLabel bookNameLabel = new JLabel("Book Name");
		bookNameLabel.setBounds(80, 90, 100, 30);
		JTextField bookNameTxt = new JTextField();
		bookNameTxt.setBounds(155, 90, 150, 30);
		setCharacterRules(bookNameTxt);
		addBookPanel.add(bookNameTxt);
		addBookPanel.add(bookNameLabel);
		JLabel bookAuthorLabel = new JLabel("Book Author");
		bookAuthorLabel.setBounds(80, 140, 100, 30);
		JTextField bookAuthorTxt = new JTextField();
		bookAuthorTxt.setBounds(155, 140, 150, 30);
		setCharacterRules(bookAuthorTxt);
		addBookPanel.add(bookAuthorTxt);
		addBookPanel.add(bookAuthorLabel);
		JLabel bookYearLabel = new JLabel("Book Year");
		bookYearLabel.setBounds(80, 190, 100, 30);
		JTextField bookYearTxt = new JTextField();
		bookYearTxt.setBounds(155, 190, 75, 30);
		setNumberRules(bookYearTxt);
		addBookPanel.add(bookYearTxt);
		addBookPanel.add(bookYearLabel);
		JLabel bookTypeLabel = new JLabel("Book Type");
		bookTypeLabel.setBounds(80, 240, 100, 30);
		//seçilebilir 3 kitap türü için kutu
		JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Novel", "Encyclopedia", "Poetry"});
		typeComboBox.setBounds(155, 240, 150, 30);
		addBookPanel.add(bookTypeLabel);
		addBookPanel.add(typeComboBox);
		//enter tuşu ile textboxlar arası geçiş yapmak için
		bookNameTxt.addActionListener(_ -> bookAuthorTxt.requestFocusInWindow());
		bookAuthorTxt.addActionListener(_ -> bookYearTxt.requestFocusInWindow());

		//geri butonu
		JButton backButton = new JButton("Back");
		setButtonLook(backButton);
		backButton.setBounds(850, 400, 100, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		addBookPanel.add(backButton);

		JButton addBookButton = new JButton("Add Book");
		setButtonLook(addBookButton);
		addBookButton.setBounds(200, 300, 100, 30);
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = bookNameTxt.getText();
					String author = bookAuthorTxt.getText();
					int year = Integer.parseInt(bookYearTxt.getText());
					String type = Objects.requireNonNull(typeComboBox.getSelectedItem()).toString();
					//book nesnesini kitap türüne göre factory design pattern ile oluşturuyoruz
					Book book = BookFactory.create(type, name, author, year, null);
					JOptionPane.showMessageDialog(null, "Book added successfully.");
					bookNameTxt.setText("");
					bookAuthorTxt.setText("");
					bookYearTxt.setText("");
					BookDatabase.insertBook(book);
					//işlem gerçekleştiğinde tablolar yenileniyor
					refreshAllBooksTable();
					refreshAvailableBookTable();
					refreshMenuBooksTable();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Unable to add the book. Check the details and try again.");
					ex.printStackTrace();
				}

			}
		});
		addBookPanel.add(addBookButton);

		//tüm kitapların listelendiği tablo ve başlığı
		JLabel tableTitleLabel = new JLabel("All Books", SwingConstants.CENTER);
		tableTitleLabel.setBounds(550, 80, 200, 30);
		tableTitleLabel.setFont(new Font("Californian FB", Font.BOLD, 15));
		addBookPanel.add(tableTitleLabel);

		DefaultTableModel model = BookDatabase.listAllBooks();
		allBooksTable = new JTable(model);

		//table sutunları artık ayarlanamıyor ve kendimiz büyüklüklerini ayarladık
		allBooksTable.setDefaultEditor(Object.class, null);
		TableColumnModel columnModel = allBooksTable.getColumnModel();
		int[] widths = {30, 80, 150, 120, 70, 70, 80};
		for (int i = 0; i < widths.length; i++) {
			columnModel.getColumn(i).setPreferredWidth(widths[i]);
			columnModel.getColumn(i).setResizable(false);
		}
		JScrollPane scrollPane = new JScrollPane(allBooksTable);
		scrollPane.setBounds(350, 100, 600, 250);
		addBookPanel.add(scrollPane);


	}
	//borrow book butonuyla açılan ödünç alma işleminin yapıldığı panel
	private void createBorrowBookPanel() {
		borrowBookPanel = new JPanel(null);
		borrowBookPanel.setBackground(new Color(222, 222, 225));
		JLabel borrowBookMessage = new JLabel("Please enter the book ID and the borrower ID.", SwingConstants.CENTER);
		borrowBookMessage.setFont(new Font("Californian FB", Font.BOLD, 20));
		borrowBookMessage.setBounds(175, 40, 650, 30);

        JPanel borrowBookFormPanel = new JPanel(null);
		borrowBookFormPanel.setBackground(new Color(222, 222, 225));
		borrowBookFormPanel.setBounds(80, 100, 400, 300);

		JLabel bookIdLabel = new JLabel("Book ID");
		bookIdLabel.setBounds(0, 0, 100, 30);
		JTextField bookIdTxt = new JTextField();
		bookIdTxt.setBounds(70, 0, 100, 30);
		setNumberRules(bookIdTxt);
		borrowBookFormPanel.add(bookIdLabel);
		borrowBookFormPanel.add(bookIdTxt);
		JLabel borrowerIdLabel = new JLabel("Borrower ID");
		borrowerIdLabel.setBounds(0, 50, 100, 30);
		JTextField borrowerIdTxt = new JTextField();
		borrowerIdTxt.setBounds(70, 50, 100, 30);
		setNumberRules(borrowerIdTxt);
		borrowBookFormPanel.add(borrowerIdLabel);
		borrowBookFormPanel.add(borrowerIdTxt);
		//textboxlar arası enter ile geçiş
		bookIdTxt.addActionListener(_ -> borrowerIdTxt.requestFocusInWindow());

		JButton borrowBookButton = new JButton("Borrow Book");
		setButtonLook(borrowBookButton);
		borrowBookButton.setBounds(110, 100, 120, 30);
		borrowBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{

					int id = Integer.parseInt(borrowerIdTxt.getText());
					int bookId = Integer.parseInt(bookIdTxt.getText());
					Book book = BookDatabase.getBookById(bookId);
					Member member = MemberDatabase.getMemberById(id);
					//ansiklopedilerin ödünç alınamaması için implement edilen NotBorrowable instanceof ile kontrol ediliyor
					if (book instanceof NotBorrowable) {
						JOptionPane.showMessageDialog(null, "You can't borrow an encyclopedia.","Borrow Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
                    assert member != null;
					//eğer bu kitabı almak isteyen üyenin halihazırda ödünç aldığı bi kitap varsa kitabı alamıyor
                    if (!(member.getCanBorrow())){
						JOptionPane.showMessageDialog(null, "The member with that ID can't borrow a book.","Borrow Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					BookDatabase.updateBookAvailability(bookId,false, id);
					MemberDatabase.updateMemberCanBorrow(id,false);
					//işlem gerçekleştiğinde tablolar yenileniyor
					refreshAllBooksTable();
					refreshAvailableBookTable();
					refreshBorrowedBookTable();
					refreshMemberTable();
					refreshMenuBooksTable();
					refreshMenuMembersTable();
					JOptionPane.showMessageDialog(null, "Book borrowed successfully.");
					bookIdTxt.setText("");
					borrowerIdTxt.setText("");

				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Unable to borrow the book, check the details and try again.");
					ex.printStackTrace();
				}
			}
		});
		borrowBookFormPanel.add(borrowBookButton);
		//ödünç alınabilecek kitaplarin tablosu ve başlığı
		JLabel tableTitleLabel = new JLabel("On-Shelf Books", SwingConstants.CENTER);
		tableTitleLabel.setBounds(560, 80, 200, 30);
		tableTitleLabel.setFont(new Font("Californian FB", Font.BOLD, 15));
		borrowBookPanel.add(tableTitleLabel);

		DefaultTableModel model = BookDatabase.listAvailableBooks();
		availableBookTable = new JTable(model);
		//tabloda istenilen kitaba tıklanarak kitap seçilebiliyor
		availableBookTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRow = availableBookTable.getSelectedRow();
				if (selectedRow != -1) {
					Object id = availableBookTable.getValueAt(selectedRow, 0);
					bookIdTxt.setText(id.toString());
				}
			}
		});

		//table sutunları artık ayarlanamıyor ve kendimiz büyüklüklerini ayarladık
		availableBookTable.setDefaultEditor(Object.class, null);
		TableColumnModel columnModel = availableBookTable.getColumnModel();
		int[] widths = {30, 90, 150, 120, 70, 60, 80};
		for (int i = 0; i < widths.length; i++) {
			columnModel.getColumn(i).setPreferredWidth(widths[i]);
			columnModel.getColumn(i).setResizable(false);
		}
		JScrollPane scrollPane = new JScrollPane(availableBookTable);
		scrollPane.setBounds(350, 100, 600, 250);
		borrowBookPanel.add(scrollPane);

		//geri butonu
		JButton backButton = new JButton("Back");
		setButtonLook(backButton);
		backButton.setBounds(850, 400, 100, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});

		borrowBookPanel.add(backButton);
		borrowBookPanel.add(borrowBookMessage);
		borrowBookPanel.add(borrowBookFormPanel);
	}
	//add member butonuyla açılan üye ekleme paneli
	private void createAddMemberPanel() {
		addMemberPanel = new JPanel(null);
		addMemberPanel.setBackground(new Color(222, 222, 225));
		JLabel addMemberMessage = new JLabel("Please enter the member information.", SwingConstants.CENTER);
		addMemberMessage.setFont(new Font("Californian FB", Font.BOLD, 20));
		addMemberMessage.setBounds(310, 30, 400, 30);
		addMemberPanel.add(addMemberMessage);
		JLabel memberNameLabel = new JLabel("Member Name");
		memberNameLabel.setBounds(80, 90, 100, 30);
		JTextField memberNameTxt = new JTextField();
		memberNameTxt.setBounds(180, 90, 150, 30);
		setCharacterRules(memberNameTxt);
		addMemberPanel.add(memberNameLabel);
		addMemberPanel.add(memberNameTxt);
		JLabel memberAgeLabel = new JLabel("Member Age");
		memberAgeLabel.setBounds(80, 140, 100, 30);
		JTextField memberAgeTxt = new JTextField();
		memberAgeTxt.setBounds(180, 140, 75, 30);
		setNumberRules(memberAgeTxt);
		addMemberPanel.add(memberAgeLabel);
		addMemberPanel.add(memberAgeTxt);
		JLabel memberGenderLabel = new JLabel("Member Gender");
		memberGenderLabel.setBounds(80, 190, 100, 30);
		//member cinsiyeti için 2 seçenekli kutu
		JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
		genderComboBox.setBounds(180, 190, 75, 30);
		addMemberPanel.add(memberGenderLabel);
		addMemberPanel.add(genderComboBox);
		genderComboBox.setSelectedItem(null);
		//enter ile textboxlar arasi geçiş
		memberNameTxt.addActionListener(_ -> memberAgeTxt.requestFocusInWindow());

		//var olan üyelerin listelendiği tablo ve başlığı
		JLabel tableTitleLabel = new JLabel("Existing Members", SwingConstants.CENTER);
		tableTitleLabel.setBounds(610, 80, 200, 30);
		tableTitleLabel.setFont(new Font("Californian FB", Font.BOLD, 15));
		addMemberPanel.add(tableTitleLabel);

		DefaultTableModel model = MemberDatabase.listMembers();
		memberTable = new JTable(model);

		//table sutunları artık ayarlanamıyor ve kendimiz büyüklüklerini ayarladık
		memberTable.setDefaultEditor(Object.class, null);
		TableColumnModel columnModel = memberTable.getColumnModel();
		int[] widths = {20, 150, 60, 80, 60};
		for (int i = 0; i < widths.length; i++) {
			columnModel.getColumn(i).setPreferredWidth(widths[i]);
			columnModel.getColumn(i).setResizable(false);
		}
		JScrollPane scrollPane = new JScrollPane(memberTable);
		scrollPane.setBounds(450, 100, 500, 250);
		addMemberPanel.add(scrollPane);

		JButton addMemberButton = new JButton("Add Member");
		setButtonLook(addMemberButton);
		addMemberButton.setBounds(200, 260, 140, 30);
		addMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = memberNameTxt.getText();
					int age = Integer.parseInt(memberAgeTxt.getText());
					String gender = Objects.requireNonNull(genderComboBox.getSelectedItem()).toString();
					Member member = MemberFactory.create(name,age,gender,true);
					MemberDatabase.insertMember(member);
					refreshMemberTable();
					refreshMenuMembersTable();
					JOptionPane.showMessageDialog(null, "Member added successfully.");
					memberNameTxt.setText("");
					memberAgeTxt.setText("");
					genderComboBox.setSelectedItem(null);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Unable to add the member. Check the details and try again.");
					ex.printStackTrace();

				}
			}


		});
		addMemberPanel.add(addMemberButton);

		JButton backButton = new JButton("Back");
		backButton.setBounds(850, 400, 100, 30);
		setButtonLook(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		addMemberPanel.add(backButton);
	}
	//return book butonuyla açılan kitap geri verme metodu
	private void createReturnBookPanel() {
		returnBookPanel = new JPanel(null);
		returnBookPanel.setBackground(new Color(222, 222, 225));
		JLabel returnBookMessage = new JLabel("Please enter the ID of the book you want to return.", SwingConstants.CENTER);
		returnBookMessage.setFont(new Font("Californian FB", Font.BOLD, 20));
		returnBookMessage.setBounds(175, 40, 650, 30);

        JPanel returnBookFormPanel = new JPanel(null);
		returnBookFormPanel.setBackground(new Color(222, 222, 225));
		returnBookFormPanel.setBounds(80, 100, 400, 300);

		JLabel bookIdLabel = new JLabel("Book ID");
		bookIdLabel.setBounds(0, 0, 100, 30);
		JTextField bookIdTxt = new JTextField();
		bookIdTxt.setBounds(70, 0, 100, 30);
		setNumberRules(bookIdTxt);
		returnBookFormPanel.add(bookIdLabel);
		returnBookFormPanel.add(bookIdTxt);

		JButton returnBookButton = new JButton("Return Book");
		setButtonLook(returnBookButton);
		returnBookButton.setBounds(110, 100, 120, 30);
		returnBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{

					int bookId = Integer.parseInt(bookIdTxt.getText());
					Book book = BookDatabase.getBookById(bookId);
					if (book instanceof NotBorrowable) {
						JOptionPane.showMessageDialog(null, "You can't borrow/return an encyclopedia.","Borrow Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					BookDatabase.updateBookAvailability(bookId,true, null);
                    assert book != null;
                    MemberDatabase.updateMemberCanBorrow(book.getBorrowerId(),true);
					refreshAllBooksTable();
					refreshAvailableBookTable();
					refreshBorrowedBookTable();
					refreshMemberTable();
					refreshMenuMembersTable();
					refreshMenuBooksTable();
					JOptionPane.showMessageDialog(null, "Book returned successfully.");
					bookIdTxt.setText("");
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Unable to return the book, check the details and try again.");
					ex.printStackTrace();
				}
			}
		});
		returnBookFormPanel.add(returnBookButton);
		//ödünç alınmış kitaplar tablosu ve başlığı
		JLabel tableTitleLabel = new JLabel("Already Borrowed Books", SwingConstants.CENTER);
		tableTitleLabel.setBounds(550, 80, 200, 30);
		tableTitleLabel.setFont(new Font("Californian FB", Font.BOLD, 15));
		returnBookPanel.add(tableTitleLabel);

		DefaultTableModel model = BookDatabase.listBorrowedBooks();
		borrowedBookTable = new JTable(model);

		borrowedBookTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRow = borrowedBookTable.getSelectedRow();
				if (selectedRow != -1) {
					Object id = borrowedBookTable.getValueAt(selectedRow, 0);
					bookIdTxt.setText(id.toString());
				}
			}
		});

		//table sutunları artık ayarlanamıyor ve kendimiz büyüklüklerini ayarladık
		borrowedBookTable.setDefaultEditor(Object.class, null);
		TableColumnModel columnModel = borrowedBookTable.getColumnModel();
		int[] widths = {30, 90, 150, 120, 70, 60, 80};
		for (int i = 0; i < widths.length; i++) {
			columnModel.getColumn(i).setPreferredWidth(widths[i]);
			columnModel.getColumn(i).setResizable(false);
		}
		JScrollPane scrollPane = new JScrollPane(borrowedBookTable);
		scrollPane.setBounds(350, 100, 600, 250);
		returnBookPanel.add(scrollPane);


		JButton backButton = new JButton("Back");
		setButtonLook(backButton);
		backButton.setBounds(850, 400, 100, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});

		returnBookPanel.add(backButton);
		returnBookPanel.add(returnBookMessage);
		returnBookPanel.add(returnBookFormPanel);
	}
	//ana menüde welcome mesajı için metod
	private void WelcomeLabel() {
		JLabel lblWelcome = new JLabel("Welcome to the Library Management System! Select an option to continue.", SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Californian FB", Font.BOLD, 20));
		lblWelcome.setBounds(50, 30, 900, 30); // x, y, width, height
		mainFrame.add(lblWelcome);
	}

	//button görünümleri için metod
	private void setButtonLook(JButton button) {
		button.setFocusPainted(false);
		button.setContentAreaFilled(true);
		button.setBorderPainted(true);
		button.setOpaque(true);
		button.setBackground(new Color(204, 229, 255));
		button.setForeground(Color.black);
		button.setFont(new Font("Californian FB", Font.BOLD, 13));
	}

	//ana menüye dönerken kullanılacak back butonlarının içindeki metod
	private void back() {
		cardLayout.show(mainPanel, "HomePanel");
		WelcomeLabel();
	}

	private void refreshMenuBooksTable() {
		if (menuBooksTable != null) {
			DefaultTableModel model = BookDatabase.listAllBooks();
			menuBooksTable.setModel(model);
			menuBooksTable.setDefaultEditor(Object.class, null);

			int[] widths = {30, 80, 150, 120, 70, 70, 80};
			TableColumnModel columnModel = menuBooksTable.getColumnModel();
			for (int i = 0; i < widths.length; i++) {
				columnModel.getColumn(i).setPreferredWidth(widths[i]);
				columnModel.getColumn(i).setResizable(false);
			}
		}
	}

	private void refreshMenuMembersTable() {
		if (menuMembersTable != null) {
			DefaultTableModel model = MemberDatabase.listMembers();
			menuMembersTable.setModel(model);
			menuMembersTable.setDefaultEditor(Object.class, null);

			int[] widths = {30, 200, 120, 150, 100};
			TableColumnModel columnModel = menuMembersTable.getColumnModel();
			for (int i = 0; i < widths.length; i++) {
				columnModel.getColumn(i).setPreferredWidth(widths[i]);
				columnModel.getColumn(i).setResizable(false);
			}
		}
	}

	//işlemler sonrası tabloları güncel tutmak için metod
	private void refreshAllBooksTable() {
		if (allBooksTable != null) {
			DefaultTableModel model = BookDatabase.listAllBooks();
			allBooksTable.setModel(model);
		}
		if(allBooksTable != null) {
			allBooksTable.setDefaultEditor(Object.class, null);
			TableColumnModel columnModel = allBooksTable.getColumnModel();
			int[] widths = {30, 80, 150, 120, 70, 70, 80};
			for (int i = 0; i < widths.length; i++) {
				columnModel.getColumn(i).setPreferredWidth(widths[i]);
				columnModel.getColumn(i).setResizable(false);
			}
			allBooksTable.setDefaultEditor(Object.class, null); // hücre düzenlemesini engelle
		}
	}
	//işlemler sonrası tabloları güncel tutmak için metod
	private void refreshAvailableBookTable() {
		if (availableBookTable != null) {
			DefaultTableModel model = BookDatabase.listAvailableBooks();
			availableBookTable.setModel(model);
		}
		//yenilendikten sonra tablo şekli tekrardan yapılıyor
		if (availableBookTable != null) {
			TableColumnModel columnModel = availableBookTable.getColumnModel();
			int[] widths = {30, 90, 150, 120, 70, 60, 80};
			for (int i = 0; i < widths.length; i++) {
				columnModel.getColumn(i).setPreferredWidth(widths[i]);
				columnModel.getColumn(i).setResizable(false);
			}
			availableBookTable.setDefaultEditor(Object.class, null); // hücre düzenlemesini engelle
		}
	}
	//işlemler sonrası tabloları güncel tutmak için metod
	private void refreshBorrowedBookTable() {
		if (borrowedBookTable != null) {
			DefaultTableModel model = BookDatabase.listBorrowedBooks();
			borrowedBookTable.setModel(model);
		}
		//yenilendikten sonra tablo şekli tekrardan yapılıyor
		if (borrowedBookTable != null) {
			TableColumnModel columnModel = borrowedBookTable.getColumnModel();
			int[] widths = {30, 90, 150, 120, 70, 60, 80};
			for (int i = 0; i < widths.length; i++) {
				columnModel.getColumn(i).setPreferredWidth(widths[i]);
				columnModel.getColumn(i).setResizable(false);
			}
			borrowedBookTable.setDefaultEditor(Object.class, null); // hücre düzenlemesini engelle
		}
	}
	//işlemler sonrası tabloları güncel tutmak için metod
	private void refreshMemberTable() {
		if (memberTable != null) {
			DefaultTableModel model = MemberDatabase.listMembers();
			memberTable.setModel(model);
		}
		//yenilendikten sonra tablo şekli tekrardan yapılıyor
		if (memberTable != null) {
			TableColumnModel columnModel = memberTable.getColumnModel();
			int[] widths = {20, 150, 60, 80, 60};
			for (int i = 0; i < widths.length; i++) {
				columnModel.getColumn(i).setPreferredWidth(widths[i]);
				columnModel.getColumn(i).setResizable(false);
			}
			memberTable.setDefaultEditor(Object.class, null); // hücre düzenlemesini engelle
		}
	}
	//sadece rakaml girilmesi gereken textboxlara implement edilen metod
	public void setNumberRules(JTextField numberRules) {
		numberRules.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null) return;
				if (str.matches("\\d+")) { // sadece rakamlar izinli
					super.insertString(offs, str, a);
				}
			}
		});
	}
	//sadece karakter girilmesi gereken textboxlara implement edilen metod
	public void setCharacterRules(JTextField characterRules) {
		characterRules.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null) return;
				if (str.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ0-9\\s]+")) { // Türkçe harfler, sayılar ve boşluk
					super.insertString(offs, str, a);
				}
			}
		});
	}
	//ana menüde kullanıcıyı bilgilendirme amaçlı butonun metodu
	private void showAboutMessage() {
		String message = """
                Welcome!
                
                - You can add, borrow, return books and add members.
                - Members and books are being stored in databases.
                - Member and book ID's are being assigned automatically.
                - Encyclopedias are not borrowable.
                - Members can only borrow 1 book at a time.
                - You can select your book by clicking to list in borrow menu.
                - Borrow Status 1 means that that member can borrow a book.
                - Availability 1 means that that book can be borrowed.
                - You can surf between input boxes with enter.\s""";

		JTextArea textArea = new JTextArea(message);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setBackground(null);
		textArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(350, 200));

		JOptionPane.showMessageDialog(null, scrollPane, "About Library Management System", JOptionPane.INFORMATION_MESSAGE);
	}
}

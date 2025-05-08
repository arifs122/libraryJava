package kutuphane;

import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Welcome to the Library Management System.\n");

		database.BookDatabase.connect();
		database.BookDatabase.initializeDB();
		database.MemberDatabase.connect();
		database.MemberDatabase.initializeDB();

		//object olusturulmazsa "static methoddan static olmayan method call edilemez" diye hata veriyor
		gui.GUI gui = new gui.GUI();
		gui.guiStart();
	}

}

package kutuphane;

public class Main {

	public static void main(String[] args) {

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

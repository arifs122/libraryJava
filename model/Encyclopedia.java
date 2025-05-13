package model;

import interfaces.NotBorrowable;
//bookdan kalıtımla oluşturulan ve NotBorrowable inteface'ini implement eden Encyclopedia class'ı
//ansiklopedi ödünç alınamayacağı için notborrowable interface'ini implement ediyor
public class Encyclopedia extends Book implements NotBorrowable {

    public Encyclopedia(String bookType, String bookName, String bookAuthor, int bookYear, boolean isBorrowed, Integer borrowerId) {
        super(bookType, bookName, bookAuthor, bookYear, isBorrowed, borrowerId);
    }

    @Override
    public void cantBorrow() {
        System.out.println("You can't borrow an Encyclopedia");
    }
}

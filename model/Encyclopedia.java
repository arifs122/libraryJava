package model;

import interfaces.NotBorrowable;
//ansiklopedi ödünç alınamayacağı için notborrowable implement ediyor
public class Encyclopedia extends Book implements NotBorrowable {

    public Encyclopedia(String bookType, String bookName, String bookAuthor, int bookYear, boolean isBorrowed, Integer borrowerId) {
        super(bookType, bookName, bookAuthor, bookYear, isBorrowed, borrowerId);
    }
}

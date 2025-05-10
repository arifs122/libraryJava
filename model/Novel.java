package model;

public class Novel extends Book {

    public Novel(String bookType, String bookName, String bookAuthor, int bookYear, boolean isBorrowed, Integer borrowerId) {
        super(bookType, bookName, bookAuthor, bookYear, isBorrowed, borrowerId);
    }

}

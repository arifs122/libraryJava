package model;

//bookdan kalıtımla oluşturulan poetrybook class'ı

public class PoetryBook extends Book {

    public PoetryBook(String bookType, String bookName, String bookAuthor, int bookYear, boolean isBorrowed, Integer borrowerId) {
        super(bookType, bookName, bookAuthor, bookYear, isBorrowed, borrowerId);
    }
}

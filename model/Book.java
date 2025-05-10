package model;

public abstract class Book {
    private String bookType;
    private String bookName;
    private String bookAuthor;
    private int bookYear;
    private boolean isBorrowed;
    private Integer borrowerId;

    Book(String bookType, String bookName, String bookAuthor, int bookYear, boolean isBorrowed, Integer borrowerId) {
        setBookType(bookType);
        setBookName(bookName);
        setBookAuthor(bookAuthor);
        setBookYear(bookYear);
        setBorrowed(isBorrowed);
        setBorrowerId(borrowerId);
    }

    public String getBookType() {
        return bookType;
    }
    public boolean isBorrowed() {
        return isBorrowed;
    }
    public int getBookYear() {
        return bookYear;
    }
    public String getBookAuthor() {
        return bookAuthor;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookType(String bookType) {
        this.bookType = bookType;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }
    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
    public Integer getBorrowerId() { return borrowerId; }
    public void setBorrowerId(Integer borrowerId) { this.borrowerId = borrowerId; }
}

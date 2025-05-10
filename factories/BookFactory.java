package factories;
import model.*;
public class BookFactory {
    public static Book create(String bookType, String bookName, String bookAuthor, int bookYear, Integer borrowerId) {
        boolean isBorrowed = false;

        switch (bookType.toLowerCase()) {
            case "novel":
                return new Novel(bookType, bookName, bookAuthor, bookYear, isBorrowed, borrowerId);
            case "encyclopedia":
                return new Encyclopedia(bookType, bookName, bookAuthor, bookYear, isBorrowed, borrowerId);
            case "poetry":
                return new PoetryBook(bookType, bookName, bookAuthor, bookYear, isBorrowed, borrowerId);
            default:
                throw new IllegalArgumentException("Unknown book type: " + bookType);
        }
    }

}

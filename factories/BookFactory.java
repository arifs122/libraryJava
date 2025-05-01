package factories;
import model.*;
public class BookFactory {
    public static Book create(String bookType, String bookName, String bookAuthor, int bookYear) {
        boolean isBorrowed = false;

        switch (bookType.toLowerCase()) {
            case "novel":
                return new Novel(bookType, bookName, bookAuthor, bookYear, isBorrowed);
            case "encyclopedia":
                return new Encyclopedia(bookType, bookName, bookAuthor, bookYear, isBorrowed);
            case "poetry":
                return new PoetryBook(bookType, bookName, bookAuthor, bookYear, isBorrowed);
            default:
                throw new IllegalArgumentException("Unknown book type: " + bookType);
        }
    }

}

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Represents a library member and implements Serializable to allow saving/loading
public class Member implements Serializable {
    private String name;                // Member's full name
    private String memberId;            // Unique identifier for the member
    private List<Book> borrowedBooks;   // List of books currently borrowed by the member

    // Constructor to initialize a new member with a name and unique ID
    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>();  // Start with no books borrowed
    }

    public String getName() { return name; }  // Getter for the member's name

    public String getMemberId() { return memberId; }  // Getter for the member's ID

    public List<Book> getBorrowedBooks() { return borrowedBooks; }  // Returns list of borrowed books

    // Adds a book to the member's list of borrowed books
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Removes a book from the member's list of borrowed books
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    // Returns a string representation of the member, showing name and ID
    @Override
    public String toString() {
        return name + " (ID: " + memberId + ")";
    }
}

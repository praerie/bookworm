import java.io.Serializable;

// Represents a book in the library and implements Serializable to allow saving/loading
public class Book implements Serializable {
    private String title;     // Title of the book
    private String author;    // Author of the book
    private String isbn;      // Unique ISBN-13 identifier
    private boolean isLoaned; // Tracks loan status: true if currently loaned out

    // Constructor to initialize a new book with title, author, and ISBN
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isLoaned = false; // New books start out as available
    }

    public String getTitle() { return title; }  // Getter for the book's title
    
    public String getAuthor() { return author; }  // Getter for the book's author

    public String getIsbn() { return isbn; }  // Getter for the book's ISBN

    public boolean isLoaned() { return isLoaned; }  // Returns true if the book is currently loaned out

    // Marks the book as loaned out and throws exception if already loaned
    public void loanOut() throws Exception {
        if (isLoaned) throw new Exception("Book is already loaned out.");
        this.isLoaned = true;
    }

    // Marks the book as returned and throws exception if it wasn't loaned
    public void returnBook() throws Exception {
        if (!isLoaned) throw new Exception("Book is not currently loaned.");
        this.isLoaned = false;
    }

    // Returns a string representation of the book, showing loan status
    @Override
    public String toString() {
        return String.format("%s by %s (ISBN-13: %s) - %s", title, author, isbn, isLoaned ? "Loaned" : "Available");
    }
}

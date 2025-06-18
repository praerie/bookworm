import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String isbn;
    private boolean isLoaned;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isLoaned = false;
    }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public String getIsbn() { return isbn; }

    public boolean isLoaned() { return isLoaned; }

    public void loanOut() throws Exception {
        if (isLoaned) throw new Exception("Book is already loaned out.");
        this.isLoaned = true;
    }

    public void returnBook() throws Exception {
        if (!isLoaned) throw new Exception("Book is not currently loaned.");
        this.isLoaned = false;
    }

    @Override
    public String toString() {
        return String.format("%s by %s (ISBN: %s) - %s", title, author, isbn, isLoaned ? "Loaned" : "Available");
    }
}

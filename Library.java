import java.io.*;
import java.util.*;

public class Library implements Serializable {
    private Map<String, Book> books;        // Map of ISBN to Book object
    private Map<String, Member> members;    // Map of member ID to Member object

    // Constructor initializes empty book and member collections
    public Library() {
        books = new HashMap<>();
        members = new HashMap<>();
    }

    // Adds a new book to the library using its ISBN-13 num as the key
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    // Retrieves a book by its ISBN-13
    public Book searchByIsbn(String isbn) {
        return books.get(isbn);
    }

    // Returns a list of books with titles matching given string (case-insensitive)
    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().equalsIgnoreCase(title)) results.add(b);
        }
        return results;
    }

    // Returns a list of books with authors matching given string (case-insensitive)
    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getAuthor().equalsIgnoreCase(author)) results.add(b);
        }
        return results;
    }

    // Loans a book to a member by ISBN and member ID
    public void loanBook(String isbn, String memberId) throws Exception {
        Book book = books.get(isbn);               // Find book by ISBN-13
        Member member = members.get(memberId);     // Find member by ID

        if (book == null) throw new Exception("Book not found.");
        if (member == null) throw new Exception("Member not found.");

        book.loanOut();            // Mark the book as loaned
        member.borrowBook(book);   // Add the book to the member's borrowed list
    }

    // Returns a book from a member
    public void returnBook(String isbn, String memberId) throws Exception {
        Book book = books.get(isbn);               // Find book by ISBN-13
        Member member = members.get(memberId);     // Find member by ID

        if (book == null || member == null)
            throw new Exception("Invalid book or member.");

        book.returnBook();         // Mark the book as returned
        member.returnBook(book);   // Remove the book from the member's borrowed list
    }

    // Finds the member who currently has a specific book loaned
    public Member getBorrowerOf(Book book) {
        for (Member m : members.values()) {
            if (m.getBorrowedBooks().contains(book)) {
                return m;  // Return the member if they have the book
            }
        }
        return null;  // Book is not currently borrowed by any member
    }

    // Lists all currently loaned books and shows who borrowed each one
    public void listLoanedBooks() {
        boolean found = false;  // Tracks if any loaned books exist

        for (Book b : books.values()) {
            if (b.isLoaned()) {
                Member borrower = getBorrowerOf(b);  // Find who has the book
                if (borrower != null) {
                    System.out.printf("%s [%s (ID: %s)]\n", b, borrower.getName(), borrower.getMemberId());
                } else {
                    System.out.println(b + " [Borrower unknown]");
                }
                found = true;
            }
        }

        if (!found) {
            System.out.println("There are currently no books on loan.");
        }
    }

    // Lists all books that are not currently loaned out
    public void listAvailableBooks() {
        boolean found = false;  // Tracks if any available books exist

        for (Book b : books.values()) {
            if (!b.isLoaned()) {
                System.out.println(b);
                found = true;
            }
        }

        if (!found) {
            System.out.println("There are currently no books available.");
        }
    }

    // Lists the entire catalogue, showing status and borrower if applicable
    public void listAllBooks() {
        if (books.isEmpty()) {
            System.out.println("The catalogue is empty.");
        } else {
            for (Book b : books.values()) {
                if (b.isLoaned()) {
                    Member borrower = getBorrowerOf(b);
                    if (borrower != null) {
                        System.out.printf("%s [%s (ID: %s)]\n", b, borrower.getName(), borrower.getMemberId());
                    } else {
                        System.out.println(b + " [Borrower unknown]");
                    }
                } else {
                    System.out.println(b);
                }
            }
        }
    }

    // Registers a new library member
    public void registerMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    // Retrieves a member by their ID
    public Member getMemberById(String memberId) {
        return members.get(memberId);
    }

    // Returns a list of all registered members
    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    // Searches for members by name (case-insensitive)
    public List<Member> searchMembersByName(String name) {
        List<Member> matches = new ArrayList<>();
        for (Member m : members.values()) {
            if (m.getName().equalsIgnoreCase(name)) {
                matches.add(m);
            }
        }
        return matches;
    }

    // Saves the current state of the library to a file
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    // Loads a saved library from a file
    public static Library loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Library) in.readObject();
        }
    }
}

import java.io.*;
import java.util.*;

public class Library implements Serializable {
    private Map<String, Book> books;        // ISBN > Book
    private Map<String, Member> members;    // ID > Member

    public Library() {
        books = new HashMap<>();
        members = new HashMap<>();
    }

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public Book searchByIsbn(String isbn) {
        return books.get(isbn);
    }

    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().equalsIgnoreCase(title)) results.add(b);
        }
        return results;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getAuthor().equalsIgnoreCase(author)) results.add(b);
        }
        return results;
    }

    public void loanBook(String isbn, String memberId) throws Exception {
        Book book = books.get(isbn);
        Member member = members.get(memberId);

        if (book == null) throw new Exception("Book not found.");
        if (member == null) throw new Exception("Member not found.");

        book.loanOut();
        member.borrowBook(book);
    }

    public void returnBook(String isbn, String memberId) throws Exception {
        Book book = books.get(isbn);
        Member member = members.get(memberId);

        if (book == null || member == null) throw new Exception("Invalid book or member.");

        book.returnBook();
        member.returnBook(book);
    }

    public void listLoanedBooks() {
        // Flag to track if any books are currently loaned out
        boolean found = false;

        // Loop through all books in the collection
        for (Book b : books.values()) {
            if (b.isLoaned()) {
                System.out.println(b); // Print the loaned book
                found = true; // Mark that we found at least one loaned book
            }
        }
        
        // If no loaned books were found, print a message
        if (!found) {
            System.out.println("There are currently no books loaned out.");
        } 
    }

    public void listAvailableBooks() {
        // Flag to track if we find at least one available book
        boolean found = false;

        // Loop through all books in the collection
        for (Book b : books.values()) {
            // If the book is not currently loaned out, it's available
            if (!b.isLoaned()) {
                System.out.println(b); // Print the available book
                found = true; // Mark that we found at least one available book
            }
        }

        // If no available books were found, print a message
        if (!found) {
            System.out.println("There are currently no books available.");
        }
    }

    public void listAllBooks() {
        // Check if library collection is empty
        if (books.isEmpty()) {
            System.out.println("The catalogue is empty.");
        } else {
            // If the library collection is not empty, print each book's info
            for (Book b : books.values()) {
                System.out.println(b);
            }
        }
    }

    public void registerMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public Member getMemberById(String memberId) {
        return members.get(memberId);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    public List<Member> searchMembersByName(String name) {
        List<Member> matches = new ArrayList<>();
        for (Member m : members.values()) {
            if (m.getName().equalsIgnoreCase(name)) {
                matches.add(m);
            }
        }
        return matches;
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public static Library loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Library) in.readObject();
        }
    }
}

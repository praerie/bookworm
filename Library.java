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

    public void registerMember(Member member) {
        members.put(member.getMemberId(), member);
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
        for (Book b : books.values()) {
            if (b.isLoaned()) System.out.println(b);
        }
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

    public Member getMemberById(String memberId) {
        return members.get(memberId);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    public void listAvailableBooks() {
        for (Book b : books.values()) {
            if (!b.isLoaned()) System.out.println(b);
        }
    }

    public void listAllBooks() {
        for (Book b : books.values()) {
            System.out.println(b);
        }
    }

}

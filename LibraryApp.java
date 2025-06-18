import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library;

        try {
            library = Library.loadFromFile("library.dat");
            System.out.println("Library data loaded.");
        } catch (Exception e) {
            library = new Library();
            System.out.println("New library created.");
        }

        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Loan Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. List Loaned Books");
            System.out.println("7. Save & Exit");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Author: ");
                        String author = scanner.nextLine();
                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        library.addBook(new Book(title, author, isbn));
                        System.out.println("Book added.");
                        break;

                    case 2:
                        System.out.print("Member Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Member ID: ");
                        String memberId = scanner.nextLine();
                        library.registerMember(new Member(name, memberId));
                        System.out.println("Member registered.");
                        break;

                    case 3:
                        System.out.print("ISBN: ");
                        isbn = scanner.nextLine();
                        System.out.print("Member ID: ");
                        memberId = scanner.nextLine();
                        library.loanBook(isbn, memberId);
                        System.out.println("Book loaned.");
                        break;

                    case 4:
                        System.out.print("ISBN: ");
                        isbn = scanner.nextLine();
                        System.out.print("Member ID: ");
                        memberId = scanner.nextLine();
                        library.returnBook(isbn, memberId);
                        System.out.println("Book returned.");
                        break;

                    case 5:
                        System.out.print("Search by (title/author/isbn): ");
                        String method = scanner.nextLine().toLowerCase();
                        System.out.print("Enter query: ");
                        String query = scanner.nextLine();

                        switch (method) {
                            case "title":
                                library.searchByTitle(query).forEach(System.out::println);
                                break;
                            case "author":
                                library.searchByAuthor(query).forEach(System.out::println);
                                break;
                            case "isbn":
                                Book book = library.searchByIsbn(query);
                                System.out.println(book != null ? book : "Book not found.");
                                break;
                        }
                        break;

                    case 6:
                        library.listLoanedBooks();
                        break;

                    case 7:
                        library.saveToFile("library.dat");
                        System.out.println("Library saved. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

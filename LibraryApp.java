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
            System.out.println("2. Loan Book");
            System.out.println("3. Return Book");
            System.out.println("4. Search Book");
            System.out.println("5. List Loaned Books");
            System.out.println("6. List Available Books");
            System.out.println("7. List Full Catalogue");
            System.out.println("8. Register Member");
            System.out.println("9. Search Member");
            System.out.println("10. List Members");
            System.out.println("11. Save & Exit");
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
                        System.out.printf("Book added: \"%s\" by %s (ISBN: %s)\n", title, author, isbn);
                        break;

                    case 2:
                        System.out.print("ISBN: ");
                        isbn = scanner.nextLine();
                        System.out.print("Member ID: ");
                        String memberId = scanner.nextLine();
                        library.loanBook(isbn, memberId);
                        Book loanedBook = library.searchByIsbn(isbn);
                        Member borrower = library.getMemberById(memberId);
                        System.out.printf("\"%s\" by %s (ISBN: %s) has been loaned to %s (ID: %s)\n",
                                loanedBook.getTitle(), loanedBook.getAuthor(), loanedBook.getIsbn(),
                                borrower.getName(), borrower.getMemberId());
                        break;

                    case 3:
                        System.out.print("ISBN: ");
                        isbn = scanner.nextLine();
                        System.out.print("Member ID: ");
                        memberId = scanner.nextLine();
                        library.returnBook(isbn, memberId);
                        Book returnedBook = library.searchByIsbn(isbn);
                        Member returner = library.getMemberById(memberId);
                        System.out.printf("\"%s\" (ISBN: %s) has been returned by %s (ID: %s)\n",
                                returnedBook.getTitle(), returnedBook.getIsbn(),
                                returner.getName(), returner.getMemberId());
                        break;

                    case 4:
                        System.out.print("Search by (title/author/isbn): ");
                        String method = scanner.nextLine().toLowerCase();
                        System.out.print("Enter query: ");
                        String query = scanner.nextLine();
                        switch (method) {
                            case "title":
                                System.out.println("Results:");
                                library.searchByTitle(query).forEach(System.out::println);
                                break;
                            case "author":
                                System.out.println("Results:");
                                library.searchByAuthor(query).forEach(System.out::println);
                                break;
                            case "isbn":
                                Book book = library.searchByIsbn(query);
                                System.out.println(book != null ? book : "Book not found.");
                                break;
                            default:
                                System.out.println("Invalid search method.");
                        }
                        break;

                    case 5:
                        System.out.println("Books currently on loan:");
                        library.listLoanedBooks();
                        break;

                    case 6:
                        System.out.println("Available books:");
                        library.listAvailableBooks();
                        break;

                    case 7:
                        System.out.println("Full catalogue:");
                        library.listAllBooks();
                        break;

                    case 8:
                        System.out.print("Member Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Member ID: ");
                        memberId = scanner.nextLine();
                        library.registerMember(new Member(name, memberId));
                        System.out.printf("Member registered: %s (ID: %s)\n", name, memberId);
                        break;

                    case 9:
                        System.out.print("Enter Member ID: ");
                        String searchId = scanner.nextLine();
                        Member foundMember = library.getMemberById(searchId);
                        if (foundMember != null) {
                            System.out.println("Member found: " + foundMember);
                        } else {
                            System.out.println("Member not found.");
                        }
                        break;

                    case 10:
                        System.out.println("Registered Members:");
                        library.getAllMembers().forEach(System.out::println);
                        break;

                    case 11:
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

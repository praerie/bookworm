import java.util.List;
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
            int choice = -1;
            while (true) {
                try {
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
                    String input = scanner.nextLine();
                    if (input.trim().isEmpty()) {
                        System.out.println("Exiting program.");
                        return;
                    }
                    choice = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 11, or press Enter to exit.");
                }
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Author: ");
                        String author = scanner.nextLine();
                        System.out.print("ISBN-13: ");
                        String isbn = scanner.nextLine();
                        library.addBook(new Book(title, author, isbn));
                        System.out.printf("Book added: \"%s\" by %s (ISBN-13: %s)\n", title, author, isbn);
                        break;

                    case 2:
                        System.out.print("ISBN-13: ");
                        isbn = scanner.nextLine();
                        System.out.print("Member ID: ");
                        String memberId = scanner.nextLine();
                        library.loanBook(isbn, memberId);
                        Book loanedBook = library.searchByIsbn(isbn);
                        Member borrower = library.getMemberById(memberId);
                        System.out.printf("\"%s\" by %s (ISBN-13: %s) has been loaned to %s (ID: %s)\n",
                                loanedBook.getTitle(), loanedBook.getAuthor(), loanedBook.getIsbn(),
                                borrower.getName(), borrower.getMemberId());
                        break;

                    case 3:
                        System.out.print("ISBN-13: ");
                        isbn = scanner.nextLine();
                        System.out.print("Member ID: ");
                        memberId = scanner.nextLine();
                        library.returnBook(isbn, memberId);
                        Book returnedBook = library.searchByIsbn(isbn);
                        Member returner = library.getMemberById(memberId);
                        System.out.printf("\"%s\" (ISBN-13: %s) has been returned by %s (ID: %s)\n",
                                returnedBook.getTitle(), returnedBook.getIsbn(),
                                returner.getName(), returner.getMemberId());
                        break;

                    case 4:
                        String method;
                        while (true) {
                            System.out.print("Search by (title/author/ISBN) or press Enter to cancel: ");
                            method = scanner.nextLine().trim().toLowerCase();
                            if (method.isEmpty()) {
                                System.out.println("Search cancelled.");
                                break;
                            }
                            if (method.equals("title") || method.equals("author") || method.equals("isbn")) {
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
                                }
                                break;
                            } else {
                                System.out.println("Invalid method. Please enter 'title', 'author', or 'ISBN'.");
                            }
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
                        String memberSearchMethod;
                        while (true) {
                            System.out.print("Search by (name/ID) or press Enter to cancel: ");
                            memberSearchMethod = scanner.nextLine().trim().toLowerCase();
                            if (memberSearchMethod.isEmpty()) {
                                System.out.println("Search cancelled.");
                                break;
                            }
                            if (memberSearchMethod.equals("name") || memberSearchMethod.equals("id")) {
                                System.out.print("Enter query: ");
                                String memberQuery = scanner.nextLine();
                                if (memberSearchMethod.equals("id")) {
                                    Member foundById = library.getMemberById(memberQuery);
                                    System.out.println(foundById != null ? "Member found: " + foundById : "Member not found.");
                                } else {
                                    List<Member> results = library.searchMembersByName(memberQuery);
                                    if (!results.isEmpty()) {
                                        System.out.println("Matching members:");
                                        results.forEach(System.out::println);
                                    } else {
                                        System.out.println("No member found with that name.");
                                    }
                                }
                                break;
                            } else {
                                System.out.println("Invalid method. Please enter 'name' or 'ID'.");
                            }
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

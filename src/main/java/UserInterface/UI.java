package UserInterface;
import logic.Book;
import logic.Library;

import java.util.ArrayList;
import java.util.Scanner;


public class UI {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        System.out.println("Welcome to the Library software!");
        while (true) {
            int choice;

            // START: Validation Loop using do-while
            do {
                System.out.println("1. Add Book");
                System.out.println("2. Borrow Book");
                System.out.println("3. Return Book");
                System.out.println("4. Exit");
                System.out.println("Please enter your choice (1-4): ");

                // Input reading/conversion with error handling
                try {
                    // Using nextLine() and parsing handles the input cleanly
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    // Catches non-integer input (like "a", "hello")
                    System.out.println("Invalid input. Please enter a number (1-4).");
                    choice = -1; // initialize to ensure the loop continues
                    continue;
                }
                // --- Range Check ---
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice: " + choice + ". Please select a number between 1 and 4.");
                }
            } while (choice < 1 || choice > 4);
            // END: Validation Loop


            // --- Main Logic Switch ---
            switch (choice) {
                case 1: // ADD BOOK
                    System.out.println("Author?");
                    String authorToAdd = scanner.nextLine();
                    System.out.println("Title?");
                    String titleToAdd = scanner.nextLine();
                    System.out.println("Quantity? (Enter 0 to cancel)");
                    int quantityToAdd = validQuantityInput(scanner);
                    if (quantityToAdd == 0) {
                        System.out.println("Action cancelled.");
                        break;
                    }
                    try {
                        library.addBook(titleToAdd, authorToAdd, quantityToAdd);
                        System.out.println("Book added/updated successfully!");
                        System.out.println();
                        break;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2: // BORROW BOOK
                    System.out.println("Title to borrow?");
                    String titleToBorrow = scanner.nextLine();
                    if (library.findBook(titleToBorrow).isEmpty()) {
                        // Book not found.
                        System.out.println("Title not found.");
                        break;
                    }

                    if (library.findBook(titleToBorrow).size() == 1) {
                        // One book.
                        System.out.println("Quantity to borrow? (Enter 0 to cancel)");
                        Book bookToBorrow = library.findBook(titleToBorrow).getFirst();
                        while (true) {
                            int quantityToBorrow = validQuantityInput(scanner);
                            if (quantityToBorrow == 0) {
                                System.out.println("Transaction cancelled.");
                                break;
                            }
                            if (library.borrowBook(bookToBorrow, quantityToBorrow)) {
                                System.out.println("Book(s) borrowed successfully!");
                                break;
                            }
                        }
                    } else { // Multiple books of the same title.
                        ArrayList<Book> booksToBorrow = library.findBook(titleToBorrow);
                        System.out.println();
                        for (int i = 0; i < booksToBorrow.size(); i++) {
                            System.out.println((i + 1) + ". " + booksToBorrow.get(i).getTitle() + " by " +  booksToBorrow.get(i).getAuthor());
                        }
                        System.out.println("Which book exactly?");
                        int choiceOfBook = validQuantityInput(scanner);
                        while (choiceOfBook > booksToBorrow.size()) {
                           System.out.println("Invalid input.");
                           choiceOfBook = validQuantityInput(scanner);
                       }
                        while (true) {
                            System.out.println("Quantity? (Enter 0 to cancel)");
                            int quantityToBorrow = validQuantityInput(scanner);

                            if (quantityToBorrow == 0) {
                                System.out.println("Transaction cancelled.");
                                break;
                            }
                            if (library.borrowBook(booksToBorrow.get(choiceOfBook), quantityToBorrow)) {
                                System.out.println("Book(s) borrowed successfully!");
                                break;
                            }
                        }
                        System.out.println();
                        break;

                    }
                    break;

                case 3: // RETURN BOOK
                    System.out.println("Title to return?");
                    String titleToReturn = scanner.nextLine();

                    if (library.findBook(titleToReturn).isEmpty()) { // Book not found.

                        System.out.println("Title not found.");
                        break;
                    }

                    if (library.findBook(titleToReturn).size() == 1) { // One book.

                        System.out.println("Quantity to return? (Enter 0 to cancel)");
                        Book bookToReturn = library.findBook(titleToReturn).getFirst();
                        while (true) {
                            System.out.println("Quantity? (Enter 0 to cancel)");
                            int quantityToBorrow = validQuantityInput(scanner);

                            if (quantityToBorrow == 0) {
                                System.out.println("Transaction cancelled.");
                                break;
                            }
                            if (library.borrowBook(bookToReturn, quantityToBorrow)) {
                                System.out.println("Book(s) returned successfully!");
                                break;
                            }
                        }

                    } else { // Multiple books of the same title.
                        ArrayList<Book> booksToReturn = library.findBook(titleToReturn);
                        System.out.println();
                        for (int i = 0; i < booksToReturn.size(); i++) {
                            System.out.println((i + 1) + ". " + booksToReturn.get(i).getTitle() + " by " +  booksToReturn.get(i).getAuthor());
                        }
                        System.out.println("Which book exactly?");
                        int choiceOfBook = validQuantityInput(scanner);
                        while (choiceOfBook > booksToReturn.size()) {
                            System.out.println("Invalid input.");
                            choiceOfBook = validQuantityInput(scanner);
                        }
                        while (true) {
                            System.out.println("Quantity? (Enter 0 to cancel)");
                            int quantityToReturn = validQuantityInput(scanner);

                            if (quantityToReturn == 0) {
                                System.out.println("Transaction cancelled.");
                                break;
                            }
                            if (library.returnBook(booksToReturn.get(choiceOfBook), quantityToReturn)) {
                                System.out.println("Book(s) returned successfully!");
                                break;
                            }
                        }

                    }


                    System.out.println("Quantity to return? (Enter 0 to cancel)");
                    while (true) {
                        int quantityToReturn = validQuantityInput(scanner);

                        if (quantityToReturn == 0) {
                            System.out.println("Transaction cancelled.");
                            break;
                        }
                        System.out.println("Try a different amount or enter 0 to cancel:");
                    }
                    break;

                case 4: // EXIT
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
            }
        }
    }



    public static int validQuantityInput(Scanner scanner){
        while (true){
            try {
                int quantityToAdd = Integer.parseInt(scanner.nextLine());
                if (quantityToAdd < 0 ){
                    System.out.println("Invalid input. Please enter a number greater than 0.");
                    continue;
                }
                return quantityToAdd;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

}

package com.LongerDude.LMS;

import com.LongerDude.LMS.model.Book;
import com.LongerDude.LMS.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Scanner;

@ShellComponent
public class LMShell {



    private static BookRepository bookRepository;
    public LMShell(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ShellMethod(key = "start")
    public static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Library software!");
        // Main application loop
        while (true) {
            int choice;

            // START: Menu Validation Loop
            do {
                System.out.println("\n--- Main Menu ---");
                System.out.println("1. Add Book");
                System.out.println("2. Borrow Book");
                System.out.println("3. Return Book");
                System.out.println("4. Exit");
                System.out.print("Please enter your choice (1-4): ");

                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number (1-4).");
                    choice = -1; // Ensure loop continues
                    continue;
                }
                // --- Range Check ---
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice: " + choice + ". Please select a number between 1 and 4.");
                }
            } while (choice < 1 || choice > 4);
            // END: Menu Validation Loop


            // --- Main Logic Switch ---
            switch (choice) {
                case 1: // ADD BOOK
                    handleAddBook(scanner);
                    break;

                case 2: // BORROW BOOK
                    borrowBook(scanner);
                    break;

                case 3: // RETURN BOOK
                    borrowBook(scanner);
                    break;

                case 4: // EXIT
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
            }
        }
    }


    // -------------------------------------------------------------------------
    // PRIMARY HANDLER METHODS
    // -------------------------------------------------------------------------

    private static void printBooks() {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + " " + book);
        }
    }

    /**
     * Handles the entire "Add Book" workflow, including input and logic call.
     */
    private static void handleAddBook(Scanner scanner) {
        System.out.print("Author? ");
        String authorToAdd = scanner.nextLine();
        System.out.print("Title? ");
        String titleToAdd = scanner.nextLine();
        System.out.print("Quantity? (Enter 0 to cancel): ");

        int quantityToAdd = validQuantityInput(scanner);
        if (quantityToAdd == 0) {
            System.out.println("Action cancelled.");
            return;
        }

        try {
            bookRepository.save(new Book(titleToAdd, authorToAdd, quantityToAdd));
            System.out.println("Book added/updated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Handles the common logic for both Borrow and Return workflows.
     * Uses a functional interface to delegate the specific library action.
     */
    private static void borrowBook(Scanner scanner) {
        printBooks();
        System.out.println("Which book would you like to borrow?");
        long id = validQuantityInputLong(scanner);
        System.out.println("How many would you like to borrow?");
        int amount = validQuantityInput(scanner);
        bookRepository.findById(id).map(existingBook -> {
            Book updatedBook = new Book(id, existingBook.getTitle(), existingBook.getAuthor(), amount);
            return bookRepository.save(updatedBook);
        });
    }
    public static int validQuantityInput(Scanner scanner) {
        while (true) {
            try {
                // We use nextLine() outside of this method and here, for clean, consistent input handling.
                int quantity = Integer.parseInt(scanner.nextLine());

                // Quantity must be non-negative. Zero is allowed for cancellation.
                if (quantity < 0) {
                    System.out.println("Invalid input. Please enter a number greater than or equal to 0.");
                    continue;
                }
                return quantity;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }

    public static long validQuantityInputLong(Scanner scanner) {
        while (true) {
            try {
                // Use Long.parseLong() to handle inputs up to 9,223,372,036,854,775,807
                long quantity = Long.parseLong(scanner.nextLine());

                // Quantity must be non-negative. Zero is allowed for cancellation.
                if (quantity < 0) {
                    System.out.println("Invalid input. Please enter a number greater than or equal to 0.");
                    continue; // Loop again for new input
                }
                return quantity; // Valid input found, exit the loop and method
            } catch (NumberFormatException e) {
                // Catches input that cannot be parsed as a long integer (e.g., "abc")
                System.out.print("Invalid input. Please enter a whole number: ");
            }
        }
    }


}

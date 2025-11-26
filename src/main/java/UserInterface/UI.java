package UserInterface;
import logic.Library;

import java.util.Scanner;


public class UI {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        System.out.println("Welcome to the Library software!");
        while (true) {
            int choice = -1; // Initialize choice to an invalid value

            // START: Validation Loop using do-while
            do {
                System.out.println("1. Add Book");
                System.out.println("2. Borrow Book");
                System.out.println("3. Return Book");
                System.out.println("4. Exit");
                System.out.println("Please enter your choice (1-4): ");

                // --- Input reading/conversion with error handling ---
                try {
                    // Using nextLine() and parsing handles the input cleanly
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    // Catches non-integer input (like "a", "hello")
                    System.out.println("Invalid input. Please enter a number (1-4).");
                    choice = -1; // Reset to ensure the loop continues
                    continue;    // Skip to the next iteration of the do-while loop
                }

                // --- Range Check ---
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice: " + choice + ". Please select a number between 1 and 4.");
                }

            } while (choice < 1 || choice > 4); // Loop continues if the choice is outside 1-4
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
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2: // BORROW BOOK
                    System.out.println("Title to borrow?");
                    String titleToBorrow = scanner.nextLine();
                    if (library.findBook(titleToBorrow) == null) break;

                    System.out.println("Quantity to borrow? (Enter 0 to cancel)");
                    while (true) {
                        int quantityToBorrow = validQuantityInput(scanner);

                        if (quantityToBorrow == 0) {
                            System.out.println("Transaction cancelled.");
                            break;
                        }

                        if (library.borrowBook(titleToBorrow, quantityToBorrow)) {
                            System.out.println("Book(s) borrowed successfully!");
                            System.out.println();
                            break; // Success exit
                        }
                        // Failure implies not enough books (error printed by Library/Book)
                        System.out.println("Try a different amount or enter 0 to cancel:");
                    }
                    break;

                case 3: // RETURN BOOK
                    System.out.println("Title to return?");
                    String titleToReturn = scanner.nextLine();
                    if (library.findBook(titleToReturn) == null) break;

                    System.out.println("Quantity to return? (Enter 0 to cancel)");
                    while (true) {
                        int quantityToReturn = validQuantityInput(scanner);

                        if (quantityToReturn == 0) {
                            System.out.println("Transaction cancelled.");
                            break;
                        }

                        if (library.returnBook(titleToReturn, quantityToReturn)){
                            System.out.println("Book(s) returned successfully!");
                            System.out.println();
                            break; // Success exit
                        }
                        // Failure implies returning more than borrowed
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
                int quantityToAdd = Integer.valueOf(scanner.nextLine());
                return quantityToAdd;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

}

package logic;

import java.util.Objects;

/**
 * Represents a single book title/author combination in the library system.
 * This class is responsible for tracking the total stock available and the number
 * currently borrowed, and for enforcing borrowing and returning rules.
 */
public class Book {
    private String title;
    private String author;

    /** The total number of copies of this specific title/author owned by the library. */
    private int quantity;

    /** The number of copies currently borrowed by patrons. */
    private int borrowed;

    /**
     * Constructs a new Book object.
     * * @param title The title of the book.
     * @param author The author of the book.
     * @param quantity The initial total number of copies.
     * @throws IllegalArgumentException if the initial quantity is not positive (must start with stock).
     */
    public Book(String title, String author, int quantity) throws IllegalArgumentException {
        // Defensive check to ensure a book is created with at least one copy.
        if (quantity <= 0) {
            throw new IllegalArgumentException("Initial quantity must be positive");
        }
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.borrowed = 0; // Newly created books start with 0 borrowed copies.
    }

    // Simple getters for read access to the book's properties.
    public String getTitle () {
        return title;
    }
    public String getAuthor () {
        return author;
    }
    public int getQuantity () {
        return quantity;
    }
    public int getBorrowed () {
        return borrowed;
    }

    /**
     * Increases the total quantity of this book in the library's stock (e.g., buying new copies).
     * * @param amount The number of copies to add. Must be positive.
     * @return true if the quantity was successfully added, false otherwise.
     */
    public boolean addQuantity (int amount){
        // Ensure the amount is positive before updating the stock.
        if (amount <= 0){
            System.out.println("Cannot add nothing or a negative amount.");
            return false;
        }
        this.quantity += amount;
        return true;
    }

    /**
     * Processes a book borrowing request.
     * Decrements the available copies and increments the borrowed count, subject to availability.
     * * @param amount The number of copies the patron wishes to borrow.
     * @return true if the borrowing was successful, false if amount is invalid or stock is insufficient.
     */
    public boolean borrow (int amount){
        if (amount <= 0){
            System.out.println("You cannot borrow nothing or a negative amount!");
            return false;
        }

        // Calculate the current available stock.
        int available = quantity - borrowed;

        // Critical check: Ensure the requested amount does not exceed the available stock.
        if (available < amount){
            System.out.println("The amount of available books is not enough.");
            System.out.println("Amount of available books is " + available);
            return false;
        }

        // Update the state if the transaction is valid.
        borrowed += amount;
        return true;
    }

    /**
     * Processes a book return.
     * Decrements the borrowed count, subject to validation that the amount returned is not excessive.
     * * @param amount The number of copies being returned.
     * @return true if the return was successful, false if amount is invalid or more is returned than borrowed.
     */
    public boolean returnBook(int amount){
        if (amount <= 0){
            System.out.println("You cannot return nothing or a negative amount!");
            return false;
        }

        // Critical check: Ensure the returned amount does not exceed the currently borrowed amount.
        if (borrowed < amount){
            System.out.println("You cannot return more than what was borrowed!");
            System.out.println("Borrowed books: " + borrowed);
            return false;
        }

        // Update the state if the transaction is valid.
        borrowed -= amount;
        return true;
    }

    /**
     * Defines equality based only on the book's title and author.
     * This is crucial for {@link logic.Library} to determine if a new book being added
     * is an existing entry (and should only update the quantity).
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        // Two books are equal if they have the same title and author (case-insensitive for author).
        return Objects.equals(title, book.title) && book.getAuthor().equalsIgnoreCase(author);
    }

    /**
     * Generates a hash code consistent with the equals method (using title and author).
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, author.toLowerCase());
    }
}
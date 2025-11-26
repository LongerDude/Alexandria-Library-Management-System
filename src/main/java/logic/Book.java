package logic;

public class Book {
    private String title;
    private String author;
    private int quantity;
    private int borrowed;

    public Book(String title, String author, int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Initial quantity must be positive");
        }
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.borrowed = 0;
    }
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

    //Add comments

    public boolean addQuantity (int amount){
        if (amount <= 0){
            System.out.println("Cannot add nothing or a negative amount.");
            return false;
        }
        this.quantity += amount;
        return true;
    }

    public boolean borrow (int amount){
        if (amount <= 0){
            System.out.println("You cannot borrow nothing or a negative amount!");
            return false;
        }
        int available = quantity - borrowed;
        if (available < amount){
            System.out.println("The amount of available books is not enough.");
            System.out.println("Amount of available books is " + available);
            return false;
        }
        borrowed += amount;
        return true;
    }
    public boolean returnBook(int amount){
        if (amount <= 0){
            System.out.println("You cannot return nothing or a negative amount!");
            return false;
        }

        if (borrowed < amount){
            System.out.println("You cannot return more than what was borrowed!");
            System.out.println("Borrowed books: " + borrowed);
            return false;
        }
        borrowed -= amount;
        return true;
    }
}

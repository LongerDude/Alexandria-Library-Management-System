package Logic;

public class Book {
    private String title;
    private String author;
    private int quantity;
    private int borrowed;

    public Book(String title, String author, int quantity){
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
            return false;
        }
        this.quantity += amount;
        return true;
    }

    public boolean borrow (int amount){
        if (amount <= 0){
            return false;
        }
        int available = quantity - borrowed;
        if (available < amount){
            return false;
        }
        borrowed += amount;
        return true;
    }
    public boolean returnBook(int amount){
        if (amount <= 0){
            return false;
        }
        if (borrowed < amount){
            return false;
        }
        borrowed -= amount;
        return true;
    }
}

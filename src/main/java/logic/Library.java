package logic;

import java.util.HashMap;
import java.util.Map;

public class Library {
    private Map<String, Book> library;
    public Library(){
        library = new HashMap<>();
    }
    public boolean addBook(String title, String author, int quantity){
        Book existing = library.putIfAbsent(title, new Book(title, author, quantity));
        if(existing != null){
            return existing.addQuantity(quantity);
        }
        return true;
    }

    public Book findBook(String title){
        Book existing = library.get(title);
        if (existing == null){
            System.out.println("Book not found");
            return existing;
        }
        return existing;
    }
    public boolean borrowBook(String title, int quantity){

        Book existing = library.get(title);

        if(existing != null){
            return existing.borrow(quantity);
        }
        return false;
    }
    public boolean returnBook(String title, int quantity){
        Book existing = library.get(title);
        if(existing != null){
            return existing.returnBook(quantity);
        }
        return false;
    }
}

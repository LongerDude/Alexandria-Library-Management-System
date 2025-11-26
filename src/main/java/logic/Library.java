package logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Library {

    private Map<String, ArrayList<Book>> books;

    public Library(){
        books = new HashMap<>();
    }

    public boolean addBook(String title, String author, int quantity){
        String normalizedTitle = title.toLowerCase();
        books.putIfAbsent(normalizedTitle, new ArrayList<>());
        ArrayList<Book> booksWithTheSameTitle = books.get(normalizedTitle);
        // Optional to check if it's present or not, thereby update or create.
        Optional<Book> matchedBook = booksWithTheSameTitle.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .findFirst();

        if (matchedBook.isPresent()){
            return matchedBook.get().addQuantity(quantity);
        } else {
            try {
                booksWithTheSameTitle.add(new Book(title, author, quantity));
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }

    // The crutch of the project.
    public ArrayList<Book> findBook(String title){

        ArrayList<Book> matches = books.get(title.toLowerCase());

        if (matches == null) {
            return new ArrayList<Book>();
        }
        return matches;
    }

    public boolean borrowBook(Book book, int quantity){
        return book.borrow(quantity);
    }

    public boolean returnBook(Book book, int quantity){
        return book.returnBook(quantity);
    }
}
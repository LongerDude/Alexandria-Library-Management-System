package LogicTests;

import logic.Book;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {
    @Test
    void bookTitleAndAuthorAreCorrect() {
        Book book = new Book("TheTestBook", "TheTester", 100);
        assertEquals ("TheTestBook", book.getTitle());
        assertEquals("TheTester", book.getAuthor());
    }
    @Test
    void bookAmountAreCorrect() {
        Book book = new Book("TheTestBook", "TheTester", 100);
        assertEquals(100, book.getQuantity());
    }
    @Test
    void bookRefusesNegativeQuantityInConstruction() {
        assertThrows(IllegalArgumentException.class, () -> new Book("TheTestBook", "TheTester", -1));
        assertThrows(IllegalArgumentException.class, () -> new Book("TheTestBook", "TheTester", 0));
    }
    @Test
    void bookRefusesNegativeQuantityAdd() {
        Book book = new Book("TheTestBook", "TheTester", 100);
        assertEquals(false, book.addQuantity(-1));
        assertEquals(false, book.addQuantity(0));

    }
    @Test
    void bookRefusesBorrowingMoreThanQuantity() {
        Book book = new Book("TheTestBook", "TheTester", 100);
        assertEquals(false, book.borrow(200));
    }
    @Test
    void bookRefusesBorrowingZeroQuantity() {
        Book book = new Book("TheTestBook", "TheTester", 100);
        assertEquals(false, book.borrow(0));
    }
    @Test
    void bookBorrowsCorrectAmount() {
        Book book = new Book("TheTestBook", "TheTester", 100);
        book.borrow(50);
        assertEquals(50, book.getBorrowed());
    }
}

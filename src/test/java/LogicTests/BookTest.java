package LogicTests;

import logic.Book;
import logic.Library;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {
    private Book book;
    @BeforeEach
    void setUp() {
        book = new Book("TheTestBook", "TheTester", 100);
    }
    @Test
    void bookTitleAndAuthorAreCorrect() {
        assertEquals ("TheTestBook", book.getTitle());
        assertEquals("TheTester", book.getAuthor());
    }
    @Test
    void bookAmountAreCorrect() {
        assertEquals(100, book.getQuantity());
    }
    @Test
    void bookRefusesNegativeQuantityInConstruction() {
        assertThrows(IllegalArgumentException.class, () -> new Book("TheTestBook", "TheTester", -1));
        assertThrows(IllegalArgumentException.class, () -> new Book("TheTestBook", "TheTester", 0));
    }
    @Test
    void bookRefusesNegativeQuantityAdd() {
        assertEquals(false, book.addQuantity(-1));
        assertEquals(false, book.addQuantity(0));

    }
    @Test
    void bookRefusesBorrowingMoreThanQuantity() {
        assertEquals(false, book.borrow(200));
    }
    @Test
    void bookRefusesBorrowingZeroQuantity() {
        assertEquals(false, book.borrow(0));
    }
    @Test
    void bookBorrowsCorrectAmount() {
        book.borrow(50);
        assertEquals(50, book.getBorrowed());
    }
}

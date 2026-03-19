package com.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LibraryTest {

    @Mock
    private LibraryModel bookModel;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private LibraryControl control;

    @Test
    public void testSearchBooksAuthor() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Intro OOP", "divi"));
        books.add(new Book(2, "Pengujian PL itu mudah", "putri"));

        // stub
        Mockito.when(bookModel.getAllBooks()).thenReturn(books);

        Assertions.assertEquals("divi", control.SearchBooksAuthor("Intro OOP"));

        // mock 
        Mockito.verify(bookModel, Mockito.times(1)).getAllBooks();
    }

    @Test
    public void testSearchBooksAuthor_withNever() {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Intro OOP", "divi"));
        
        // stub
        Mockito.when(bookModel.getAllBooks()).thenReturn(books);
        control.SearchBooksAuthor("Unknown Book");

        Mockito.verify(bookModel, Mockito.never()).saveSearchKeyword(Mockito.anyString());
    }

    @Test
    public void testCheckBookAvailability_controller() {
        // stub: menentukan perilaku dari dependency (LibraryModel)
        Mockito.when(bookModel.isBookAvailable(1)).thenReturn(true);

        Assertions.assertTrue(control.checkBookAvailability(1));

        // mock: verifikasi interaksi terjadi
        Mockito.verify(bookModel, Mockito.times(1)).isBookAvailable(1);
    }

    @Test
    public void testBorrowBookById_whenNotBorrowed_callsBorrow() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Intro OOP", "divi", false));

        // stub
        Mockito.when(bookModel.getAllBooks()).thenReturn(books);

        boolean borrowed = control.borrowBookById(1, "alice");
        Assertions.assertTrue(borrowed);

        // mock + verify
        Mockito.verify(bookModel, Mockito.times(1)).borrowBook("Intro OOP", "alice");
    }

    @Test
    public void testBorrowBookById_whenBorrowed_doesNotCallBorrow() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Intro OOP", "divi", true));

        // stub
        Mockito.when(bookModel.getAllBooks()).thenReturn(books);

        boolean borrowed = control.borrowBookById(1, "alice");
        Assertions.assertFalse(borrowed);

        // verify borrowBook tidak dipanggil
        Mockito.verify(bookModel, Mockito.never()).borrowBook(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(notificationService, Mockito.never()).sendNotification(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testBorrowBookById_inOrder_getAllBooks_borrow_sendNotification() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Intro OOP", "divi", false));

        // stub
        Mockito.when(bookModel.getAllBooks()).thenReturn(books);

        boolean borrowed = control.borrowBookById(1, "alice");
        Assertions.assertTrue(borrowed);

        InOrder inOrder = Mockito.inOrder(bookModel, notificationService);
        inOrder.verify(bookModel).getAllBooks();
        inOrder.verify(bookModel).borrowBook("Intro OOP", "alice");
        inOrder.verify(notificationService).sendNotification("alice", "Intro OOP");

        // optional: pastikan tidak ada interaksi tambahan
        inOrder.verifyNoMoreInteractions();
    }
}

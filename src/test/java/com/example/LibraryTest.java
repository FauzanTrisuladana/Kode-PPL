package com.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LibraryTest {

    @Mock
    private LibraryModel bookModel;

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

        Mockito.verify(bookModel, Mockito.times(1)).getAllBooks();
    }
}

package com.example;

import java.util.List;
import java.util.ArrayList;

public class LibraryModel {

    public List<Book> getAllBooks() {

        List<Book> allBooks = new ArrayList<>();
        // Search in database
        return allBooks;
    }

    public Book getBookById(int bookId) {
        for (Book book : getAllBooks()) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public boolean isBookAvailable(int bookId) {
        Book book = getBookById(bookId);
        return book != null && !book.isBorrowed();
    }

    public void borrowBook(String title, String username) {
        // Persist borrowing transaction (e.g., update DB)
    }

    public void returnBook(String title, String username) {
        // Persist return transaction (e.g., update DB)
    }

    void saveSearchKeyword(String keyword) {
        // Save to db
    }
}

package com.example;

import java.util.List;

public class LibraryControl {

    private final LibraryModel model;

    public LibraryControl(LibraryModel model) {
        this.model = model;
    }

    public String SearchBooksAuthor(String tittle) {
        List<Book> books = model.getAllBooks();
        String result = "";
        for (Book book : books) {
            if (book.getTitle().equals(tittle)) {
                model.saveSearchKeyword(tittle);
                result = book.getAuthor();
            }

            // else result = "Book not found";
        }
        return result;
    }
}

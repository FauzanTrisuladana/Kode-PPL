package com.example;

import java.util.List;

public class LibraryControl {

    private final LibraryModel model;
    private final NotificationService notificationService;

    public LibraryControl(LibraryModel model) {
        this(model, null);
    }

    public LibraryControl(LibraryModel model, NotificationService notificationService) {
        this.model = model;
        this.notificationService = notificationService;
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

    public boolean checkBookAvailability(int bookId) {
        return model.isBookAvailable(bookId);
    }

    public boolean borrowBookById(int bookId, String username) {
        List<Book> books = model.getAllBooks();
        Book bookToBorrow = null;
        for (Book book : books) {
            if (book.getId() == bookId) {
                bookToBorrow = book;
                break;
            }
        }

        if (bookToBorrow == null || bookToBorrow.isBorrowed()) {
            return false;
        }

        String title = bookToBorrow.getTitle();
        model.borrowBook(title, username);

        if (notificationService != null) {
            notificationService.sendNotification(username, title);
        }

        return true;
    }

    public boolean returnBookById(int bookId, String username) {
        List<Book> books = model.getAllBooks();
        Book bookToReturn = null;
        for (Book book : books) {
            if (book.getId() == bookId) {
                bookToReturn = book;
                break;
            }
        }

        if (bookToReturn == null || !bookToReturn.isBorrowed()) {
            return false;
        }

        String title = bookToReturn.getTitle();
        model.returnBook(title, username);

        if (notificationService != null) {
            notificationService.sendNotification(username, title);
        }

        return true;
    }
}

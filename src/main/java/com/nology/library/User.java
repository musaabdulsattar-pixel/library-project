package com.nology.library;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private boolean isAdmin;
    //  track of the specific books this user has checked out
    private List<Book> loanedBooks;


    public User(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.loanedBooks = new ArrayList<>(); // Starts empty!
    }


    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public List<Book> getLoanedBooks() {
        return loanedBooks;
    }


    public void borrowBook(Book book) {
        loanedBooks.add(book);
    }
}

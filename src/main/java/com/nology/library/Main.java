package com.nology.library;

import java.util.Scanner;



public class Main {
    public static void main(String[] args) {

        Library myLibrary = new Library();
        myLibrary.loadBooksFromCSV("src/main/resources/books_data.csv");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the  Library ");


        System.out.print("Please enter your username to log in: ");
        String username = scanner.nextLine();

        //  type "admin", get admin privileges.
        boolean isAdmin = username.equalsIgnoreCase("admin");
        User currentUser = new User(username, isAdmin);

        System.out.println("Welcome, " + currentUser.getUsername() + "! (Admin: " + currentUser.isAdmin() + ")");

        boolean isRunning = true;


        while (isRunning) {
            System.out.println("MAIN MENU ");
            System.out.println("1. View all available books");
            System.out.println("2. Loan a book");
            System.out.println("3. View my loaned books");
            System.out.println("4. Exit");
            if (currentUser.isAdmin()) {
                System.out.println("5. Run Admin Report");
            }
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println(" Available Books ");
                for (Book book : myLibrary.getCollection()) {

                    if (!book.isLoaned()) {
                        System.out.println(book.getBookNum() + ". " + book.getTitle() + " by " + book.getAuthor());
                    }
                }
            }
            else if (choice.equals("2")) {

                System.out.print("Enter the Number of the book you want to loan: ");
                try {
                    int bookNumToLoan = Integer.parseInt(scanner.nextLine());
                    boolean found = false;

                    for (Book book : myLibrary.getCollection()) {
                        if (book.getBookNum() == bookNumToLoan) {
                            found = true;
                            if (book.isLoaned()) {
                                System.out.println("Sorry, that book is already checked out.");
                            } else {

                                book.setLoaned(true);
                                book.setLoanCount(book.getLoanCount() + 1);


                                currentUser.borrowBook(book);
                                System.out.println("Success! You have loaned out: " + book.getTitle());
                            }
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Could not find a book with that number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
            else if (choice.equals("3")) {

                System.out.println(" Your Loaned Books ");
                if (currentUser.getLoanedBooks().isEmpty()) {
                    System.out.println("You don't have any books checked out right now.");
                } else {
                    for (Book book : currentUser.getLoanedBooks()) {
                        System.out.println("- " + book.getTitle());
                    }
                }
            } else if (choice.equals("5")) {
                if (currentUser.isAdmin()){
                    myLibrary.runAdminReport();
                } else {
                    System.out.println("Access denied");
                }

            } else if (choice.equals("4")) {
                System.out.println("Goodbye! Closing the library...");
                isRunning = false;
            }
            else {
                System.out.println("Invalid choice. Please choose from the menu.");
            }
        }
        scanner.close();
    }
}
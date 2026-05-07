package com.nology.library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Book> collection = new ArrayList<>();

    public void loadBooksFromCSV(String filePath) {
        String line;


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // This skips the first line (the headers: Number, Title, etc.)

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");


                if (data.length >= 6) {
                    int num = Integer.parseInt(data[0].trim());
                    String title = data[1].replace("\"", "").trim();
                    String author = data[2].replace("\"", "").trim();
                    String genre = data[3].replace("\"", "").trim();
                    String subGenre = data[4].replace("\"", "").trim();
                    String publisher = data[5].replace("\"", "").trim();


                    Book newBook = new Book(num, title, author, genre, subGenre, publisher);
                    collection.add(newBook);
                }
            }
            System.out.println("Success! Loaded " + collection.size() + " books into the library.");

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }


    public List<Book> getCollection() {
        return collection;
    }
    public void runAdminReport(){
        System.out.println("ADMIN REPORT");

        try(PrintWriter writer = new PrintWriter(new FileWriter("library_report.csv"))){
            writer.println("Title,Author,Currently out, total times loaned");

            boolean foundAny = false;

            for (Book book: collection){
                if (book.getLoanCount()>0){
                    foundAny = true;
                    System.out.println( book.getTitle() + "  Times Loaned: " + book.getLoanCount() + "  Currently Out: " + book.isLoaned());
                    writer.println(book.getTitle() + "," + book.getAuthor() + "," + book.isLoaned() + "," + book.getLoanCount());

                }
            }
            if (!foundAny) {
                System.out.println("No books have been loaned out yet.");
                writer.println("No data yet.");
            } else {
                System.out.println("\nSuccess! This report has also been saved to 'library_report.csv' in your project folder.");
            }

        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
        }
    }


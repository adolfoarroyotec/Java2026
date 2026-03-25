package com.tecmx.ut;

public class Book {

    private final String title;
    private final String author;
    private int availableCopies;

    public Book(String title, String author, int availableCopies) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author is required");
        }
        if (availableCopies < 0) {
            throw new IllegalArgumentException("Available copies cannot be negative");
        }

        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public boolean hasAvailableCopies() {
        return availableCopies > 0;
    }

    public void lendCopy() {
        if (!hasAvailableCopies()) {
            throw new IllegalStateException("No copies are available to lend");
        }
        availableCopies--;
    }
}

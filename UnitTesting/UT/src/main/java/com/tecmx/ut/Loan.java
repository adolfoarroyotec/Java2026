package com.tecmx.ut;

import java.time.LocalDate;

public class Loan {

    private final Book book;
    private final User user;
    private final LocalDate loanDate;
    private final LocalDate dueDate;

    public Loan(Book book, User user, LocalDate loanDate, LocalDate dueDate) {
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}

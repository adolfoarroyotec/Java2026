package com.tecmx.ut;

import java.time.LocalDate;

public class LoanService {

    private static final int MAX_LOAN_DAYS = 14;

    private final LoanRepository loanRepository;
    private final Notifier notifier;

    public LoanService(LoanRepository loanRepository, Notifier notifier) {
        this.loanRepository = loanRepository;
        this.notifier = notifier;
    }

    public Loan lendBook(Book book, User user, int loanDays) {
        validateBook(book);
        validateUser(user);
        validateLoanDays(loanDays);

        book.lendCopy();

        LocalDate today = LocalDate.now();
        Loan loan = new Loan(book, user, today, today.plusDays(loanDays));

        loanRepository.save(loan);
        notifier.send(
                user.getName(),
                "Loan registered: " + book.getTitle() + ". Due date: " + loan.getDueDate()
        );

        return loan;
    }

    private void validateBook(Book book) {
        if (book == null) {
            throw new LoanException("A valid book is required");
        }
        if (!book.hasAvailableCopies()) {
            throw new LoanException("No copies of the book are available");
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new LoanException("A valid user is required");
        }
        if (!user.isActive()) {
            throw new LoanException("The user is not active");
        }
        if (user.isSuspended()) {
            throw new LoanException("The user is suspended and cannot borrow books");
        }
    }

    private void validateLoanDays(int loanDays) {
        if (loanDays <= 0) {
            throw new LoanException("Loan days must be greater than zero");
        }
        if (loanDays > MAX_LOAN_DAYS) {
            throw new LoanException("Loan days cannot exceed " + MAX_LOAN_DAYS);
        }
    }
}

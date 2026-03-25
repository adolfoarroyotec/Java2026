package com.tecmx.ut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private Notifier notifier;

    @InjectMocks
    private LoanService service;

    @Test
    void lendBookShouldRegisterLoanAndReduceCopies() {
        Book book = new Book("The Little Prince", "Antoine de Saint-Exupery", 2);
        User user = new User("Luis", true, false);

        Loan loan = service.lendBook(book, user, 5);

        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());

        Loan savedLoan = loanCaptor.getValue();
        assertEquals("The Little Prince", loan.getBook().getTitle());
        assertEquals(1, book.getAvailableCopies());
        assertNotNull(savedLoan);
        assertEquals(loan.getBook(), savedLoan.getBook());
        assertEquals(loan.getUser(), savedLoan.getUser());
    }

    @Test
    void lendBookShouldFailWhenNoCopiesAreAvailable() {
        Book book = new Book("1984", "George Orwell", 0);
        User user = new User("Maria", true, false);

        LoanException error = assertThrows(LoanException.class, () -> service.lendBook(book, user, 4));

        assertEquals("No copies of the book are available", error.getMessage());
        verify(loanRepository, never()).save(any(Loan.class));
        verify(notifier, never()).send(any(), any());
    }

    @Test
    void lendBookShouldFailWhenUserIsSuspended() {
        Book book = new Book("Foundation", "Isaac Asimov", 3);
        User user = new User("Pedro", true, true);

        LoanException error = assertThrows(LoanException.class, () -> service.lendBook(book, user, 4));

        assertEquals("The user is suspended and cannot borrow books", error.getMessage());
        verify(loanRepository, never()).save(any(Loan.class));
        verify(notifier, never()).send(any(), any());
    }

    @Test
    void lendBookShouldSendNotification() {
        Book book = new Book("Atomic Habits", "James Clear", 1);
        User user = new User("Sofia", true, false);

        service.lendBook(book, user, 7);

        verify(notifier).send(eq("Sofia"), contains("Atomic Habits"));
    }

    @Test
    void lendBookShouldFailWhenLoanDaysExceedMaximum() {
        Book book = new Book("Learning Java", "Author X", 2);
        User user = new User("Carla", true, false);

        LoanException error = assertThrows(LoanException.class, () -> service.lendBook(book, user, 21));

        assertTrue(error.getMessage().contains("14"));
        verify(loanRepository, never()).save(any(Loan.class));
        verify(notifier, never()).send(any(), any());
    }
}

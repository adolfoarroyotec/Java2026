package com.tecmx.ut;

public class UnitTesting {

    public static void main(String[] args) {
        Book book = new Book("Clean Code", "Robert C. Martin", 3);
        User user = new User("Ana", true, false);

        LoanService loanService = new LoanService(
                new ConsoleLoanRepository(),
                new ConsoleNotifier()
        );

        Loan loan = loanService.lendBook(book, user, 7);

        System.out.println();
        System.out.println("Exercise summary:");
        System.out.println("Book loaned: " + loan.getBook().getTitle());
        System.out.println("User: " + loan.getUser().getName());
        System.out.println("Due date: " + loan.getDueDate());
        System.out.println("Remaining copies: " + book.getAvailableCopies());
        System.out.println();
        System.out.println("Run the tests with: mvn test");
    }
}
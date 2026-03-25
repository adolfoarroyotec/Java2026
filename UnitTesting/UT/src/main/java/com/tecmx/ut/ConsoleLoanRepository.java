package com.tecmx.ut;

public class ConsoleLoanRepository implements LoanRepository {

    @Override
    public void save(Loan loan) {
        System.out.println("Loan saved for " + loan.getUser().getName());
    }
}

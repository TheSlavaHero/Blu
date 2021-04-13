package com.blubank.entity.Transaction;

public class TransactionForm {
    private Transaction transaction;
    private int cvc;
    private String date;

    public TransactionForm(Transaction transaction, int cvc, String date) {
        this.transaction = transaction;
        this.cvc = cvc;
        this.date = date;
    }

    public TransactionForm() {
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

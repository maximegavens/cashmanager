package com.cashmanager.model;

public class FakeBank {
    private long id_bank;

    private String accountRef;

    private Double amount;

    public FakeBank(long id_bank, String accountRef, Double amount) {
        this.id_bank = id_bank;
        this.accountRef = accountRef;
        this.amount = amount;
    }

    public long getId_bank() {
        return id_bank;
    }

    public void setId_bank(long id_bank) {
        this.id_bank = id_bank;
    }

    public String getAccountRef() {
        return accountRef;
    }

    public void setAccountRef(String accountRef) {
        this.accountRef = accountRef;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

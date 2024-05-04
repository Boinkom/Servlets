package ru.productstar.servlets.model;

import java.util.ArrayList;

public class Transaction {
    Expense expense;
    Income income;


    public Expense getExpense() {
        return expense;
    }

    public Income getIncome() {
        return income;
    }

    public Transaction(Expense expense, Income income) {
        this.expense = expense;
        this.income = income;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public void setIncome(Income income) {
        this.income = income;
    }
}

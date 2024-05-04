package ru.productstar.servlets.model;

public class Income {
    private final String name;
    private final int sum;

    public Income(String name, int sum) {
        this.name = name;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", sum=" + sum +
                '}';
    }
}

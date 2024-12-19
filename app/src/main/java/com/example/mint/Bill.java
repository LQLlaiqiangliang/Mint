package com.example.mint;

public class Bill {
    private String name;   // 账单名称
    private double amount; // 账单金额
    private String date;   // 账单日期

    // 构造函数
    public Bill(String name, double amount, String date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    // Getter 和 Setter 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


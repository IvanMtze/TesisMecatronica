package com.app.app.DATA.Entitys;

public class Agua {
    private int id;
    private double agua;
    private String Date;

    public Agua(int id, double agua, String date) {
        this.id = id;
        this.agua = agua;
        Date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAgua() {
        return agua;
    }

    public void setAgua(double agua) {
        this.agua = agua;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}

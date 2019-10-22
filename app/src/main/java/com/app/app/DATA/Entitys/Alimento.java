package com.app.app.DATA.Entitys;

public class Alimento {
    private int id;
    private double alimento;
    private String date;

    public Alimento(int id, double alimento, String date) {
        this.id = id;
        this.alimento = alimento;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAlimento() {
        return alimento;
    }

    public void setAlimento(double alimento) {
        this.alimento = alimento;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

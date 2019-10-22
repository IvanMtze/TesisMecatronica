package com.app.app.DATA.Entitys;

import android.database.sqlite.SQLiteOpenHelper;

public class Peso {
    private int id;
    private double peso;
    private String date;

    public Peso(int id, double peso, String date) {
        this.id = id;
        this.peso = peso;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

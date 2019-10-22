package com.app.app.DATA;

public class Saver {
    public static Saver myInstance = null;
    private Saver (){}

    public Saver getInstance(){
        if(myInstance == null){
            myInstance = new Saver();
        }
        return myInstance;
    }

    public boolean save(){
        return false;
    }
}

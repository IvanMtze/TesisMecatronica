package com.app.app.DATA.BaseData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.widget.Toast;

import com.app.app.DATA.Contracts.AguaContract.AguaEntry;
import com.app.app.DATA.Contracts.PollosContract.PolloEntry;
import com.app.app.DATA.Contracts.PesoContract.PesoEntry;
import com.app.app.DATA.Contracts.AlimentoContract.AlimentoEntry;

import java.text.SimpleDateFormat;

import java.util.Date;

public class BDHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION  = 1;
    public static final String DATABASE_NAME = "SanAntonio.bd";
    private static BDHelper myInstance = null;
    Context context;

    public static BDHelper getInstance(Context context){
        if(myInstance == null)
            myInstance = new BDHelper(context);
        return myInstance;
    }

    public BDHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context = context;
    }

    public BDHelper(Context context, String dataBaseName, SQLiteDatabase.CursorFactory factory, int version){
        super(context,dataBaseName,factory,version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PolloEntry.TABLE_NAME + "("
                + PolloEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PolloEntry.POLLOS + " INTEGER, "
                + PolloEntry.DATE + " TEXT"
                + ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + AguaEntry.TABLE_NAME + "("
                + AguaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AguaEntry.AGUA+ " INTEGER, "
                + AguaEntry.DATE+ " TEXT"
                + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + AlimentoEntry.TABLE_NAME + "("
                + AlimentoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AlimentoEntry.ALIMENTO+ " INTEGER, "
                + AlimentoEntry.DATE+ " TEXT"
                + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PesoEntry.TABLE_NAME + "("
                + PesoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PesoEntry.PESO+ " INTEGER, "
                + PesoEntry.DATE+ " TEXT"
                + ")");
        this.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void eliminarTodo(){
        SQLiteDatabase baseData = getWritableDatabase();

        baseData.execSQL("DELETE FROM " + PesoEntry.TABLE_NAME);
        baseData.execSQL("DELETE FROM " + AlimentoEntry.TABLE_NAME);
        baseData.execSQL("DELETE FROM " + AguaEntry.TABLE_NAME);
        baseData.execSQL("DELETE FROM " + PolloEntry.TABLE_NAME);
    }
    public void llenar(){
        SQLiteDatabase baseData = getWritableDatabase();

        eliminarTodo();
        String ano = "2019";
        String mes = "09";
        int dia = 13;
        for (dia = 13; dia <= 31; dia++){
            ContentValues content = new ContentValues();
            content.put(PolloEntry.DATE,ano+"-"+mes+"-"+dia);
            content.put(PolloEntry.POLLOS, 2*(35-dia)*1.0);
            baseData.insert(PolloEntry.TABLE_NAME,null,content);
        }
    }

    public boolean existeActividad(String nombre){
        SQLiteDatabase baseData = getReadableDatabase();

        switch (nombre){
            case "agua":
                nombre = AguaEntry.TABLE_NAME;
                break;
            case "alimento":
                nombre = AlimentoEntry.TABLE_NAME;
                break;
            case "pollos":
                nombre = PolloEntry.TABLE_NAME;
                break;
            case "peso":
                nombre = PesoEntry.TABLE_NAME;
                break;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String diaFormato = dateFormat.format(new Date());
        Cursor c = baseData.rawQuery("select DATE from "+ nombre +" where DATE = \"" + diaFormato +"\"",null);
        if(c.getCount()<=0) {
            c.close();
            return false;
        }
        else{
            return true;
        }
    }
}

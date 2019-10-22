package com.app.app.DATA.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.app.app.DATA.BaseData.BDHelper;
import com.app.app.DATA.Contracts.PollosContract;
import com.app.app.DATA.SALIDA.ManagerConnection;
import com.app.app.DATA.Pair;
import com.app.app.DATA.SALIDA.POST_POLLOS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PollosDAO {
    BDHelper baseDataHelper;
    Context context;

    public PollosDAO(Context context) {
        this.context = context;
        baseDataHelper = new BDHelper(context);
    }

    public boolean insertarPollos(double pollos, Activity activity) {
        POST_POLLOS pollosEnvio = new POST_POLLOS(ManagerConnection.IP, context, activity);

        SQLiteDatabase baseData = baseDataHelper.getWritableDatabase();
        Cursor c = baseData.rawQuery("select "+ PollosContract.PolloEntry.POLLOS+ " from "+ PollosContract.PolloEntry.TABLE_NAME,null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String diaFormateado = dateFormat.format(new Date());
        try {
            pollosEnvio.makePost((int)pollos, diaFormateado);
            Toast.makeText(context,"Enviado", Toast.LENGTH_SHORT);

        }catch (IOException e){
            System.err.println(e);//Toast.makeText(context, "ERROR WHEN SENDING", Toast.LENGTH_SHORT).show();
        }
        if(!c.moveToLast()) {//No hay nada
            ContentValues contenedor = new ContentValues();
            contenedor.put(PollosContract.PolloEntry.DATE,diaFormateado);
            contenedor.put(PollosContract.PolloEntry.POLLOS,pollos);
            baseData.insert(PollosContract.PolloEntry.TABLE_NAME, null, contenedor);
            Toast t = Toast.makeText(context,"Guardado",Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            c.moveToLast();
            double polloTotal = c.getDouble(0);
            double polloInsertar = polloTotal - pollos;
            ContentValues contenedor = new ContentValues();
            contenedor.put(PollosContract.PolloEntry.DATE, diaFormateado);
            contenedor.put(PollosContract.PolloEntry.POLLOS, polloInsertar);
            baseData.insert(PollosContract.PolloEntry.TABLE_NAME, null, contenedor);
            Toast t = Toast.makeText(context, "Guardado", Toast.LENGTH_SHORT);
            t.show();
        }
        return true;
    }
    public ArrayList<Pair> getNumPollos(){
        SQLiteDatabase baseData = baseDataHelper.getReadableDatabase();

        ArrayList<Pair> pollos = new ArrayList<>();
        Cursor c =  baseData.rawQuery("select "+ PollosContract.PolloEntry.POLLOS +" , "+ PollosContract.PolloEntry.DATE+" from " + PollosContract.PolloEntry.TABLE_NAME,null);
        //Cursor c =  baseData.rawQuery("select pollo, date from " + tablePolloName,null);
        if(c != null && c.getCount()>0){
            c.moveToFirst();
            do{
                Pair<Float,String> pair = new Pair<>();
                pair.setLeft((float)c.getInt(0));
                pair.setRight(c.getString(1));
                pollos.add(pair);
            }while(c.moveToNext());
        }
        return pollos;
    }
}

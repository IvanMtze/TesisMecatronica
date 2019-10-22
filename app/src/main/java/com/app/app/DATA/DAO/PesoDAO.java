package com.app.app.DATA.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.app.app.DATA.BaseData.BDHelper;
import com.app.app.DATA.Contracts.PesoContract;
import com.app.app.DATA.SALIDA.ManagerConnection;
import com.app.app.DATA.Pair;
import com.app.app.DATA.SALIDA.POST_PESO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PesoDAO {
    BDHelper baseDataHelper;
    Context context;

    public PesoDAO(Context context) {
        baseDataHelper = new BDHelper(context);
        this.context = context;
    }

    public ArrayList<Pair> getPesoPromedio(){
        SQLiteDatabase baseData = baseDataHelper.getReadableDatabase();

        ArrayList<Pair> peso = new ArrayList<>();
        Pair<Float, String> pair = new Pair<>();
        Cursor c =  baseData.rawQuery("select " + PesoContract.PesoEntry.PESO + " , " + PesoContract.PesoEntry.DATE + " from " + PesoContract.PesoEntry.TABLE_NAME,null);
        //Cursor c =  baseData.rawQuery("select peso, date  from " + tablePesoName,null);
        if(c != null && c.getCount()>0){
            c.moveToFirst();
            do{
                Float f = (float) c.getDouble(0);
                pair.setLeft(f);
                pair.setRight(c.getString(1));
                peso.add(pair);
            }while(c.moveToNext());
        }
        return peso;
    }
    public boolean insertarPeso(double peso, Activity activity){
        POST_PESO pesoEnvio = new POST_PESO(ManagerConnection.IP, context, activity);

        SQLiteDatabase baseData = baseDataHelper.getWritableDatabase();

        Cursor c = baseData.rawQuery("select " + PesoContract.PesoEntry.PESO + " from "+ PesoContract.PesoEntry.TABLE_NAME,null);
        //Cursor c = baseData.rawQuery("select peso from "+ tablePesoName,null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String diaFormateado = dateFormat.format(new Date());
        try {
            pesoEnvio.makePost(peso, diaFormateado);
            Toast.makeText(context,"Enviado", Toast.LENGTH_SHORT);

        }catch (IOException e){
            Toast.makeText(context, "ERROR WHEN SENDING", Toast.LENGTH_SHORT).show();
        }
        if(!c.moveToLast()) {//No hay nada
            ContentValues contenedor = new ContentValues();
            contenedor.put(PesoContract.PesoEntry.DATE,diaFormateado);
            contenedor.put(PesoContract.PesoEntry.PESO,peso);
            baseData.insert(PesoContract.PesoEntry.TABLE_NAME, null, contenedor);
            Toast t = Toast.makeText(context,"Guardado sas",Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            c.moveToLast();
            double pesoTotal= c.getDouble(0);
            double pesoInsertar= pesoTotal - peso;
            ContentValues contenedor = new ContentValues();
            contenedor.put(PesoContract.PesoEntry.DATE, diaFormateado);
            contenedor.put(PesoContract.PesoEntry.PESO, pesoInsertar);
            baseData.insert(PesoContract.PesoEntry.TABLE_NAME, null, contenedor);
            Toast t = Toast.makeText(context, "Guardado", Toast.LENGTH_SHORT);
            t.show();
        }
        return true;
    }
}

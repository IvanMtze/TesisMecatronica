package com.app.app.DATA.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.app.app.DATA.BaseData.BDHelper;
import com.app.app.DATA.Contracts.AguaContract;
import com.app.app.DATA.SALIDA.ManagerConnection;
import com.app.app.DATA.Pair;
import com.app.app.DATA.SALIDA.POST_AGUA;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AguaDao {
    BDHelper baseDataHelper;
    Context context;

    public AguaDao(Context context) {
        this.context = context;
        baseDataHelper = new BDHelper(context);
    }

    public ArrayList<Pair> getAguaToda(){
        SQLiteDatabase baseData = baseDataHelper.getReadableDatabase();
        ArrayList<Pair> agua = new ArrayList<>();
        Pair<Float, String> pair = new Pair<>();
        Cursor c =  baseData.rawQuery("select " + AguaContract.AguaEntry.AGUA + " , " + AguaContract.AguaEntry.DATE + " from " + AguaContract.AguaEntry.TABLE_NAME ,null);
        if(c != null && c.getCount()>0){
            c.moveToFirst();
            do{
                Float f = (float) c.getDouble(0);
                pair.setLeft(f);
                pair.setRight(c.getString(1));
                agua.add(pair);
            }while(c.moveToNext());
        }
        return agua;
    }

    public boolean insertarAgua(double agua, Activity activity){
        POST_AGUA aguaEnvio = new POST_AGUA(ManagerConnection.IP, context, activity);

        SQLiteDatabase baseData = baseDataHelper.getWritableDatabase();

        Cursor c = baseData.rawQuery(" select " + AguaContract.AguaEntry.AGUA + " from "+ AguaContract.AguaEntry.TABLE_NAME,null);
        //Cursor c = baseData.rawQuery("select agua from "+ tableAguaName,null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String diaFormateado = dateFormat.format(new Date());
        try {
            aguaEnvio.makePost(agua, diaFormateado);
            Toast.makeText(context,"Enviado", Toast.LENGTH_SHORT);
        }catch (IOException e){
            Toast.makeText(context, "ERROR WHEN SENDING", Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }

        if(!c.moveToLast()) {//No hay nada
            ContentValues contenedor = new ContentValues();
            contenedor.put(AguaContract.AguaEntry.DATE,diaFormateado);
            contenedor.put(AguaContract.AguaEntry.AGUA,agua);
            baseData.insert(AguaContract.AguaEntry.TABLE_NAME, null, contenedor);
        }
        else{
            c.moveToLast();
            double aguaTotal = c.getDouble(0);
            double aguaInsertar = aguaTotal + agua;
            ContentValues contenedor = new ContentValues();
            contenedor.put(AguaContract.AguaEntry.DATE, diaFormateado);
            contenedor.put(AguaContract.AguaEntry.AGUA, aguaInsertar);
            baseData.insert(AguaContract.AguaEntry.TABLE_NAME, null, contenedor);
            Toast t = Toast.makeText(context, "Guardado", Toast.LENGTH_SHORT);
            t.show();
        }
        return false;
    }
}

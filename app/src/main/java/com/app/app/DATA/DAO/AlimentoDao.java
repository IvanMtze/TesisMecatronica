package com.app.app.DATA.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.app.app.DATA.BaseData.BDHelper;
import com.app.app.DATA.Contracts.AlimentoContract;
import com.app.app.DATA.SALIDA.ManagerConnection;
import com.app.app.DATA.Pair;
import com.app.app.DATA.SALIDA.POST_ALIMENTO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AlimentoDao {
    BDHelper baseDataHelper;
    Context context;

    public AlimentoDao(Context context) {
        baseDataHelper = new BDHelper(context);
        this.context = context;
    }

    public boolean insertarAlimento(double alimento, Activity activity){
        POST_ALIMENTO alimentoEnvio = new POST_ALIMENTO(ManagerConnection.IP, context, activity);

        SQLiteDatabase baseData = baseDataHelper.getWritableDatabase();

        Cursor c = baseData.rawQuery("select " + AlimentoContract.AlimentoEntry.ALIMENTO + " from "+ AlimentoContract.AlimentoEntry.TABLE_NAME,null);
        //Cursor c = baseData.rawQuery("select alimento from "+ tableAlimentoName,null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String diaFormateado = dateFormat.format(new Date());
        try {
            alimentoEnvio.makePost(alimento, diaFormateado);
            Toast.makeText(context,"Enviado", Toast.LENGTH_SHORT);

        }catch (IOException e){
            Toast.makeText(context, "ERROR WHEN SENDING", Toast.LENGTH_SHORT).show();
        }
        if(!c.moveToLast()) {//No hay nada
            ContentValues contenedor = new ContentValues();
            contenedor.put(AlimentoContract.AlimentoEntry.DATE,diaFormateado);
            contenedor.put(AlimentoContract.AlimentoEntry.ALIMENTO,alimento);
            baseData.insert(AlimentoContract.AlimentoEntry.TABLE_NAME, null, contenedor);
            Toast t = Toast.makeText(context,"Guardado sas",Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            c.moveToLast();
            double alimentoTotal = c.getDouble(0);
            double alimentoInsertar = alimentoTotal - alimento;
            ContentValues contenedor = new ContentValues();
            contenedor.put(AlimentoContract.AlimentoEntry.DATE, diaFormateado);
            contenedor.put(AlimentoContract.AlimentoEntry.ALIMENTO, alimentoInsertar);
            baseData.insert(AlimentoContract.AlimentoEntry.TABLE_NAME, null, contenedor);
            Toast t = Toast.makeText(context, "Guardado", Toast.LENGTH_SHORT);
            t.show();
        }
        return true;
    }
    //
    public ArrayList<Pair> getAlimento(){
        SQLiteDatabase baseData = baseDataHelper.getReadableDatabase();

        ArrayList<Pair> alimento = new ArrayList<>();
        Pair<Float,String> pair = new Pair<>();
        Cursor c =  baseData.rawQuery("select " + AlimentoContract.AlimentoEntry.ALIMENTO +" , " + AlimentoContract.AlimentoEntry.DATE+" from " + AlimentoContract.AlimentoEntry.TABLE_NAME,null);
        //Cursor c =  baseData.rawQuery("select alimento,date from " + tableAlimentoName,null);
        if(c != null && c.getCount()>0){
            c.moveToFirst();
            do{
                float f = (float) c.getDouble(0);
                pair.setLeft(f);
                pair.setRight(c.getString(1));
                alimento.add(pair);
            }while(c.moveToNext());
        }
        return alimento;
    }
}

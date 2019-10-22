package com.app.app.DATA.ContentProviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.app.DATA.BaseData.BDHelper;
import com.app.app.DATA.Contracts.AlimentoContract;

public class AlimentoProvider extends ContentProvider {
    private static final String URI = "content://com.sanAntonio.android.contentproviders/alimento";
    public static final Uri CONTENT_URI = Uri.parse(URI);


    private BDHelper baseData;
    private static final String BD_NOMBRE = BDHelper.DATABASE_NAME;
    private static final int BD_VERSION = BDHelper.DATABASE_VERSION;
    private static final String TABLA_ALIMENTO= AlimentoContract.AlimentoEntry.TABLE_NAME;

    private static final int ALIMENTO = 1;
    private static final int ALIMENTO_ID = 2;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URI,"alimento",ALIMENTO);
        uriMatcher.addURI(URI,"alimento/#",ALIMENTO_ID);
    }
    @Override
    public boolean onCreate() {
        baseData = new BDHelper(getContext(), BD_NOMBRE,null, BD_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String where = selection;
        if(uriMatcher.match(uri) == ALIMENTO_ID){
            where = "_id=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = baseData.getWritableDatabase();

        Cursor c = db.query(TABLA_ALIMENTO, projection,where,selectionArgs,null,null,sortOrder);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = uriMatcher.match(uri);
        switch (match){
            case ALIMENTO:
                return "vnd.android.cursor.dir/vnd.sanAntonio.alimento";
            case ALIMENTO_ID:
                return "vnd.android.cursor.item/vnd.sanAntonio.alimento";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long regId = 1;
        SQLiteDatabase db = baseData.getWritableDatabase();
        regId = db.insert(TABLA_ALIMENTO, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int cont;

        String where = selection;
        if(uriMatcher.match(uri) == ALIMENTO_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = baseData.getWritableDatabase();
        cont = db.delete(TABLA_ALIMENTO, where, selectionArgs);
        return cont;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int cont;

        String where = selection;
        if(uriMatcher.match(uri) == ALIMENTO_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = baseData.getWritableDatabase();
        cont = db.update(TABLA_ALIMENTO,values, where, selectionArgs);
        return cont;
    }

    public static final class ALIMENTO implements BaseColumns {
        private ALIMENTO(){}

        public static final String TABLE_NAME = AlimentoContract.AlimentoEntry.TABLE_NAME;

        public static final String ALIMENTO = AlimentoContract.AlimentoEntry.ALIMENTO;
        public static final String DATE = AlimentoContract.AlimentoEntry.DATE;
    }
}

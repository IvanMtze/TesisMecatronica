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
import com.app.app.DATA.Contracts.PollosContract;

public class PollosProvider extends ContentProvider {

    private static final String URI = "content://com.sanAntonio.android.contentproviders/pollos";
    public static final Uri CONTENT_URI = Uri.parse(URI);


    private BDHelper baseData;
    private static final String BD_NOMBRE = BDHelper.DATABASE_NAME;
    private static final int BD_VERSION = BDHelper.DATABASE_VERSION;
    private static final String TABLA_POLLOS = PollosContract.PolloEntry.TABLE_NAME;

    private static final int POLLOS = 1;
    private static final int POLLOS_ID = 2;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URI,"pollos",POLLOS);
        uriMatcher.addURI(URI,"pollos/#",POLLOS_ID);
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
        if(uriMatcher.match(uri) == POLLOS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = baseData.getWritableDatabase();

        Cursor c = db.query(TABLA_POLLOS, projection,where,selectionArgs,null,null,sortOrder);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = uriMatcher.match(uri);
        switch (match){
            case POLLOS:
                return "vnd.android.cursor.dir/vnd.sanAntonio.pollos";
            case POLLOS_ID:
                return "vnd.android.cursor.item/vnd.sanAntonio.pollos";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long regId = 1;
        SQLiteDatabase db = baseData.getWritableDatabase();
        regId = db.insert(TABLA_POLLOS, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int cont;

        String where = selection;
        if(uriMatcher.match(uri) == POLLOS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = baseData.getWritableDatabase();
        cont = db.delete(TABLA_POLLOS, where, selectionArgs);
        return cont;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int cont;

        String where = selection;
        if(uriMatcher.match(uri) == POLLOS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = baseData.getWritableDatabase();
        cont = db.update(TABLA_POLLOS,values, where, selectionArgs);
        return cont;
    }

    public static final class Pollos implements BaseColumns{
        private Pollos(){}

        public static final String TABLE_NAME = PollosContract.PolloEntry.TABLE_NAME;

        public static final String POLLOS = PollosContract.PolloEntry.POLLOS;
        public static final String DATE = PollosContract.PolloEntry.DATE;
    }
}

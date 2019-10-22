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
import com.app.app.DATA.Contracts.AguaContract;

public class AguaProvider extends ContentProvider {
    private static final String URI = "content://com.sanAntonio.android.contentproviders/agua";
    public static final Uri CONTENT_URI = Uri.parse(URI);


    private BDHelper baseData;
    private static final String BD_NOMBRE = BDHelper.DATABASE_NAME;
    private static final int BD_VERSION = BDHelper.DATABASE_VERSION;
    private static final String TABLA_AGUA = AguaContract.AguaEntry.TABLE_NAME;

    private static final int AGUA = 1;
    private static final int AGUA_ID = 2;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URI,"agua",AGUA);
        uriMatcher.addURI(URI,"agua/#",AGUA_ID);
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
        if(uriMatcher.match(uri) == AGUA_ID){
            where = "_id=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = baseData.getWritableDatabase();

        Cursor c = db.query(TABLA_AGUA, projection,where,selectionArgs,null,null,sortOrder);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = uriMatcher.match(uri);
        switch (match){
            case AGUA:
                return "vnd.android.cursor.dir/vnd.sanAntonio.agua";
            case AGUA_ID:
                return "vnd.android.cursor.item/vnd.sanAntonio.agua";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long regId = 1;
        SQLiteDatabase db = baseData.getWritableDatabase();
        regId = db.insert(TABLA_AGUA, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int cont;

        String where = selection;
        if(uriMatcher.match(uri) == AGUA_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = baseData.getWritableDatabase();
        cont = db.delete(TABLA_AGUA, where, selectionArgs);
        return cont;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int cont;

        String where = selection;
        if(uriMatcher.match(uri) == AGUA_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = baseData.getWritableDatabase();
        cont = db.update(TABLA_AGUA,values, where, selectionArgs);
        return cont;
    }

    public static final class AGUA implements BaseColumns {
        private AGUA(){}

        public static final String TABLE_NAME = AguaContract.AguaEntry.TABLE_NAME;

        public static final String AGUA = AguaContract.AguaEntry.AGUA;
        public static final String DATE = AguaContract.AguaEntry.DATE;
    }
}

package com.app.app.DATA.Contracts;

import android.provider.BaseColumns;

public class PesoContract {
    public abstract static class PesoEntry implements BaseColumns {
        public static final String TABLE_NAME = "PESO";

        public static final String ID = "id";
        public static final String PESO = "peso";
        public static final String DATE = "date";
    }
}

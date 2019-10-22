package com.app.app.DATA.Contracts;

import android.provider.BaseColumns;

public class PollosContract {
    public static abstract class PolloEntry implements BaseColumns{
        public static final String TABLE_NAME = "POLLOS";

        public static final String ID = "id";
        public static final String POLLOS = "numPollos";
        public static final String DATE = "date";
    }
}

package com.app.app.DATA.Contracts;

import android.provider.BaseColumns;

public class AguaContract {
    public abstract static class AguaEntry implements BaseColumns{
        public static final String TABLE_NAME = "AGUA";

        public static final String ID = "id";
        public static final String AGUA = "agua";
        public static final String DATE = "date";
    }
}

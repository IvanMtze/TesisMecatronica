package com.app.app.DATA.Contracts;

import android.provider.BaseColumns;

public class AlimentoContract {
    public abstract static class AlimentoEntry implements BaseColumns {
        public static final String TABLE_NAME = "ALIMENTO";

        public static final String ID = "id";
        public static final String ALIMENTO = "alimento";
        public static final String DATE = "date";
    }
}

package com.app.app.DATA.ContentProviders;

import android.provider.BaseColumns;

public class NetworkContract {
    public abstract static class NetworkEntry implements BaseColumns {
        public static final String TABLE_NAME = "NETWORK";

        public static final String ID = "id";
        public static final String IP_DIRECTION = "ip_direction";
    }
}

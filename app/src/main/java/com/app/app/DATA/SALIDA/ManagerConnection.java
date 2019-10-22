package com.app.app.DATA.SALIDA;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.net.MalformedURLException;
import java.net.URL;

public class ManagerConnection {
    public static URL IP = makeURL("http://192.168.4.1");

    private static java.net.URL makeURL(String s) {
        try{
            return new URL(s);
        }catch (MalformedURLException e){
            return null;
        }
    }

    public static boolean isConnected(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else{
            //we are not connected to a network
            return false;
        }
    }
}

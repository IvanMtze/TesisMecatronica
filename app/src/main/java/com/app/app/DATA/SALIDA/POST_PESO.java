package com.app.app.DATA.SALIDA;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class POST_PESO {
    URL url;
    Context context;
    Activity activity;
    public POST_PESO(URL url, Context context, Activity activity) {
        this.url = url;
        this.context = context;
        this.activity = activity;
    }

    public boolean makePost(double peso, String date) throws IOException {

        OkHttpClient client =  new OkHttpClient();
        String url = ManagerConnection.IP.toString()+"/"+peso+","+date;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(context, "ARE YOU CONNECTED?",Toast.LENGTH_LONG);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"OK",Toast.LENGTH_LONG);
                        }
                    });
                }
            }
        });
        return true;
    }
}

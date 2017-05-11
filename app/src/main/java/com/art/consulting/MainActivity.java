package com.art.consulting;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;



import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //String suffix = Uri.encode("#");
        //String ussdCode = "*998*1234"+suffix;
        //startActivityForResult(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)),1);








/*
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://www.google.com/")
                    .build();
            Response response = client.newCall(request).execute();
*/
            /*
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.1.17:8070/rim_learning_spring_mvc/rest/api/get/ane jeyttt"));
            startActivity(browserIntent);
            */



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getApplicationContext(),
                "USSD: " + requestCode + "  " + resultCode + " ",Toast.LENGTH_LONG).show();

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                // String result=data.getStringExtra("result");
                String dd = data.getDataString();
                Toast.makeText(getApplicationContext(), dd, Toast.LENGTH_LONG).show();
            }
        }
    }
}

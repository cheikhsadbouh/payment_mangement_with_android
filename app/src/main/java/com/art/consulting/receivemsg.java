package com.art.consulting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by root on 13/04/17.
 */
public class receivemsg extends BroadcastReceiver {


    final SmsManager mysms = SmsManager.getDefault();
 static int id =1 ;
     String number =null ;
    String withoutspace;
    String from="";
    String to="";
    String credit="";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle mybundle = intent.getExtras();
        HttpURLConnection http;

        try{



            if(mybundle!=null){

                final Object[] msgcontent = (Object[])mybundle.get("pdus");
                for (int i = 0; i<msgcontent.length; i++) {
                    final SmsMessage mynewsms = SmsMessage.createFromPdu((byte[]) msgcontent[i]);
                    NewMessageNotification notification = new NewMessageNotification();
                    notification.notify( context,
                            mynewsms.getDisplayOriginatingAddress(),mynewsms.getDisplayMessageBody(), id);


                    number = mynewsms.getDisplayOriginatingAddress().trim();
                    number = number.substring(1, number.length() - 1);
                    String  msgbody =mynewsms.getDisplayMessageBody().trim();
                    withoutspace = msgbody.replaceAll("\\s", "");
                    int check =0;
                    for(int k=0;k<msgbody.length();k++)
                    {
                        if(Character.isDigit(msgbody.charAt(k))) {
                            if (check < 8) {
                                from = from + msgbody.charAt(k);
                                Log.i("i<8 ",from);
                            }
                            if (check > 7 && check < 16) {
                                to = to + msgbody.charAt(k);
                            }

                            if (check > 15) {
                                credit = credit + msgbody.charAt(k);
                            }

                            check++;
                        }
                    }
                    Log.i("status :  code :"+from+" "+to+"  "+credit,number);

                    Thread thread = new Thread(new Runnable() {
                        HttpURLConnection connection;
                        @Override
                        public void run() {
                            try  {


                                int code =0;
                                URL url = null;

                                url = new URL("http://192.168.43.234:8080/rim_learning_spring_mvc/rest/api/get/"+from+"/"+credit);
                                // Create the request to OpenWeatherMap, and open the connection
                                connection = (HttpURLConnection) url.openConnection();
                                //    connection.setRequestMethod("GET");
                                //  connection.setDoInput(true);





                                code = connection.getResponseCode();
                                connection.getResponseMessage();
                                //  connection.setDoOutput(true);

                                Log.i("response"+connection.getResponseMessage(),"");


                                ///  http.connect();
                            } catch (Exception e) {
                                Log.i("ff + number :"+number, e.toString());
                            }finally {
                                connection.disconnect();
                            }
                        }
                    });

                    thread.start();


                    id++;
                    for(int f=0 ;f<23 ;f++) {

                        Toast.makeText(context, "from : \n" + from + "\n to :" +
                                "\n"+to+"\n credit :" +credit, Toast.LENGTH_LONG).show();
                    }
                    for(int f=0 ;f<23 ;f++) {

                        Toast.makeText(context, "from : \n" + mynewsms.getDisplayOriginatingAddress() + "\n msg body :" +
                                "\n"+withoutspace, Toast.LENGTH_LONG).show();
                    }
                }


            }
        }catch (Exception e){
            Log.i("ca", e.toString());

        }

    }






}

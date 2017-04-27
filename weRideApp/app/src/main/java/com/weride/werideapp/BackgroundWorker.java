package com.weride.werideapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Alpho on 07/04/2017.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker (Context ctx){
        context=ctx;

    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url="http://luxurytradingacademy.com/werideapp/insertStudent.php";
        if(type.equals("register")) {
            try {
                String firstname = params[1];
                String lastname = params[2];
                String age = params[3];
                String address = params[4];
                String email = params[5];
                String username = params[6];
                String password = params[7];
                try {
                    URL url = new URL(register_url);
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                       BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("firstname","UTF-8")+"="+ URLEncoder.encode(firstname,"UTF-8")+
                                "&"+URLEncoder.encode("lastname","UTF-8")+"="+ URLEncoder.encode(lastname,"UTF-8")+
                                "&"+URLEncoder.encode("age","UTF-8")+"="+ URLEncoder.encode(age,"UTF-8")+
                                "&"+URLEncoder.encode("address","UTF-8")+"="+ URLEncoder.encode(address,"UTF-8")+
                                "&"+URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+
                                "&"+URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8")+
                                "&"+URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        InputStream inputStream=httpURLConnection.getInputStream();
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                        String result="";
                        String line="";
                        while((line=bufferedReader.readLine())!=null){
                            result+=line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return result;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }finally{

            }
        }
        return null;

    }
    protected  void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Registration Status");

    }
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        //alertDialog.show();

    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

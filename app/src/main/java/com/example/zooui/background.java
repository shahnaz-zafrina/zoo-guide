package com.example.zooui;

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

public class background extends AsyncTask <String, Void, String> {

    AlertDialog dialog;
    Context context;

    public background(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Animal Description");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.setMessage(s);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... voids){
        String result = "";
        String introtext = voids[0];
        String images = voids[1];

        String connstr = "http://10.0.178.45/test.txt";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("introtext", "UTF-8")+"="+URLEncoder.encode(introtext, "UTF-8")+"&&"+URLEncoder.encode("images", "UTF-8")+"="+URLEncoder.encode(images, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;

        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }


        return result;
    }
}


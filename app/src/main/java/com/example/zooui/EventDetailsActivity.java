package com.example.zooui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EventDetailsActivity extends AppCompatActivity {

    ImageView imageView;
    Button setTimer;
    TextView eventNameTxt, dayTxt, timeTxt, venueTxt, descTxt, textViewSender, langReceiver;
    private int drawableResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent receiverIntent1 = getIntent();
        String receivedValue1 = receiverIntent1.getStringExtra("eventTitle");

        Intent receiverIntent2 = getIntent();
        String receivedValue2 = receiverIntent2.getStringExtra("selectedLanguage");

        imageView = findViewById(R.id.imageView);
        setTimer = findViewById(R.id.setTimer);
        eventNameTxt = findViewById(R.id.eventNameTxt);
        dayTxt = findViewById(R.id.dayTxt);
        timeTxt = findViewById(R.id.timeTxt);
        venueTxt = findViewById(R.id.venueTxt);
        descTxt = findViewById(R.id.descTxt);
        langReceiver = findViewById(R.id.langSender);

        if (receivedValue2 != null && receivedValue2.equals("日本語")){
            langReceiver.setText(getString(R.string.japanese));
        }
        else if (receivedValue2 != null && receivedValue2.equals("ภาษาไทย")){
            langReceiver.setText(getString(R.string.thai));
        }
        else if (receivedValue2 != null && receivedValue2.equals("简体中文")){
            langReceiver.setText(getString(R.string.chinese));
        }
        else if (receivedValue2 != null && receivedValue2.equals("Bahasa Melayu")){
            langReceiver.setText(getString(R.string.malay));
        }
        else if (receivedValue2 != null && receivedValue2.equals("English")){
            langReceiver.setText(getString(R.string.english));
        }

        if (receivedValue1 != null && receivedValue1.equals("Multi-Animal Show")){
            drawableResourceId = R.drawable.show_02;
            imageView.setImageResource(drawableResourceId);
            eventNameTxt.setText(R.string.eventTitle1);
            dayTxt.setText(R.string.eventDay1);
            timeTxt.setText(R.string.eventTime1);
            venueTxt.setText(R.string.eventVenue1);

            String string1 = eventNameTxt.getText().toString();
            String string2 = langReceiver.getText().toString();

            String url = "http://10.0.145.73/retrieveEvent.php";

            RequestQueue queue = Volley.newRequestQueue(EventDetailsActivity.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        // Handle the response from the PHP script
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Assuming the response contains an array of JSON objects
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String introText = jsonObject.getString("introtext");
                                String strippedText = Html.fromHtml(introText.replaceAll("<.*?>", "")).toString();

                                // Update the TextViews with the retrieved data
                                descTxt.setText(strippedText);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.e("Error", error.toString());
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("string1", string1);
                    params.put("string2", string2);
                    return params;
                }
            };
            queue.add(postRequest);

        }
        else if (receivedValue1 != null && receivedValue1.equals("Animal Feeding Session")){
            eventNameTxt.setText(R.string.eventTitle2);
            dayTxt.setText(R.string.eventDay2);
            timeTxt.setText(R.string.eventTime2);
            venueTxt.setText(R.string.eventVenue2);

            String string1 = eventNameTxt.getText().toString();
            String string2 = langReceiver.getText().toString();

            String url = "http://10.0.145.73/retrieveEvent.php";

            RequestQueue queue = Volley.newRequestQueue(EventDetailsActivity.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        // Handle the response from the PHP script
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Assuming the response contains an array of JSON objects
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String introText = jsonObject.getString("introtext");
                                String strippedText = Html.fromHtml(introText.replaceAll("<.*?>", "")).toString();

                                // Update the TextViews with the retrieved data
                                descTxt.setText(strippedText);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.e("Error", error.toString());
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("string1", string1);
                    params.put("string2", string2);
                    return params;
                }
            };
            queue.add(postRequest);
        }
    }

    public void buttonSenderPressed(View v){
        Intent senderIntent = new Intent(EventDetailsActivity.this, AlarmActivity.class);
        textViewSender = (TextView) findViewById(R.id.eventNameTxt);
        senderIntent.putExtra("KEY_SENDER", textViewSender.getText().toString());
        startActivity(senderIntent);
    }
}
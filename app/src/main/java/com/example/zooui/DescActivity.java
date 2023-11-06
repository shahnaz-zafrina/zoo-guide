package com.example.zooui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class DescActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4, textView5, scannedQRtxt;
    Button viewButton, backButton;
    ImageView animalImg;
    LinearLayout linearLayout;
    private int drawableResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);

        scannedQRtxt = findViewById(R.id.scannedQRtxt);
        linearLayout = findViewById(R.id.linearLayout);
        animalImg = findViewById(R.id.animalImg);

        textView1 = findViewById(R.id.textView1);
        Intent receiverIntent = getIntent();
        String receivedValue = receiverIntent.getStringExtra("qrID");
        textView1.setText(receivedValue);

        if (receivedValue != null) {
            if (receivedValue.equals("asian-small-clawed-otters")) {
                drawableResourceId = R.drawable.imgasianotter;
            } else if (receivedValue.equals("capybara")) {
                drawableResourceId = R.drawable.imgcapy;
            } else if (receivedValue.equals("giraffe")) {
                drawableResourceId = R.drawable.imggiraffe;
            } else if (receivedValue.equals("himalayan-griffon")) {
                drawableResourceId = R.drawable.imggriffon;
            } else if (receivedValue.equals("javan-deer")) {
                drawableResourceId = R.drawable.imgjavandeer;
            } else if (receivedValue.equals("malayan-tapir")) {
                drawableResourceId = R.drawable.imgmtapir;
            } else if (receivedValue.equals("malayan-tiger")) {
                drawableResourceId = R.drawable.imgmtiger;
            } else if (receivedValue.equals("raccoon")) {
                drawableResourceId = R.drawable.imgraccoon;
            } else if (receivedValue.equals("malayan-sun-bear")) {
                drawableResourceId = R.drawable.imgsunbear;
            }
        }

        animalImg.setImageResource(drawableResourceId);

        textView2 = findViewById(R.id.textView2);
        Intent receiverIntent2 = getIntent();
        String receivedValue2 = receiverIntent2.getStringExtra("lang");
        if (receivedValue2 != null && receivedValue2.equals("日本語")){
            textView2.setText(getString(R.string.japanese));
        }
        else if (receivedValue2 != null && receivedValue2.equals("ภาษาไทย")){
            textView2.setText(getString(R.string.thai));
        }
        else if (receivedValue2 != null && receivedValue2.equals("简体中文")){
            textView2.setText(getString(R.string.chinese));
        }
        else if (receivedValue2 != null && receivedValue2.equals("Bahasa Melayu")){
            textView2.setText(getString(R.string.malay));
        }
        else if (receivedValue2 != null && receivedValue2.equals("English")){
            textView2.setText(getString(R.string.english));
        }

        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);

        viewButton = findViewById(R.id.button1);
        backButton = findViewById(R.id.button2);

        String string1 = textView1.getText().toString();
        String string2 = textView2.getText().toString();

        String url = "http://10.0.145.73/retrieveAnim.php";

        RequestQueue queue = Volley.newRequestQueue(DescActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle the response from the PHP script
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        // Assuming the response contains an array of JSON objects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String introText = jsonObject.getString("introtext");
                            String note = jsonObject.getString("note");
                            String strippedText = Html.fromHtml(introText.replaceAll("<.*?>", "")).toString();

                            // Update the TextViews with the retrieved data
                            textView3.setText(title);
                            textView4.setText(strippedText);
                            textView5.setText(note);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DescActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
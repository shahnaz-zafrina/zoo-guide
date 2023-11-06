package com.example.zooui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Intent receiverIntent = getIntent();
        String receivedValue = receiverIntent.getStringExtra("lang");

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);

        if (receivedValue != null && receivedValue.equals("日本語")){
            textView1.setText(R.string.tv1Jp);
            textView2.setText(R.string.tv2Jp);
            textView3.setText(R.string.tv3Jp);
            textView4.setText(R.string.tv4Jp);
            textView5.setText(R.string.tv5Jp);
            textView6.setText(R.string.tv6Jp);
            textView7.setText(R.string.tv7Jp);
        }
        else if (receivedValue != null && receivedValue.equals("ภาษาไทย")){
            textView1.setText(R.string.tv1Th);
            textView2.setText(R.string.tv2Th);
            textView3.setText(R.string.tv3Th);
            textView4.setText(R.string.tv4Th);
            textView5.setText(R.string.tv5Th);
            textView6.setText(R.string.tv6Th);
            textView7.setText(R.string.tv7Th);
        }
        else if (receivedValue != null && receivedValue.equals("简体中文")){
            textView1.setText(R.string.tv1Ch);
            textView2.setText(R.string.tv2Ch);
            textView3.setText(R.string.tv3Ch);
            textView4.setText(R.string.tv4Ch);
            textView5.setText(R.string.tv5Ch);
            textView6.setText(R.string.tv6Ch);
            textView7.setText(R.string.tv7Ch);
        }
        else if (receivedValue != null && receivedValue.equals("Bahasa Melayu")){
            textView1.setText(R.string.tv1My);
            textView2.setText(R.string.tv2My);
            textView3.setText(R.string.tv3My);
            textView4.setText(R.string.tv4My);
            textView5.setText(R.string.tv5My);
            textView6.setText(R.string.tv6My);
            textView7.setText(R.string.tv7My);
        }
    }
}
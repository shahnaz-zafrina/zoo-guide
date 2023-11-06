package com.example.zooui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class MapSettingActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_setting);

        btn = findViewById(R.id.selectBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the DestinationActivity
                Intent intent = new Intent(MapSettingActivity.this, MapsActivity.class);

                // Retrieve the selected items from the checklist
                ArrayList<String> selectedItems = new ArrayList<>();

                CheckBox checkBox1 = findViewById(R.id.mTiger);
                if (checkBox1.isChecked()) {
                    selectedItems.add(checkBox1.getText().toString());
                }

                CheckBox checkBox2 = findViewById(R.id.wLion);
                if (checkBox2.isChecked()) {
                    selectedItems.add(checkBox2.getText().toString());
                }

                CheckBox checkBox3 = findViewById(R.id.kCheetah);
                if (checkBox3.isChecked()) {
                    selectedItems.add(checkBox3.getText().toString());
                }

                CheckBox checkBox4 = findViewById(R.id.capy);
                if (checkBox4.isChecked()) {
                    selectedItems.add(checkBox4.getText().toString());
                }

                CheckBox checkBox5 = findViewById(R.id.rtLemur);
                if (checkBox5.isChecked()) {
                    selectedItems.add(checkBox5.getText().toString());
                }

                CheckBox checkBox6 = findViewById(R.id.jDeer);
                if (checkBox6.isChecked()) {
                    selectedItems.add(checkBox6.getText().toString());
                }

                CheckBox checkBox7 = findViewById(R.id.childWorld);
                if (checkBox7.isChecked()) {
                    selectedItems.add(checkBox7.getText().toString());
                }

                CheckBox checkBox8 = findViewById(R.id.bearComp);
                if (checkBox8.isChecked()) {
                    selectedItems.add(checkBox8.getText().toString());
                }

                CheckBox checkBox9 = findViewById(R.id.giraffe);
                if (checkBox9.isChecked()) {
                    selectedItems.add(checkBox9.getText().toString());
                }

                CheckBox checkBox10 = findViewById(R.id.hGriffon);
                if (checkBox10.isChecked()) {
                    selectedItems.add(checkBox10.getText().toString());
                }

                CheckBox checkBox11 = findViewById(R.id.aWallaby);
                if (checkBox11.isChecked()) {
                    selectedItems.add(checkBox11.getText().toString());
                }

                CheckBox checkBox12 = findViewById(R.id.otter);
                if (checkBox12.isChecked()) {
                    selectedItems.add(checkBox12.getText().toString());
                }

                CheckBox checkBox13 = findViewById(R.id.nileHippo);
                if (checkBox13.isChecked()) {
                    selectedItems.add(checkBox13.getText().toString());
                }

                CheckBox checkBox14 = findViewById(R.id.mTapir);
                if (checkBox14.isChecked()) {
                    selectedItems.add(checkBox14.getText().toString());
                }

                CheckBox checkBox15 = findViewById(R.id.entrances);
                if (checkBox15.isChecked()) {
                    selectedItems.add(checkBox15.getText().toString());
                }

                CheckBox checkBox16 = findViewById(R.id.ticketCounter);
                if (checkBox16.isChecked()) {
                    selectedItems.add(checkBox16.getText().toString());
                }

                CheckBox checkBox17 = findViewById(R.id.tramStation);
                if (checkBox17.isChecked()) {
                    selectedItems.add(checkBox17.getText().toString());
                }

                CheckBox checkBox18 = findViewById(R.id.souShop);
                if (checkBox18.isChecked()) {
                    selectedItems.add(checkBox18.getText().toString());
                }

                CheckBox checkBox19 = findViewById(R.id.toilet);
                if (checkBox19.isChecked()) {
                    selectedItems.add(checkBox19.getText().toString());
                }

                CheckBox checkBox20 = findViewById(R.id.restAndKiosk);
                if (checkBox20.isChecked()) {
                    selectedItems.add(checkBox20.getText().toString());
                }

                // Add the selected items to the intent as an extra
                intent.putStringArrayListExtra("SELECTED_ITEMS", selectedItems);

                // Start the DestinationActivity
                startActivity(intent);
            }
        });
    }
}
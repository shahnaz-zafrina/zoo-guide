package com.example.zooui;

import static android.R.attr.id;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmActivity extends Activity {

    TimePicker myTimePicker;
    Button buttonstartSetCustom, buttonSet30, buttonSet20, buttonSet10;
    TextView textAlarmPrompt, textView, textViewReceiver, message;
    private Button button;

    TimePickerDialog timePickerDialog;
    final Context context= this;
    final static int RQS_1 = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        textView = (TextView) findViewById(R.id.textViewReceiver);

        Intent receiverIntent = getIntent();
        String receivedValue = receiverIntent.getStringExtra("KEY_SENDER");
        textView.setText(receivedValue);

        textAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
        buttonstartSetCustom = (Button)findViewById(R.id.startSetDialog);

        buttonSet30 = (Button) findViewById(R.id.alarm30);

        buttonSet30.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePicker30(false);
            }});

        buttonSet20 = (Button) findViewById(R.id.alarm20);

        buttonSet20.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePicker20(false);
            }});

        buttonSet10 = (Button) findViewById(R.id.alarm10);

        buttonSet10.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePicker10(false);
            }});
    }

    //create method to open time picker
    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(
                AlarmActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    private void openTimePicker30(boolean is24r){
        Calendar calendar = Calendar.getInstance();
        if (textView.getText().toString().equals("Multi-Animal Show")) //if Text isnt variable add doube quotes "text"
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 30);
        }

        else if (textView.getText().toString().equals("Animal Feeding Session")) //if Text isnt variable add doube quotes "text"
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 30);
        }
        timePickerDialog = new TimePickerDialog(
                AlarmActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    private void openTimePicker20(boolean is24r){
        Calendar calendar = Calendar.getInstance();
        if (textView.getText().toString().equals("Multi-Animal Show")) //if Text isnt variable add doube quotes "text"
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 40);
        }

        else if (textView.getText().toString().equals("Animal Feeding Session")) //if Text isnt variable add doube quotes "text"
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 40);
        }
        timePickerDialog = new TimePickerDialog(
                AlarmActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    private void openTimePicker10(boolean is24r){
        Calendar calendar = Calendar.getInstance();
        if (textView.getText().toString().equals("Multi-Animal Show")) //if Text isnt variable add doube quotes "text"
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 50);
        }

        else if (textView.getText().toString().equals("Animal Feeding Session")) //if Text isnt variable add doube quotes "text"
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 50);
        }
        timePickerDialog = new TimePickerDialog(
                AlarmActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    OnTimeSetListener onTimeSetListener
            = new OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }
            setAlarm(calSet);
        }};

    private void setAlarm(Calendar targetCal){

        message = (TextView) findViewById(R.id.textViewReceiver);

        textAlarmPrompt.setText(
                "Alarm is set at: \n" + targetCal.getTime() +  "\n" + "for event " + message.getText().toString()
        );

        // Generate a unique id for each alarm using current timestamp
        int id = (int) System.currentTimeMillis();

        //Passing custom value to AlarmNotificationService using pending Intent
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("msg", message.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }
}
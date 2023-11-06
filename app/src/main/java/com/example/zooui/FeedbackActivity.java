package com.example.zooui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    private boolean emailSentSuccessfully = false;

    private EditText editTextName;
    private Spinner spinnerCountry;
    private EditText editTextSubject;
    private EditText editTextBody;
    private Button buttonSendEmail;
    private TextView textViewName, textViewCountry, textViewTitle, textViewContent, textViewRating;

    private static final int REQUEST_EMAIL_SEND = (int) System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent receiverIntent = getIntent();
        String receivedValue = receiverIntent.getStringExtra("lang");

        textViewName = findViewById(R.id.txtName);
        textViewCountry = findViewById(R.id.txtCountry);
        textViewTitle = findViewById(R.id.txtTitle);
        textViewContent = findViewById(R.id.txtContent);
        textViewRating = findViewById(R.id.txtRating);

        editTextName = findViewById(R.id.editTextName);
        editTextSubject = findViewById(R.id.editTextSubject);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        editTextBody = findViewById(R.id.editTextBody);
        buttonSendEmail = findViewById(R.id.buttonSendEmail);

        buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        if (receivedValue != null && receivedValue.equals("日本語")){
            textViewName.setText(R.string.fbNameJp);
            textViewCountry.setText(R.string.fbCountryJp);
            textViewTitle.setText(R.string.fbTitleJp);
            textViewContent.setText(R.string.fbContentJp);
            textViewRating.setText(R.string.fbRatingJp);
        }
        else if (receivedValue != null && receivedValue.equals("ภาษาไทย")){
            textViewName.setText(R.string.fbNameTh);
            textViewCountry.setText(R.string.fbCountryTh);
            textViewTitle.setText(R.string.fbTitleTh);
            textViewContent.setText(R.string.fbContentTh);
            textViewRating.setText(R.string.fbRatingTh);
        }
        else if (receivedValue != null && receivedValue.equals("简体中文")){
            textViewName.setText(R.string.fbNameCh);
            textViewCountry.setText(R.string.fbCountryCh);
            textViewTitle.setText(R.string.fbTitleCh);
            textViewContent.setText(R.string.fbContentCh);
            textViewRating.setText(R.string.fbRatingCh);
        }
        else if (receivedValue != null && receivedValue.equals("Bahasa Melayu")){
            textViewName.setText(R.string.fbNameMy);
            textViewCountry.setText(R.string.fbCountryMy);
            textViewTitle.setText(R.string.fbTitleMy);
            textViewContent.setText(R.string.fbContentMy);
            textViewRating.setText(R.string.fbRatingMy);
        }

        Spinner spinnerCountry = findViewById(R.id.spinnerCountry);

        // Get the array of all countries
        String[] countries = CountryUtils.getAllCountries();

        // Create an ArrayAdapter to populate the Spinner with the country list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the Spinner
        spinnerCountry.setAdapter(adapter);
    }

    private void sendEmail() {
        String name = editTextName.getText().toString();
        String country = spinnerCountry.getSelectedItem().toString();
        String subject = editTextSubject.getText().toString();
        String feedbackContent = editTextBody.getText().toString();
        float rating = ((RatingBar) findViewById(R.id.ratingBarFeedback)).getRating();

        // Create the email body with the feedback details and rating
        String body = "Name: " + name + "\nCountry: " + country + "\nRating: " + rating +
                "\n\n" + feedbackContent;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"2020476486@student.uitm.edu.my"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EMAIL_SEND) {
            if (resultCode == RESULT_OK) {
                // Email was sent successfully
                Toast.makeText(this, "Email sent successfully.", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Email sending was canceled
                Toast.makeText(this, "Email sending canceled.", Toast.LENGTH_SHORT).show();
            } else {
                // Email sending failed or another error occurred
                Toast.makeText(this, "Failed to send email.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (emailSentSuccessfully) {
            // Email was sent successfully
            Intent feedbackThanksIntent = new Intent(this, FeedbackThanksActivity.class);
            startActivity(feedbackThanksIntent);

            // Reset the flag to false
            emailSentSuccessfully = false;
        }
    }
}
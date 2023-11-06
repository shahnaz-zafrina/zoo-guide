package com.example.zooui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class FeedbackThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_thanks);

        ImageView imageView = findViewById(R.id.likeIcon);

        // Load the animation from the XML file
        Animation pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_anim);

        // Apply the animation to the ImageView
        imageView.startAnimation(pulseAnimation);

        Button btnSendAnother = findViewById(R.id.btnSendAnother);
        Button btnHome = findViewById(R.id.btnHome);

        btnSendAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity to submit another feedback
                Intent intent = new Intent(FeedbackThanksActivity.this, FeedbackActivity.class);
                startActivity(intent);
                // Finish the current activity if you want to go back to the main menu instead of stacking the activities.
                // Otherwise, remove the following line.
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity to go back to the main menu or any other desired activity.
                Intent intent = new Intent(FeedbackThanksActivity.this, MainActivity.class);
                startActivity(intent);
                // Finish the current activity if you want to return to the main menu instead of stacking the activities.
                // Otherwise, remove the following line.
                finish();
            }
        });
    }
}
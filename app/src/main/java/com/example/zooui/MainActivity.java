package com.example.zooui;

import static android.R.attr.id;
import static android.R.attr.start;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_scan;
    Button settingBtn;
    String selectedLanguage;
    FromQR qr = new FromQR();
    private int selectedOptionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedLanguage = "English";

        settingBtn = findViewById(R.id.BtnSetting);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDialog();
            }

            private void showOptionDialog() {
                String[] languages = {"English", "Bahasa Melayu", "日本語", "ภาษาไทย", "简体中文"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Description language");
                builder.setSingleChoiceItems(languages, selectedOptionIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedOptionIndex = which; // Update the selected option index
                        selectedLanguage = languages[which];
                        Toast.makeText(MainActivity.this, "Language selected: " + selectedLanguage, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        btn_scan = findViewById(R.id.BtnScan);
        btn_scan.setOnClickListener(v ->
        {
            scanCode();
        });

        ViewPager viewPager = findViewById(R.id.viewPager);
        List<Integer> imageList = Arrays.asList(R.drawable.eventshow, R.drawable.eventfeeding);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, imageList, selectedLanguage);
        viewPager.setAdapter(adapter);

        ImageButton previousButton = findViewById(R.id.previousButton);
        ImageButton nextButton = findViewById(R.id.nextButton);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem > 0) {
                    viewPager.setCurrentItem(currentItem - 1);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem < adapter.getCount() - 1) {
                    viewPager.setCurrentItem(currentItem + 1);
                }
            }
        });

        adapter.setOnItemClickListener(new ImageSliderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String eventTitle;
                if (position == 0) {
                    eventTitle = "Multi-Animal Show";
                } else {
                    eventTitle = "Animal Feeding Session";
                }

                Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
                intent.putExtra("selectedLanguage", selectedLanguage);
                intent.putExtra("eventTitle", eventTitle);
                startActivity(intent);
            }
        });
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to turn flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("QR Scanned!");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    qr.setQrId(result.getContents());

                    Intent senderIntent = new Intent(MainActivity.this, DescActivity.class);
                    senderIntent.putExtra("qrID", result.getContents());
                    senderIntent.putExtra("lang", selectedLanguage);
                    startActivity(senderIntent);
                }
            }).show();
        }
    });

    public void actMap(View v) {
        Intent i = new Intent(this, MapSettingActivity.class);
        startActivity(i);
    }

    public void actAbout(View v) {
        Intent i = new Intent(this, AboutActivity.class);
        i.putExtra("lang", selectedLanguage);
        startActivity(i);
    }

    public void actFeedback(View v) {
        Intent i = new Intent(this, FeedbackActivity.class);
        i.putExtra("lang", selectedLanguage);
        startActivity(i);
    }
}

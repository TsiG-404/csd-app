package com.example.csd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_CAPTURE = 1;
    ImageView photoView;

    private TextView timerTextView;
    public long selectedTimeInMillis=10*60*1000;

    private CountDownTimer countDownTimer;
    //private View webView;
    private WebView webView;
    public boolean stime=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign the menu_context_img_actions to the ImageView
        //photoView = findViewById(R.id.photoView);
        //registerForContextMenu(photoView);

        //Enable the Up button on the app bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timerTextView = findViewById(R.id.timerTextView);

        // Show a dialog to select the timer duration
        showTimerDialog();

        //pt2
        //webView = findViewById(R.id.webView);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.csd.auth.gr/");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    //timer pou otan teleiosei kleinei tin efarmogi

    private void showTimerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Διάλεξε χρονικό περιθώριο");

        // Array of options for the timer duration
        final CharSequence[] options = {"1 Λεπτό", "15 Λεπτά", "45 Λεπτά"};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        selectedTimeInMillis = 1 * 60 * 1000; // 1 minute
                        stime=true;
                        break;
                    case 1:
                        selectedTimeInMillis = 15 * 60 * 1000; // 15 minutes
                        stime=true;
                        break;
                    case 2:
                        selectedTimeInMillis = 45 * 60 * 1000; // 45 minutes
                        stime=true;
                        break;
                }

                startTimer();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(selectedTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeString = String.format("%02d:%02d", minutes, seconds);
                timerTextView.setText(timeString);
                String  hms = (TimeUnit.MILLISECONDS.toHours(millis))+":"+(TimeUnit.MILLISECONDS.toMinutes(millis) -TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)))+":"+ (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                //counter.setTitle(hms);
            }

            @Override
            public void onFinish() {
                // Display a message when the timer ends
                showMessageAndExit("Τέλος χρόνου");
            }
        };

        countDownTimer.start();
    }



    private void showMessageAndExit(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ευχαριστούμε που χρησιμοποιήσατε την εφαρμογή")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Close the app
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel the timer to avoid memory leaks
        }
    }
    //long timer=selectedTimeInMillis;
    long timer= 1 * 60 * 1000;
    //selectedTimeInMillis = 99*60*1000;



    //timer=getSelectedTimeInMillis();
    //Inflate the menu_appbar to the layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_appbar, menu);


        //edo alagi
        //selectedTimeInMillis=9*60*1000;
        timer = 100 * 60 * 1000;
        timer = selectedTimeInMillis;
        //System.out.println(timer);

            final MenuItem counter = menu.findItem(R.id.counter);
        if (stime == false) {
            timer = selectedTimeInMillis;
            new CountDownTimer(timer, 1000) {

                public void onTick(long millisUntilFinished) {
                    long millis = millisUntilFinished;
                    String hms = (TimeUnit.MILLISECONDS.toHours(millis)) + ":" + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))) + ":" + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                    counter.setTitle(hms);
                    timer = millis;

                }

                public void onFinish() {
                    counter.setTitle("ok!");
                }

            }.start();

        }
            return true;
        }


    //Handle clicks on menu_appbar items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ConstraintLayout mainLayout = findViewById(R.id.MainLayoutView);

        switch (item.getItemId()) {
            //Take photo (single menu item)


            //Main menu
            case R.id.menu_about:
                //Send explicit intent to AboutActivity
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_program:
                //Send explicit intent to AboutActivity
                i = new Intent(this, ProActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_eudoxus:
                //Send explicit intent to AboutActivity
                i = new Intent(this, EuActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_mail:
                //Send explicit intent to AboutActivity
                i = new Intent(this, MailActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_sis:
                //Send explicit intent to AboutActivity
                i = new Intent(this, SisActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                //Photo saved in variable "data" of Bundle item
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                //NOTE: this is a thumbnail of the photo so it looks blurry. To take photo
                //with good resolution see https://developer.android.com/training/camera/photobasics
                photoView.setImageBitmap(imageBitmap);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Photo taking cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to take photo",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    //Inflate the menu_context_img_actions to the layout
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_context_img_actions, menu);
    }

    //Handle clicks on menu_context_img_actions items
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        float currentAlpha = photoView.getImageAlpha(); //0=transparent, 255=opaque
        switch (item.getItemId()){
            case R.id.menu_img_alpha_down:
                if (currentAlpha - 51 < 0) photoView.setImageAlpha(0);
                else photoView.setImageAlpha (photoView.getImageAlpha() - 51);
                return true;
            case R.id.menu_img_alpha_up:
                if (currentAlpha + 51 > 255) photoView.setImageAlpha(255);
                else photoView.setImageAlpha (photoView.getImageAlpha() + 51);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //edo nea
    @Override
    public void onBackPressed() {
        // Go back in the WebView if possible
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}
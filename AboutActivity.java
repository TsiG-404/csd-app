package com.example.csd;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

public class AboutActivity extends MainActivity {
    TextView change_log_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        //Enable the Up button on the app bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}

package com.silatsaktistudios.plmgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OptionsActivity.this, PLMGRActivity.class));
        finish();
    }
}

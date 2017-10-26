package com.neo1125.numberkeyviewsample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.neo1125.numberkeyview.NumberKeyOnClickListener;
import com.neo1125.numberkeyview.NumberKeyView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberKeyView numberKeyView = (NumberKeyView) findViewById(R.id.numberKey);
        numberKeyView.setOnNumberKeyOnClickListener(new NumberKeyOnClickListener() {
            @Override
            public void onClick(NumberKeyView.Key key) {
                Log.d("TEST", "############### key : " + key);
            }
        });
    }
}

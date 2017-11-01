package com.neo1125.numberkeyviewsample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.neo1125.numberkeyview.NumberKeyOnClickListener;
import com.neo1125.numberkeyview.NumberKeyView;
import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity {

    NumberKeyView numberKeyView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        numberKeyView = (NumberKeyView) findViewById(R.id.numberKey);
        numberKeyView.setOnNumberKeyOnClickListener(new NumberKeyOnClickListener() {
            @Override
            public void onClick(NumberKeyView.Key key) {
                String text = textView.getText().toString();
                switch (key) {
                    case clear:
                        if (text != null && !text.equals(""))
                            textView.setText(text.substring(0, text.length()-1));
                        break;
                    case custom:
                        break;
                    default:
                        textView.setText(text + key.toString());
                        break;
                }

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void onClick(View v) {
        int rand = (int) ((Math.random() * 10) + 1);
        numberKeyView.setKeyCustomText((rand%2 == 0) ? "적립" : "입력");
        numberKeyView.setKeyCustomEnabled((rand%3 == 0) ? true : false);
    }
}

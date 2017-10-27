package com.neo1125.numberkeyviewsample;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        Typekit.getInstance()
//                .addNormal(Typekit.createFromAsset(this, "HelveticaNeue Medium.ttf"))
//                .addBold(Typekit.createFromAsset(this, "HelveticaNeue Bold.ttf"))
//                .addItalic(Typekit.createFromAsset(this, "HelveticaNeueThin.ttf"))
//                .add("NotoSansLight", Typekit.createFromAsset(this, "HelveticaNeueThin.ttf"));
    }
}

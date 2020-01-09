package com.example.android.practiceset2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {




    static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToTrainingSession (View view) {
        Intent intent = new Intent(this, DailyTrainingSessionActivity.class);
        startActivity(intent);
    }

    public void goToBrowseAllRecords (View view) {
        Intent intent = new Intent(this, BrowseAllRecords.class);
        startActivity(intent);
    }

}
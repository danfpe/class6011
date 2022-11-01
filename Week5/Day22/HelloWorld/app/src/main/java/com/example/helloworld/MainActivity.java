package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean firstClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("SetTextI18n")
    public void handleClick(View view) { // view is a widget
        Log.i("Dd:MainActivity", "button was pressed");

        if(firstClicked) {
            TextView roowView = findViewById(R.id.textView);
            roowView.setText("A new world");
            firstClicked = false;
        }
        else {
            Intent intent = new Intent(this, AnotherActivity.class);
            startActivity(intent);
        }

    }
}
package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView my_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*my_text_view = (TextView) findViewById(R.id.my_text_view);
        Intent intent = getIntent();

        if (intent.hasExtra("my_text")) {
            String sharedText = intent.getStringExtra("my_text");
            my_text_view.setText(sharedText);
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        my_text_view = (TextView) findViewById(R.id.my_text_view);
        Intent intent = getIntent();
        Log.d("APP2","SETANDO TEXTO");

        if (intent.hasExtra("my_text")) {

            String my_text = intent.getStringExtra("my_text");
            Log.d("APP2","SETANDO TEXTO "+ my_text);
            my_text_view.setText(my_text);
        }
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        Log.d("APP2","STOPPING");
        Intent intent = getIntent();
        intent.removeExtra("my_text");
    }*/
}
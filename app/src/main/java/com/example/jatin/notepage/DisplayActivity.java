package com.example.jatin.notepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //getting the note_id in the variable
        int note_id = getIntent().getIntExtra("note_id", -1);

        //if there is no such id move back to previous activity
        if (note_id == -1) this.finish();



    }
}

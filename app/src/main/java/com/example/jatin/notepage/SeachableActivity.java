package com.example.jatin.notepage;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by present on 2/14/17.
 */

public class SeachableActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // get the intent and verify the get the action and verify the query
        Intent intent = getIntent();
        if(intent.ACTION_SEARCH.equals(intent.getAction())){
            String[] query = intent.getStringArrayExtra(SearchManager.QUERY);
            domysearch(query);
        }



    }


}

package com.example.jatin.notepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Title> titles = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainListAdapter mainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //content
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
//        intent.putExtra("note_id", 2);
//        startActivity(intent);


        //setting up recycler view
        recyclerView = (RecyclerView)findViewById(R.id.list_recycler);

        mainListAdapter = new MainListAdapter(titles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainListAdapter);

        //populating notes list
        Database db = new Database(this);
        db.addTitle("title1");
        db.addTitle("title2");
        db.addTitle("title3");
        try {
            db.addNote("something", 1);
        } catch (Database.DatabaseException e) {
            e.printStackTrace();
        }
        titles = db.getTitles();

        //updating list
        mainListAdapter.notifyDataSetChanged();
    }


    //Adapter for the list
    public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

        private List<Title> titles;

        public class ViewHolder extends RecyclerView.ViewHolder {

            //getting the Views from the layout file
            public TextView title, data;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView)itemView.findViewById(R.id.title);
                data = (TextView)itemView.findViewById(R.id.data);
            }
        }

        //constructor
        public MainListAdapter(List<Title> titles){
            this.titles = titles;
        }

        // Implemented methods
        @Override
        public MainListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_layout, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MainListAdapter.ViewHolder holder, int position) {
            Title title = titles.get(position);
            holder.title.setText(title.getTitle());
            holder.data.setText(title.getLatestMessage());
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
    }

}

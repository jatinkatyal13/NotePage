package com.example.jatin.notepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Title> titles = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainListAdapter mainListAdapter;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //content
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
//        intent.putExtra("note_id", 2);
//        startActivity(intent);


        //setting up recycler view
        recyclerView = (RecyclerView)findViewById(R.id.list_recycler);

        mainListAdapter = new MainListAdapter(titles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainListAdapter);

        //populating notes list
        db = new Database(this);
        titles = db.getTitles();
//        Log.e("jatin", titles.get(0).getTitle());

        recyclerView.setAdapter(new MainListAdapter(titles));


        //updating list
//        mainListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        titles = db.getTitles();
//        Log.e("jatin", titles.get(0).getTitle());

        recyclerView.setAdapter(new MainListAdapter(titles));
    }

    //Adapter for the list
    public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

        private List<Title> titles;

        public class ViewHolder extends RecyclerView.ViewHolder {

            //getting the Views from the layout file
            public TextView title, data;
            public LinearLayout list_item;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView)itemView.findViewById(R.id.title);
                data = (TextView)itemView.findViewById(R.id.data);
                list_item = (LinearLayout)itemView.findViewById(R.id.list_item);
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
            final Title title = titles.get(position);
            holder.title.setText(title.getTitle());
            holder.data.setText(title.getLatestMessage());
            holder.list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent (MainActivity.this, DisplayActivity.class);
                    intent.putExtra("title_id", title.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
    }

}

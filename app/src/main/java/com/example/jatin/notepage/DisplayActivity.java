package com.example.jatin.notepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private List<String> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private DisplayListAdapter displayListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //getting the note_id in the variable
        int note_id = getIntent().getIntExtra("note_id", -1);

        //if there is no such id move back to previous activity
        if (note_id == -1) this.finish();

        //populating the messages according to note_id
        displayListAdapter = new DisplayListAdapter(messages);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(displayListAdapter);
        messages.add("Something");
        messages.add("Something2");
        messages.add("something3");

        displayListAdapter.notifyDataSetChanged();

    }

    public class DisplayListAdapter extends RecyclerView.Adapter<DisplayListAdapter.ViewHolder> {

        List<String> messages = new ArrayList<>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView message;

            public ViewHolder(View itemView) {
                super(itemView);
                message = (TextView)itemView.findViewById(R.id.list_recycler);
            }
        }

        public DisplayListAdapter(List<String> messages){
            this.messages = messages;
        }

        @Override
        public DisplayListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.display_list_layout, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(DisplayListAdapter.ViewHolder holder, int position) {
            holder.message.setText(messages.get(position));
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }
    }
}

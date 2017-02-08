package com.example.jatin.notepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        recyclerView = (RecyclerView)findViewById(R.id.list_recycler);
        displayListAdapter = new DisplayListAdapter(messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(displayListAdapter);

        //adding data to list
        messages.add("Something");
        messages.add("Something2");
        messages.add("something3");

        //updating the list
        displayListAdapter.notifyDataSetChanged();

        //scroll to the newest note
        scrollToBottom();


        //edit text reference of message box
        final EditText message = (EditText) findViewById(R.id.message);
        ImageView button = (ImageView) findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String note = message.getText().toString();
                note.trim();
                addNote(note);
                message.setText("");

            }
        });

    }

    private void addNote(String note){
        messages.add(note);
        displayListAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    private void scrollToBottom(){
        recyclerView.canScrollVertically(0);
    }

    public class DisplayListAdapter extends RecyclerView.Adapter<DisplayListAdapter.ViewHolder> {

        List<String> messages = new ArrayList<>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView message;

            public ViewHolder(View itemView) {
                super(itemView);
                message = (TextView)itemView.findViewById(R.id.message);
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

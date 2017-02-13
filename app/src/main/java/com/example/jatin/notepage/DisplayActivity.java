package com.example.jatin.notepage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private List<Note> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private DisplayListAdapter displayListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //getting the note_id in the variable
        final int title_id = getIntent().getIntExtra("title_id", -1);

        //if there is no such id move back to previous activity
        if (title_id == -1) this.finish();

        //populating the messages according to note_id
        recyclerView = (RecyclerView)findViewById(R.id.list_recycler);
        displayListAdapter = new DisplayListAdapter(messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(displayListAdapter);

        //adding data to list
        final Database db = new Database(DisplayActivity.this);
        messages = db.getNotes(title_id);

        //updating the list
        recyclerView.setAdapter(new DisplayListAdapter(messages));
        //displayListAdapter.notifyDataSetChanged();

        //scroll to the newest note
        scrollToBottom();


        //edit text reference of message box
        final EditText message = (EditText) findViewById(R.id.message);
        ImageView button = (ImageView) findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String data = message.getText().toString();
                data.trim();
                if (data != "" && data.length()<200){
                    addNote(new Note(data), title_id, db);
                    message.setText("");
                }
            }
        });

    }

    private void addNote(Note note, int title_id, Database db){
        try{
            db.addNote(note.getMessage(), title_id);
            messages.add(note);

        } catch (Database.DatabaseException e){
            Log.e("jaitn", "not added");
        }

        displayListAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    private void scrollToBottom(){
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());

    }

    public class DisplayListAdapter extends RecyclerView.Adapter<DisplayListAdapter.ViewHolder> {

        List<Note> messages = new ArrayList<>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView message;
            public CheckBox done;
            public CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                message = (TextView)itemView.findViewById(R.id.message);
                done = (CheckBox)itemView.findViewById(R.id.done);
                cardView = (CardView)itemView.findViewById(R.id.card);
            }
        }

        public DisplayListAdapter(List<Note> messages){
            this.messages = messages;
        }

        @Override
        public DisplayListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_list_layout, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final DisplayListAdapter.ViewHolder holder, int position) {
            holder.message.setText(messages.get(position).getMessage());
            holder.done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.done.isSelected()){
                        holder.message.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.message.setText(holder.message.getText().toString());
                    } else {
                        holder.message.setPaintFlags(0);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }
    }
}

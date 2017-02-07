package com.example.jatin.notepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

        private List<Note> notes;

        public class ViewHolder extends RecyclerView.ViewHolder {

            //getting the Views from the layout file
            public TextView title, data;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView)itemView.findViewById(R.id.title);
                data = (TextView)itemView.findViewById(R.id.data);
            }
        }

        @Override
        public MainListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MainListAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}

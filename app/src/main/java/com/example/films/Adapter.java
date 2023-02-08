package com.example.films;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.GroupHolder> {
    Context context;
    ArrayList<Group> group;
    public Adapter(Context context, ArrayList<Group> group){
        this.context = context;
        this.group = group;
    }


    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new GroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        Group gr = group.get(position);
        holder.tv.setText(gr.name);

    }

    @Override
    public int getItemCount() {
        return group.size();
    }

    public static class GroupHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.t);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

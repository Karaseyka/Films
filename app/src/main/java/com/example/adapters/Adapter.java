package com.example.adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.ui.main.forGroup.Group;
import com.example.films.ui.main.forGroup.GroupActivity;
import com.example.films.R;

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
        holder.tv1.setText(gr.id);

    }

    @Override
    public int getItemCount() {
        return group.size();
    }

    public static class GroupHolder extends RecyclerView.ViewHolder {

        TextView tv;
        TextView tv1;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.t);
            tv1 = itemView.findViewById(R.id.id);
            Context context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myintent = new Intent(context, GroupActivity.class).putExtra("id", tv1.getText());
                    context.startActivity(myintent);
                }
            });
        }

    }
}

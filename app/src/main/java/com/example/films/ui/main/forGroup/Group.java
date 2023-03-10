package com.example.films.ui.main.forGroup;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Group {
    public String name, id;

    public Group() {
    }

    public Group(String name, String id){
        this.id = id;
        this.name = name;
    }

}

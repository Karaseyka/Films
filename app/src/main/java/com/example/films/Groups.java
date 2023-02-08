package com.example.films;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Groups extends Fragment {
    private DatabaseReference mdb;
    private FirebaseAuth ma;
    private FirebaseRecyclerAdapter rA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        ma = FirebaseAuth.getInstance();
        ArrayList<String> a = new ArrayList<>();;
        mdb = FirebaseDatabase.getInstance().getReference();

        mdb.child("Users").child(ma.getCurrentUser().getUid()).child("Group").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    TextView tw = v.findViewById(R.id.textView13);

                    Group gr1 = postSnapshot.getValue(Group.class);
                    assert gr1 != null;
                    tw.setText(gr1.name);
                    a.add(gr1.name);

                }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
        });
        TextView tw = v.findViewById(R.id.textView13);

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.recyclerview);
        ArrayList<Group> list = new ArrayList<>();
        Adapter ad = new Adapter(this.getContext(), list);
        for(int i = 0; i < a.size(); i ++){
            Group gr = new Group(a.get(i));
            list.add(gr);
        }

        rv.setAdapter(ad);

        ad.notifyDataSetChanged();
//        FirebaseRecyclerOptions<Group> options =
//                new FirebaseRecyclerOptions.Builder<Group>()
//                        .setQuery(mdb.child(), Group.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<Group, Group.GroupHolder> adapter = new FirebaseRecyclerAdapter<Group, Group.GroupHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull Group.GroupHolder holder, int position, @NonNull Group model) {
//
//            }
//
//            @NonNull
//            @Override
//            public Group.GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.message for each item
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_item, parent, false);
//
//                return new Group.GroupHolder(view);
//            }
//
//
//        };

    }
}
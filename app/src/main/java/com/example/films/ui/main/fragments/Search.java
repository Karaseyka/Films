package com.example.films.ui.main.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.adapters.AdapterUser;
import com.example.films.R;
import com.example.films.ui.main.forUser.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class Search extends Fragment {
    private FirebaseAuth ma;
    private DatabaseReference mdb;
    public ArrayList<User> list;
    public ArrayList<String> nt;
    public ArrayList<String> spin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        list = new ArrayList<>();
        nt = new ArrayList<>();
        spin = new ArrayList<>();
        Spinner sp = (Spinner) v.findViewById(R.id.spinner);
        SearchView sv = (SearchView) v.findViewById(R.id.searchView);
        String[] choose = getResources().getStringArray(R.array.searchBy);
        mdb = FirebaseDatabase.getInstance().getReference();
        ma = FirebaseAuth.getInstance();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                spin.clear();

                spin.add(choose[selectedItemPosition]);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list.clear();
                nt.clear();
                assert newText != null;
                nt.add(newText);
                try {
                    String x = spin.get(0);

                    if (Objects.equals(x, choose[0])) {
                        mdb.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot i : snapshot.getChildren()) {
                                    User us = i.getValue(User.class);
//                                Log.d("fhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", nt.get(0));
                                    if (us.email.toLowerCase().contains(nt.get(0).toLowerCase())) {
                                        list.add(us);
                                    }
                                }
                                RecyclerView rv = (RecyclerView) v.findViewById(R.id.users);
                                AdapterUser adapter = new AdapterUser((Context) getActivity(), list);
                                rv.setAdapter(adapter);
                                rv.setLayoutManager(new LinearLayoutManager((Context) getActivity()));
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else if (Objects.equals(spin.get(0), choose[1])) {
                        mdb.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot i : snapshot.getChildren()) {
                                    User us = i.getValue(User.class);
                                    Log.d("fhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", nt.get(0));
                                    if (us.name.toLowerCase().contains(nt.get(0).toLowerCase())) {
                                        list.add(us);
                                    }
                                }
                                RecyclerView rv = (RecyclerView) v.findViewById(R.id.users);
                                AdapterUser adapter = new AdapterUser((Context) getActivity(), list);
                                rv.setAdapter(adapter);
                                rv.setLayoutManager(new LinearLayoutManager((Context) getActivity()));
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else {
                        mdb.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot i : snapshot.getChildren()) {
                                    String us = i.getKey().toString();
                                    User user = i.getValue(User.class);
                                    Log.d("fhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", nt.get(0));
                                    if (us.contains(nt.get(0))) {
                                        list.add(user);
                                    }
                                }
                                RecyclerView rv = (RecyclerView) v.findViewById(R.id.users);
                                AdapterUser adapter = new AdapterUser((Context) getActivity(), list);
                                rv.setAdapter(adapter);
                                rv.setLayoutManager(new LinearLayoutManager((Context) getActivity()));
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }catch(Exception e){}
                    return false;
            }

        });
    }
}
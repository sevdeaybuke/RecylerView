package com.example.recylerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<PersonUtils> personUtilsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        personUtilsList = new ArrayList<>();


        personUtilsList.add(new PersonUtils("Sevde Aybüke Kaleli","Project Manager"));
        personUtilsList.add(new PersonUtils("Ayşe Gün","Senior Developer"));
        personUtilsList.add(new PersonUtils("Ahmet Sezer","Lead Developer"));
        personUtilsList.add(new PersonUtils("Can Caner","Lead Developer"));
        personUtilsList.add(new PersonUtils("Hilal Al","UI/UX Developer"));
        personUtilsList.add(new PersonUtils("Begüm Günel","Front-End Developer"));
        personUtilsList.add(new PersonUtils("Damla Yanar","Backend Developer"));
        personUtilsList.add(new PersonUtils("Merve Su","Android Developer"));


        mAdapter = new CustomRecyclerAdapter(this, personUtilsList);

        recyclerView.setAdapter(mAdapter);

    }
}
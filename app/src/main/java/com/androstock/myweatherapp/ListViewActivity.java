package com.androstock.myweatherapp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.androstock.myweatherapp.R.styleable.View;

public class ListViewActivity extends Activity {

    EditText citylist_edt;
    Button searchBtn_listview;

    private ArrayList<String> mArrayList;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        citylist_edt = (EditText) findViewById(R.id.citylist_edt);
        searchBtn_listview =(Button) findViewById(R.id.searchBtn_listview);


        ListView listView = (ListView)findViewById(R.id.listview_activity);

        String[] cities = { "Houston", "New York", "San Jose", "Beaumont", "Surat"};
        mArrayList = new ArrayList<>(Arrays.asList(cities));

        adapter = new ArrayAdapter<String>(this,R.layout.city_list,R.id.cityname,mArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String name =(String)adapterView.getAdapter().getItem(position);
                Intent intent = new Intent(view.getContext(),MainActivity.class);
                intent.putExtra("CITY",name);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
            }
        });


        searchBtn_listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCity = citylist_edt.getText().toString();
                mArrayList.add(newCity);
                adapter.notifyDataSetChanged();
            }
        });


    }




}

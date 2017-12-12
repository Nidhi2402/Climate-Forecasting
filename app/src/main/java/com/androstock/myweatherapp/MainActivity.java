package com.androstock.myweatherapp;

import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    Typeface weatherFont;

    EditText anyLocationEDT;
    Button searchButton;
    LocationManager locationManager;
    String lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        anyLocationEDT = (EditText) findViewById(R.id.search_field);
        searchButton =(Button) findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(this);

        weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");

        cityField = (TextView)findViewById(R.id.city_field);
        updatedField = (TextView)findViewById(R.id.updated_field);
        detailsField = (TextView)findViewById(R.id.details_field);
        currentTemperatureField = (TextView)findViewById(R.id.current_temperature_field);
        humidity_field = (TextView)findViewById(R.id.humidity_field);
        pressure_field = (TextView)findViewById(R.id.pressure_field);
        weatherIcon = (TextView)findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);

        String cityToSearch = getIntent().getStringExtra("CITY");
        onSearch(cityToSearch);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchBtn:
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchButton.getWindowToken(),0);
                //onSearch();
                break;
        }
    }

    //this method search the location
    private void onSearch(String city) {
        String location = city;
        List<Address> addressList = null;
         if (location != null || !location.equals("")) {
             Geocoder geocoder = new Geocoder(this);
             try {
                 addressList = geocoder.getFromLocationName(location, 1);


             } catch (IOException e) {
                 e.printStackTrace();
             }
             Address address = addressList.get(0);
             String locality = address.getLocality();
             Toast.makeText(this, locality, Toast.LENGTH_LONG).show();  //show location
             this.lat= String.valueOf(address.getLatitude());
             this.lng= String.valueOf(address.getLongitude());

             getWeather(lat,lng);

         }


    }

    private void getWeather(String lat,String lng) {
        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText("Humidity: "+weather_humidity);
                pressure_field.setText("Pressure: "+weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));

            }
        });
        asyncTask.execute(lat,lng); //  asyncTask.execute("Latitude", "Longitude")
    }
}

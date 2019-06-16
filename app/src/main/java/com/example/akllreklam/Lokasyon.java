package com.example.akllreklam;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lokasyon extends AppCompatActivity implements LocationListener {
    private int izinKontrol;
    private String konumSaglayici = "gps";
    private LocationManager locationManager;
    private String en2, boy2;
    private String esik = "100";
    private EditText editEnlem;
    private EditText editBoylam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasyon);
        editEnlem = (EditText) findViewById(R.id.editEnlem);
        editBoylam = (EditText) findViewById(R.id.editBoylam);
        final EditText editTextmesafe = (EditText) findViewById(R.id.editTextesik);
        final Button buttonDegistir = (Button) findViewById(R.id.buttonDegistir);
        final Button buttonmfiltre = (Button) findViewById(R.id.buttonmfiltre);
        final boolean[] numeric = {true};
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        editTextmesafe.setText("100");


        izinKontrol = ContextCompat.checkSelfPermission(Lokasyon.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (izinKontrol != PackageManager.PERMISSION_GRANTED) {
            //daha önceden izin verilmemis
            ActivityCompat.requestPermissions(Lokasyon.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            //daha önceden izin verilmis
            Location konum = locationManager.getLastKnownLocation(konumSaglayici);
            if (konum != null) {
                onLocationChanged(konum);

            } else {
                Toast.makeText(getApplicationContext(), "Enlem-Boylam Aktif Değil!", Toast.LENGTH_SHORT).show();
            }

        }

        buttonDegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enlem = editEnlem.getText().toString();
                String boylam = editBoylam.getText().toString();
                en2 = enlem;
                boy2 = boylam;

                try {
                    Double num = Double.parseDouble(enlem);
                } catch (NumberFormatException e) {
                    numeric[0] = false;
                    Toast.makeText(getApplicationContext(), "Lütfen sayı giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Double num = Double.parseDouble(boylam);
                } catch (NumberFormatException e) {
                    numeric[0] = false;
                    Toast.makeText(getApplicationContext(), "Lütfen sayı giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Enlem-Boylam Değişti!", Toast.LENGTH_SHORT).show();


            }
        });
        buttonmfiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enlem = editEnlem.getText().toString();
                String boylam = editBoylam.getText().toString();
                esik = editTextmesafe.getText().toString();
                try {
                    Double num = Double.parseDouble(esik);
                } catch (NumberFormatException e) {
                    numeric[0] = false;
                    Toast.makeText(getApplicationContext(), "Lütfen sayı giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Double num = Double.parseDouble(enlem);
                } catch (NumberFormatException e) {
                    numeric[0] = false;
                    Toast.makeText(getApplicationContext(), "Lütfen sayı giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Double num = Double.parseDouble(boylam);
                } catch (NumberFormatException e) {
                    numeric[0] = false;
                    Toast.makeText(getApplicationContext(), "Lütfen sayı giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(Lokasyon.this, AnaSayfa2.class);

                i.putExtra("enlem", enlem);
                i.putExtra("boylam", boylam);
                i.putExtra("esik", esik);
                startActivity(i);


            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100) {
            izinKontrol = ContextCompat.checkSelfPermission(Lokasyon.this, Manifest.permission.ACCESS_FINE_LOCATION);

            Location konum = locationManager.getLastKnownLocation(konumSaglayici);
            if (konum != null) {
                onLocationChanged(konum);
            } else {
                Toast.makeText(getApplicationContext(), "Enlem-Boylam Aktif Değil!", Toast.LENGTH_SHORT).show();
            }

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "İZİN VERİLDİ", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "İZİN VERİLMEDİ", Toast.LENGTH_SHORT).show();


            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double enlem = location.getLatitude();
        double boylam = location.getLongitude();
        en2 = String.valueOf(enlem);
        boy2 = String.valueOf(boylam);
        editEnlem.setText(String.valueOf(enlem));
        editBoylam.setText(String.valueOf(boylam));

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

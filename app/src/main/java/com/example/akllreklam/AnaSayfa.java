package com.example.akllreklam;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnaSayfa extends AppCompatActivity implements LocationListener {
    private TextView kk;
    private ListView mMainList;
    public  FirmaAdapter myAdapter;
    private DatabaseReference mDatabase;
    private ArrayList<Firmalar> alist=new ArrayList<>();
    private  CustomListViewAdapter  adapter;
    private int count=0;
    private int izinKontrol;
    private String konumSaglayici = "gps";
    private LocationManager locationManager;
    private String enlemm,boylamm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);
        reklam();
        final EditText sorgu = (EditText) findViewById(R.id.editTextfiltre2);
        final Button buttonProfil = (Button) findViewById(R.id.buttonProfil);
        final String mail = getIntent().getStringExtra("email");
        final String pass = getIntent().getStringExtra("password");
        final Button buttonLokasyon = (Button) findViewById(R.id.buttonLokasyon);
        final Button buttonfiltre = (Button) findViewById(R.id.buttonfiltre2);
        mDatabase=FirebaseDatabase.getInstance().getReference("firmalar");
        mMainList=(ListView) findViewById(R.id.mList);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        izinKontrol = ContextCompat.checkSelfPermission(AnaSayfa.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (izinKontrol != PackageManager.PERMISSION_GRANTED) {
            //daha önceden izin verilmemis
            ActivityCompat.requestPermissions(AnaSayfa.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            //daha önceden izin verilmis
            Location konum = locationManager.getLastKnownLocation(konumSaglayici);
            if (konum != null) {
                onLocationChanged(konum);

            } else {
                Toast.makeText(getApplicationContext(), "Enlem-Boylam Aktif Değil!", Toast.LENGTH_SHORT).show();
            }

        }




        buttonProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnaSayfa.this, Profil.class);
                i.putExtra("email", mail);
                i.putExtra("password", pass);
                startActivity(i);
            }
        });
        buttonLokasyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnaSayfa.this, Lokasyon.class);
                startActivity(i);
            }
        });
        
        buttonfiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= String.valueOf(sorgu.getText());
                if(s.equals("Yiyecek") || s.equals("Kozmetik") || s.equals("Giyim") || s.equals("Elektronik")|| s.equals("yiyecek") || s.equals("kozmetik") || s.equals("giyim") || s.equals("elektronik")){
                    if(s.equals( "yiyecek"))
                        sorgukategori("Yiyecek");
                    if(s.equals( "kozmetik"))
                        sorgukategori("Kozmetik");
                    if(s.equals( "giyim"))
                        sorgukategori("Giyim");
                    if(s.equals( "elektronik"))
                        sorgukategori("Elektronik");
                    sorgukategori(s);
                }else if(s.equals("")){
                   reklam();
                } else{
                    reklam();
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100) {
            izinKontrol = ContextCompat.checkSelfPermission(AnaSayfa.this, Manifest.permission.ACCESS_FINE_LOCATION);

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
    public void reklam() {
        alist.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("firmalar");
        final NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        for(int i=0;i<=count;i++){
            notif.cancel(i);
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Firmalar firma = d.getValue(Firmalar.class);
                    alist.add(firma);
                    adapter=new CustomListViewAdapter(AnaSayfa.this,alist);
                    mMainList.setAdapter(adapter);
                    double firmaboylam=Double.parseDouble(firma.getFirma_boylam());
                    double firmaenlem=Double.parseDouble(firma.getFirma_enlem());

                    if(firmaenlem>=Double.parseDouble(enlemm)-10 && firmaenlem<=Double.parseDouble(enlemm)+10){
                        if (firmaboylam>=Double.parseDouble(boylamm)-10 && firmaboylam<=Double.parseDouble(boylamm)+10) {

                            Notification notify=new Notification.Builder
                                    (getApplicationContext()).setContentTitle("Yakınınızda olan mağazalar").setContentText(firma.getFirma_adi()).
                                    setContentTitle("Yakınında olan Mağaza").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).build();

                            notify.flags |= Notification.FLAG_AUTO_CANCEL;
                            notif.notify(count, notify);
                            count ++;
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void sorgukategori(String s) {
        alist.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("firmalar");
        Query kisilerSorgu = myRef.orderByChild("kagegori").equalTo(s);
        final NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        for(int i=0;i<=count;i++){
            notif.cancel(i);
        }
        kisilerSorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Firmalar firma = d.getValue(Firmalar.class);
                    alist.add(firma);
                    adapter=new CustomListViewAdapter(AnaSayfa.this,alist);
                    mMainList.setAdapter(adapter);
                    double firmaboylam=Double.parseDouble(firma.getFirma_boylam());
                    double firmaenlem=Double.parseDouble(firma.getFirma_enlem());
                    if(firmaenlem>=Double.parseDouble(enlemm)-10 && firmaenlem<=Double.parseDouble(enlemm)+10){
                        if (firmaboylam>=Double.parseDouble(boylamm)-10 && firmaboylam<=Double.parseDouble(boylamm)+10) {

                            Notification notify=new Notification.Builder
                                    (getApplicationContext()).setContentTitle("Yakınınızda olan mağazalar").setContentText(firma.getFirma_adi()).
                                    setContentTitle("Yakınında olan Mağaza").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).build();

                            notify.flags |= Notification.FLAG_AUTO_CANCEL;
                            notif.notify(count, notify);
                            count ++;
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void sorguad(String s) {
        alist.clear();
        final NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        for(int i=0;i<=count;i++){
            notif.cancel(i);
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("firmalar");
        Query kisilerSorgu = myRef.orderByChild("firma_adi").equalTo(s);

        kisilerSorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Firmalar firma = d.getValue(Firmalar.class);
                    alist.add(firma);
                    adapter=new CustomListViewAdapter(AnaSayfa.this,alist);
                    mMainList.setAdapter(adapter);
                    double firmaboylam=Double.parseDouble(firma.getFirma_boylam());
                    double firmaenlem=Double.parseDouble(firma.getFirma_enlem());
                    if(firmaenlem>=Double.parseDouble(enlemm)-10 && firmaenlem<=Double.parseDouble(enlemm)+10){
                        if (firmaboylam>=Double.parseDouble(boylamm)-10 && firmaboylam<=Double.parseDouble(boylamm)+10) {

                            Notification notify=new Notification.Builder
                                    (getApplicationContext()).setContentTitle("Yakınınızda olan mağazalar").setContentText(firma.getFirma_adi()).
                                    setContentTitle("Yakınında olan Mağaza").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).build();

                            notify.flags |= Notification.FLAG_AUTO_CANCEL;
                            notif.notify(count, notify);
                            count ++;
                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
         enlemm = String.valueOf(location.getLatitude());
         boylamm = String.valueOf(location.getLongitude());
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
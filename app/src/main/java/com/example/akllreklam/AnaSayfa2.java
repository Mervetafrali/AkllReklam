package com.example.akllreklam;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnaSayfa2 extends AppCompatActivity {

    private ListView mMainList;
    public  FirmaAdapter myAdapter;
    private DatabaseReference mDatabase;
    private ArrayList<Firmalar> alist=new ArrayList<>();
    private  CustomListViewAdapter  adapter;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa2);

        final EditText sorgu = (EditText) findViewById(R.id.editTextfiltre2);
        final String enlem1 = getIntent().getStringExtra("enlem");
        final String boylam1 = getIntent().getStringExtra("boylam");
        final String esik1 = getIntent().getStringExtra("esik");
        final Button buttonfiltre = (Button) findViewById(R.id.buttonfiltre2);

        mDatabase= FirebaseDatabase.getInstance().getReference("firmalar");
        mMainList=(ListView) findViewById(R.id.list2);

        reklam(enlem1,boylam1,esik1);
        buttonfiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = String.valueOf(sorgu.getText());
                if (s.equals("Yiyecek") || s.equals("Kozmetik") || s.equals("Giyim") || s.equals("Elektronik") || s.equals("yiyecek") || s.equals("kozmetik") || s.equals("giyim") || s.equals("elektronik")) {
                    if (s.equals("yiyecek"))
                        sorgukategori("Yiyecek",enlem1,boylam1,esik1);
                    if (s.equals("kozmetik"))
                        sorgukategori("Kozmetik",enlem1,boylam1,esik1);
                    if (s.equals("giyim"))
                        sorgukategori("Giyim",enlem1,boylam1,esik1);
                    if (s.equals("elektronik"))
                        sorgukategori("Elektronik",enlem1,boylam1,esik1);
                    sorgukategori(s,enlem1,boylam1,esik1);
                } else if (s.equals("")) {
                    reklam(enlem1,boylam1,esik1);
                } else {
                    reklam(enlem1,boylam1,esik1);
                }
            }
        });

    }


    public void reklam(String en,String boylam,String esik) {
        alist.clear();
        final NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        for(int i=0;i<=count;i++){
            notif.cancel(i);
        }
        final double e=Double.parseDouble(en);
        final double b=Double.parseDouble(boylam);
        final int es=Integer.parseInt(esik);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("firmalar");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Firmalar firma = d.getValue(Firmalar.class);
                    double firmaboylam=Double.parseDouble(firma.getFirma_boylam());
                    double firmaenlem=Double.parseDouble(firma.getFirma_enlem());
                    if(firmaenlem>=e-es && firmaenlem<=e+es){
                        if (firmaboylam>=b-es && firmaboylam<=b+es){
                            alist.add(firma);
                            adapter = new CustomListViewAdapter(AnaSayfa2.this, alist);
                            mMainList.setAdapter(adapter);
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
    public void sorgukategori(String sorgu,String en,String boylam,String esik) {
        alist.clear();
        final NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        for(int i=0;i<=count;i++){
            notif.cancel(i);
        }
        final double e=Double.parseDouble(en);
        final double b=Double.parseDouble(boylam);
        final int es=Integer.parseInt(esik);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("firmalar");
        Query kisilerSorgu = myRef.orderByChild("kagegori").equalTo(sorgu);
        kisilerSorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Firmalar firma = d.getValue(Firmalar.class);
                    double firmaboylam=Double.parseDouble(firma.getFirma_boylam());
                    double firmaenlem=Double.parseDouble(firma.getFirma_enlem());
                    if(firmaenlem>=e-es && firmaenlem<=e+es){
                        if (firmaboylam>=b-es && firmaboylam<=b+es){
                            alist.add(firma);
                            adapter = new CustomListViewAdapter(AnaSayfa2.this, alist);
                            mMainList.setAdapter(adapter);
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


    public void sorguad(String sorgu,String en,String boylam,String esik) {
        alist.clear();
        final NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        for(int i=0;i<=count;i++){
            notif.cancel(i);
        }
        final double e=Double.parseDouble(en);
        final double b=Double.parseDouble(boylam);
        final int es=Integer.parseInt(esik);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("firmalar");
        Query kisilerSorgu = myRef.orderByChild("kagegori").equalTo(sorgu);
        kisilerSorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Firmalar firma = d.getValue(Firmalar.class);
                    double firmaboylam=Double.parseDouble(firma.getFirma_boylam());
                    double firmaenlem=Double.parseDouble(firma.getFirma_enlem());
                    if(firmaenlem>=e-es && firmaenlem<=e+es){
                        if (firmaboylam>=b-es && firmaboylam<=b+es){
                            alist.add(firma);
                            adapter = new CustomListViewAdapter(AnaSayfa2.this, alist);
                            mMainList.setAdapter(adapter);
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

    }}
package com.example.akllreklam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirmaEkle extends AppCompatActivity {
    final boolean[] numeric = {true};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma_ekle);
        deneme();


    }
    public void deneme(){
        final EditText editTextFirmaAdi = (EditText) findViewById(R.id.editTextFirmaAdi);
        final EditText editTextEnlem = (EditText) findViewById(R.id.editTextEnlem);
        final EditText editTextBoylam = (EditText) findViewById(R.id.editTextBoylam);
        final EditText editTextIcerik = (EditText) findViewById(R.id.editTextIcerik);
        final EditText editTextSure = (EditText) findViewById(R.id.editTextSure);
        final EditText editTextKategori = (EditText) findViewById(R.id.editTextKategori);
        final Button buttonFirmaEkle = (Button) findViewById(R.id.buttonFirmaEkle);
        final Button buttoncikis = (Button) findViewById(R.id.buttonCikis);

        buttonFirmaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firmaAdi=editTextFirmaAdi.getText().toString();
                String enlem=editTextEnlem.getText().toString();
                String boylam=editTextBoylam.getText().toString();
                String icerik=editTextIcerik.getText().toString();
                String sure=editTextSure.getText().toString();
                String kategori=editTextKategori.getText().toString();
                if(TextUtils.isEmpty(editTextFirmaAdi.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen Firma adını giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextEnlem.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen enlem giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextBoylam.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen boylam giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextIcerik.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen içerik giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextSure.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen süre giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextKategori.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen kategori giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Double num = Double.parseDouble(enlem);
                } catch (NumberFormatException e) {
                    numeric[0] = false;
                    Toast.makeText(getApplicationContext(), "Lütfen enlemi sayı olarak giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Double num = Double.parseDouble(boylam);
                } catch (NumberFormatException e) {
                    numeric[0] = false;
                    Toast.makeText(getApplicationContext(), "Lütfen boylamı sayı olarak giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef2 = database.getReference("firmalar");
                Firmalar firma= new Firmalar("",firmaAdi,enlem,boylam,icerik,kategori,sure);
                myRef2.push().setValue(firma);
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Firmalar firma = d.getValue(Firmalar.class);
                            String key = d.getKey();
                            firma.setFirma_key(key);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("firmalar");
                            Map<String,Object> firmalar=new HashMap<>();
                            firmalar.put("firma_key",firma.getFirma_key());
                            firmalar.put("firma_adi",firma.getFirma_adi());
                            firmalar.put("firma_enlem",firma.getFirma_enlem());
                            firmalar.put("firma_boylam",firma.getFirma_boylam());
                            firmalar.put("firma_kampanya_icerik",firma.getFirma_kampanya_icerik());
                            firmalar.put("firma_kagogeri",firma.getKagegori());
                            firmalar.put("firma_ksure",firma.getFirma_ksure());

                            myRef.child(firma.getFirma_key()).updateChildren(firmalar);
                            Toast.makeText(getApplicationContext(),"Başarılı Eklendi",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        buttoncikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FirmaEkle.this,MainActivity.class);
                startActivity(i);
            }
        });


    }

}

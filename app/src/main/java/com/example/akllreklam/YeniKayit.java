package com.example.akllreklam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class YeniKayit extends AppCompatActivity {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_kayit);
        final EditText editTextKullanıcıAdı = (EditText) findViewById(R.id.editTextKullanıcıAdı);
        final EditText editTextParola = (EditText) findViewById(R.id.editTextTekrar);
        final Button buttonKayitTamamla = (Button) findViewById(R.id.buttonKayitTamamla);
        progressDialog = new ProgressDialog(this);
        buttonKayitTamamla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextKullanıcıAdı.getText().toString();
                String password = editTextParola.getText().toString();
                final String ad = editTextKullanıcıAdı.getText().toString();
                final String password1 = editTextParola.getText().toString();

                if(TextUtils.isEmpty(editTextKullanıcıAdı.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen emailinizi giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextParola.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen parolanızı giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextParola.length()<6){
                    Toast.makeText(getApplicationContext(),"Parola en az 6 haneli olmalıdır",Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(YeniKayit.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_SHORT).show();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("kisiler");
                            Kisiler kisi = new Kisiler("", ad, password1);
                             myRef.push().setValue(kisi);
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                                        Kisiler kisi = d.getValue(Kisiler.class);
                                        String key = d.getKey();
                                        kisi.setKisi_key(key);
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("kisiler");
                                        Map<String,Object> bilgiler=new HashMap<>();
                                        bilgiler.put("kisi_ad",kisi.getKisi_ad());
                                        bilgiler.put("kisi_paralo",kisi.getKisi_paralo());
                                        bilgiler.put("kisi_key",kisi.getKisi_key());
                                        myRef.child(kisi.getKisi_key()).updateChildren(bilgiler);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Intent i = new Intent(YeniKayit.this,MainActivity.class);
                            startActivity(i);
                        } else {
                            Log.e("Yeni Kullanıcı Hatası", task.getException().getMessage());
                        }

                    }
                });
            }
        });

    }



}

package com.example.akllreklam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Profil extends AppCompatActivity {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        final EditText editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextParola1 = (EditText) findViewById(R.id.editTextParola1);
        final EditText editTextTekrar = (EditText) findViewById(R.id.editTextTekrar);
        final Button buttonGüncelle = (Button) findViewById(R.id.buttonGüncelle);
        final String mm=getIntent().getStringExtra("email");
        final String pp=getIntent().getStringExtra("password");
        editTextEmail.setText(mm);
        buttonGüncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editTextParola1.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen parolanızı giriniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextParola1.length()<6){
                    Toast.makeText(getApplicationContext(),"Parola en az 6 haneli olmalıdır",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.equals(editTextParola1.getText().toString(),editTextTekrar.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Parolalar aynı olmalı!",Toast.LENGTH_SHORT).show();
                    return;
                }
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(mm, pp);
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(editTextParola1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(Profil.this,AnaSayfa.class);
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(getApplicationContext(),"Başarısız",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(),"Hata",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }
}
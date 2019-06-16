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
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextKullanıcıAdı = (EditText) findViewById(R.id.KullanıcıAdı);
        final EditText editTextParola = (EditText) findViewById(R.id.Parola);
        final Button buttonGiris = (Button) findViewById(R.id.buttonGiris);
        progressDialog = new ProgressDialog(this);
        buttonGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextKullanıcıAdı.getText().toString().equals("admin")&& editTextParola.getText().toString().equals("123456")){
                    Toast.makeText(getApplicationContext(),"Admin",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,FirmaEkle.class);
                    startActivity(i);

                }
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
                firebaseAuth.signInWithEmailAndPassword(editTextKullanıcıAdı.getText().toString(), editTextParola.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //startActivity(new Intent(getApplication(), AnaSayfa.class));
                            Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(MainActivity.this,AnaSayfa.class);
                            i.putExtra("email",editTextKullanıcıAdı.getText().toString());
                            i.putExtra("password",editTextParola.getText().toString());
                            startActivity(i);
                        } else {
                            Log.e("giris hatası", task.getException().toString());
                        }
                    }
                });
            }
        });
        final Button buttonKayıt = (Button) findViewById((R.id.buttonKayıt));
        buttonKayıt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,YeniKayit.class);
                startActivity(i);
            }
        });
    }



}

package com.infocity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.infocity.InfoCity.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edLogin, edPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null){
            Toast.makeText( this, "User not null", Toast.LENGTH_SHORT ).show();
        }else {
            Toast.makeText( this, "User null", Toast.LENGTH_SHORT ).show();
        }
    }


    private void init()
    {
        edLogin = findViewById( R.id.edLogin );
        edPassword = findViewById( R.id.edPassword );
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickSignIn(View view)
    {
        if(!TextUtils.isEmpty( edLogin.getText().toString()) && !TextUtils.isEmpty( edPassword.getText().toString())) {
            mAuth.signInWithEmailAndPassword(edLogin.getText().toString(), edPassword.getText().toString()).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    assert user != null;
                    if(task.isSuccessful() && user.isEmailVerified())
                    {
                        Toast.makeText( LoginActivity.this, "Вы успешно авторизовались!", Toast.LENGTH_SHORT ).show();
                        Intent intent = new Intent( LoginActivity.this, HomeActivity.class );
                        startActivity( intent );
                    }
                    else
                    {
                        Toast.makeText( LoginActivity.this, "Не удалось авторизоваться. Заполните все поля и повторите попытку!", Toast.LENGTH_SHORT ).show();
                    }
                }
            } );
        }else {
            Toast.makeText( this, "Пожалуйста заполните все поля!", Toast.LENGTH_SHORT ).show();
        }


    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent( LoginActivity.this, StarterActivity.class );
        startActivity( intent );
        finish();
    }

    public void onClickCancel(View view)
    {
        Intent intent = new Intent( LoginActivity.this, StarterActivity.class );
        startActivity( intent );
        finish();
    }




}

package com.infocity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.infocity.InfoCity.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText edLogin, edPassword;
    private FirebaseAuth mAuth;
    private Button btnStart, btnSignUp, btnCancel;
    private TextView tvUsername, tvPochta, tvNameLayout;
    private LinearLayout DataLayout, etLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
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
        DataLayout = findViewById( R.id.DataLayout );
        etLayout = findViewById( R.id.etLayout );
        tvNameLayout =findViewById( R.id.tvNameLayout );
        tvUsername =findViewById( R.id.tvUserEmail );
        tvPochta =findViewById( R.id.tvPochta );
        btnStart = findViewById( R.id.btnStart );
        btnSignUp = findViewById( R.id.bntSignUp );
        btnCancel = findViewById( R.id.btnCancel );
        edLogin = findViewById( R.id.emailField );
        edPassword = findViewById( R.id.passField );
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickSignUp(View view)
    {

        if(!TextUtils.isEmpty( edLogin.getText().toString()) && !TextUtils.isEmpty( edPassword.getText().toString())){
        mAuth.createUserWithEmailAndPassword( edLogin.getText().toString(), edPassword.getText().toString() ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    tvUsername.setVisibility( View.VISIBLE );
                    tvPochta.setVisibility( View.VISIBLE );
                    btnStart.setVisibility( View.VISIBLE );
                    DataLayout.setVisibility( View.GONE );
                    etLayout.setVisibility( View.GONE );
                    tvNameLayout.setVisibility( View.GONE );

                    sendEmailVer();

                    Toast.makeText( RegisterActivity.this, "Регистрация прошла успешно!", Toast.LENGTH_SHORT ).show();
                }
                else
                    {
                        tvUsername.setVisibility( View.GONE );
                        tvPochta.setVisibility( View.GONE );
                        btnStart.setVisibility( View.GONE );
                        tvNameLayout.setVisibility( View.VISIBLE );
                        DataLayout.setVisibility( View.VISIBLE );
                        etLayout.setVisibility( View.VISIBLE );


                    Toast.makeText( RegisterActivity.this, "Не удалось зарегистрироваться. Заполните все поля и повторите попытку!", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }else {
            tvUsername.setVisibility( View.GONE );
            tvPochta.setVisibility( View.GONE );
            btnStart.setVisibility( View.GONE );
            tvNameLayout.setVisibility( View.VISIBLE );
            DataLayout.setVisibility( View.VISIBLE );
            etLayout.setVisibility( View.VISIBLE );

            Toast.makeText( this, "Пожалуйста заполните все поля!", Toast.LENGTH_SHORT ).show();
        }

    }

    public void onClickStart(View view)
    {
        Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
        startActivity( intent );
    }

    private void sendEmailVer()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText( RegisterActivity.this, "Проверьте свою почту для потверждения Email адреса.", Toast.LENGTH_SHORT ).show();
                }else {
                    Toast.makeText( RegisterActivity.this, "Некорректная почта", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent( RegisterActivity.this, StarterActivity.class );
        startActivity( intent );
        finish();
    }

    public void onClickCancel(View view)
    {
        Intent intent = new Intent( RegisterActivity.this, StarterActivity.class );
        startActivity( intent );
        finish();
    }


}

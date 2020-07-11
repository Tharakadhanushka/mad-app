package com.example.travel;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText TextUsername;
    EditText TextPassword;
    Button ButtonLogin;
    TextView RegisterTextView;
    TravelDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        db = new TravelDB(this);
        TextUsername = (EditText)findViewById(R.id.usernameTextView);
        TextPassword = (EditText)findViewById(R.id.passwordTextView);
        ButtonLogin = (Button)findViewById(R.id.loginBtn);
        RegisterTextView = (TextView)findViewById(R.id.registerTextView);
        RegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });



        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = TextUsername.getText().toString().trim();
                String pwd = TextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res == true)
                {
                    Intent HomePage = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Invalid Login",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
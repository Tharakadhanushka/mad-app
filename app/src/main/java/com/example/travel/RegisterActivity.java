package com.example.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    TravelDB db;
    EditText TextUsername;
    EditText TextPassword;
    EditText TextCnfPassword;
    Button ButtonRegister;
    TextView LoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new TravelDB(this);
        TextUsername = (EditText)findViewById(R.id.usernameTextView);
        TextPassword = (EditText)findViewById(R.id.passwordTextView);
        TextCnfPassword = (EditText)findViewById(R.id.confpasswordTextView);
        ButtonRegister = (Button)findViewById(R.id.registerBtn);
        LoginTextView = (TextView)findViewById(R.id.loginBtn);
        LoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(LoginIntent);
            }
        });




        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = TextUsername.getText().toString().trim();
                String pwd = TextPassword.getText().toString().trim();
                String cnf_pwd = TextCnfPassword.getText().toString().trim();

                // check cnf password and register

                  if(user.equals("") || pwd.equals("") || cnf_pwd.equals("")){
                      Toast.makeText(RegisterActivity.this, "Please Fill the form", Toast.LENGTH_SHORT).show();
                  }
                  else {

                      if (pwd.equals(cnf_pwd)) {
                          long val = db.addUser(user, pwd);
                          if (val > 0) {
                              Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                              Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                              startActivity(moveToLogin);
                          } else {
                              Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                          }

                      } else {
                          Toast.makeText(RegisterActivity.this, "Please enter Same Password", Toast.LENGTH_SHORT).show();
                      }
                  }
            }
        });
    }
}
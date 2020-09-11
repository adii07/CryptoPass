package com.example.cryptopass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPassword extends AppCompatActivity {
    private EditText newTitle;
    private EditText newPassword;
    private Button saveNewPassword;
    DataBaseHelper myDB;
    private  String title;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        getSupportActionBar().setTitle("Add new Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newTitle=findViewById(R.id.newTitle);
        newPassword=findViewById(R.id.newPassword);
        saveNewPassword=findViewById(R.id.saveNewPassword);
        myDB=new DataBaseHelper(this);


        saveNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=newTitle.getText().toString();
                password=newPassword.getText().toString();
                myDB.insertData(title,password);
                Toast.makeText(getApplicationContext(),"Password Saved.",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
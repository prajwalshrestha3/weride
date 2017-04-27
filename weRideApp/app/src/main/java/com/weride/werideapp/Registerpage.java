package com.weride.werideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.ResultSet ;
import java.sql.Statement ;
import java.util.Scanner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Registerpage extends AppCompatActivity {
    EditText firstname,lastname,age,address,email,username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button insert;
        setContentView(R.layout.activity_registerpage);
        firstname=(EditText)(findViewById(R.id.nameslot));
        lastname=(EditText)(findViewById(R.id.surenameslot));
        age=(EditText)(findViewById(R.id.ageslot));
        address=(EditText)(findViewById(R.id.addressslot));
        email=(EditText)(findViewById(R.id.emailslot));
        username=(EditText)(findViewById(R.id.usrenameslot));
        password=(EditText)(findViewById(R.id.pwslot));
        insert = (Button) findViewById(R.id.insertregslot);
    }

    public void onReg(View view){
    String str_firstname = firstname.getText().toString();
        String str_lastname = lastname.getText().toString();
        String str_age = age.getText().toString();
        String str_address = address.getText().toString();
        String str_email = email.getText().toString();
        String str_username = username.getText().toString();
        String str_password = password.getText().toString();
        String type = "register";
        BackgroundWorker backgroundWorker=new BackgroundWorker(this);
        backgroundWorker.execute(type, str_firstname,str_lastname,str_age,str_address,str_email,str_username,str_password);

    }

}
